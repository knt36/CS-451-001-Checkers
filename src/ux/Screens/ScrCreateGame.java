package ux.Screens;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.nio.file.attribute.UserPrincipalLookupService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import database.UserList;
import game.Game;
import game.GameList;
import game.Player;
import network.Client;
import network.messages.*;
import ux.Buttons.OptionButton;
import ux.CheckBox.CheckBoxFactory;
import ux.Labels.BulletLabel;
import ux.Labels.GroupBulletLabel;
import ux.Labels.HeaderLabel;
import ux.TextField.TextField;

import static network.messages.MessageTypes.ACK;

public class ScrCreateGame extends ScrFactory {
	protected TextField gameNameField = new TextField(STRINGS.GAMENAME);
	protected TextField searchUserName = new TextField("");
	
	protected HeaderLabel gameLabel = new HeaderLabel("Game");
	protected HeaderLabel usersLabel = new HeaderLabel( STRINGS.HEADERSEARCHUSERS);
	//Bullets
    ScrFactory usersArea = new ScrFactory();
    JScrollPane usersScroll = new JScrollPane(usersArea);
	
	protected List<String> playerList = new ArrayList<>();
	protected List<BulletLabel> usersList = new ArrayList<>();
	
	protected CheckBoxFactory allUsers = new CheckBoxFactory(STRINGS.ALLUSERSLABEL);
	protected OptionButton startBut = new OptionButton(STYLE.GREEN,STRINGS.START);
	
	protected int selectedUserForGame = -1; // -1 for no user
	protected GroupBulletLabel groupBullets = new GroupBulletLabel();
	public ScrCreateGame() {
		// TODO Auto-generated constructor stub
		this.constr.fill = constr.HORIZONTAL;
		this.add(gameLabel);
		this.constr.gridy++;
		this.add(gameNameField);
		this.constr.gridy++;
		this.constr.weighty = 0;
		this.constr.fill=constr.BOTH;
		this.constr.anchor = this.constr.ABOVE_BASELINE;
		this.add(usersLabel);
		this.constr.gridy++;
		this.add(this.searchUserName);
		this.constr.gridy++;
		this.constr.weighty = 1;
		this.constr.fill = constr.HORIZONTAL;
		this.usersArea.constr.fill = this.usersArea.constr.BOTH;
		this.usersScroll.setMinimumSize(new Dimension(0, 300));

		paintUsersArea();

		this.add(usersScroll);
		this.constr.gridy++;

		this.add(this.allUsers);
		allUsers.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//When check all users, should release all buttons....
				groupBullets.releaseAllButton();
				selectedUserForGame = -1;
			}
		});

		this.constr.gridy++;
		this.constr.fill = constr.NONE;
		this.add(this.startBut);

		//Add button functionality
		this.startBut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//Saves the game to the server
				//Opens the game in question
				
				if(allUsers.isSelected()){
                    Client.client.send(new Game(gameNameField.getText(), Client.client.getUsername()), (p)->networkGameRequest(p));
				}else {
					if(selectedUserForGame != -1){
						System.out.println("Selected game for user " + selectedUserForGame);
						//A player has been selected since it is not -1
						ArrayList<String> arrayListPlayer = (ArrayList)playerList;
						String p1 = arrayListPlayer.get(selectedUserForGame);
                        Client.client.send(new Game(gameNameField.getText(), Client.client.getUsername(), p1), (p)->networkGameRequest(p));
					}else{
						FrameNotify fn = new FrameNotify();
						fn.add(new ScrNotify("Either select a player or checkbox for all players..."));
					}
				}
			}
		});

		//Add autofill functionality
		this.searchUserName.getDocument().addDocumentListener(new DocumentListener() {
			List<String> userNames = new ArrayList<>();
			@Override
			public void removeUpdate(DocumentEvent e) {
				action(e);
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				action(e);
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				action(e);
			}

			public void action(DocumentEvent e){
				try{
					String s = searchUserName.getText();
					if(s.length()==3){
						//Get the database info for the names
						System.out.println(s);
                        Client.client.send(new UserListRequest(s), (p)->networkUserRequest(p));
					} else if(s.length()<3){
						//Do nothing
						System.out.println(s);
					} else{
						//if it is greater than 3 then just search off the list you have now
						//Something wrong with the filtering function
                        //playerList = playerList.stream().filter(p -> p.startsWith(s)).collect(Collectors.toList());
						//System.out.println(s);
					}
				}catch(Exception b){

				}
			}
		});
	}

    private void paintUsersArea() {
		// TODO Auto-generated method stub
    	//Add users to the database
    	//Add false users to the database
    	System.out.println("Paint user area ran");
    	this.usersArea.removeAll();
    			for(int i = 0 ; i < this.playerList.size(); i ++){
    				String p = this.playerList.get(i);
    				BulletLabel bt = new BulletLabel(p);
    				groupBullets.add(bt);
    				this.usersArea.add(bt);
    				final int selection = i;
    				bt.addMouseListener(new MouseListener() {

    					@Override
    					public void mouseReleased(MouseEvent e) {
    						// TODO Auto-generated method stub
    						action();
    					}

    					@Override
    					public void mousePressed(MouseEvent e) {
    						// TODO Auto-generated method stub
    						action();
    					}

    					@Override
    					public void mouseExited(MouseEvent e) {
    						// TODO Auto-generated method stub

    					}

    					@Override
    					public void mouseEntered(MouseEvent e) {
    						// TODO Auto-generated method stub

    					}

    					@Override
    					public void mouseClicked(MouseEvent e) {
    						// TODO Auto-generated method stub
    						action();
    					}

    					public void action(){
    						selectedUserForGame = selection;
    						//un check the all users
    						allUsers.setSelected(false);
    					}
    				});

    				this.usersArea.constr.gridy++;
    			}
	}

	private void networkUserRequest(Packet p) {
        Message message = p.getData();
        switch (message.type()) {
            case USER_LIST:
                UserList userList = (UserList) message;
                playerList = userList.getUsers();
                paintUsersArea();
                revalidate();
                repaint();
                break;
            case ACK:
                Ack ack = (Ack) message;
                //Creation failed
                System.out.println("Something failed");
                FrameNotify fn = new FrameNotify();
                fn.add(new ScrNotify(ack.getMessage()));
                break;
            default:
                System.out.println("Unexpected message from server: " + p.toJson());
        }
    }

	public void networkGameRequest(Packet p){
		System.out.println("Network Game Request");
		 Message message = p.getData();
	        switch (message.type()) {
	            case GAME:
	                Game game = (Game) message;
	                FrameGame fg = new FrameGame();
	                fg.add(new ScrGame(game));
	                frame.dispose();
	                //Peform the start of the new game while closing down this window
	                break;
	            case ACK:
	                Ack ack = (Ack) message;
	                //Creation failed
	                System.out.println("Something failed");
	                FrameNotify fn = new FrameNotify();
	                fn.add(new ScrNotify(ack.getMessage()));
	                break;
	            default:
	                System.out.println("Unexpected message from server: " + p.toJson());
	        }
	}
}
