package ux.Screens;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import game.Game;
import ux.Buttons.OptionButton;
import ux.CheckBox.CheckBoxFactory;
import ux.Labels.BulletLabel;
import ux.Labels.HeaderLabel;
import ux.TextField.TextField;

public class ScrCreateGame extends ScrFactory {
	protected TextField gameNameField = new TextField(STRINGS.GAMENAME);
	protected TextField searchUserName = new TextField(STRINGS.SEARCHUSER);
	
	protected HeaderLabel gameLabel = new HeaderLabel("Game");
	protected HeaderLabel usersLabel = new HeaderLabel(STRINGS.HEADERUSERS);
	//Bullets
		ScrFactory usersArea = new ScrFactory();
		JScrollPane usersScroll = new JScrollPane(usersArea);
		
	protected ArrayList<BulletLabel> usersList = new ArrayList<>();
	
	protected CheckBoxFactory allUsers = new CheckBoxFactory(STRINGS.ALLUSERSLABEL);
	protected OptionButton startBut = new OptionButton(STYLE.GREEN,STRINGS.START);
	
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
		for(int i = 0 ; i < 99; i++){
			this.usersArea.add(new BulletLabel("Dick " + i));
			this.usersArea.constr.gridy++;
		}
		this.add(usersScroll);
		this.constr.gridy++;
		
		this.add(this.allUsers);
		
		
		this.constr.gridy++;
		this.constr.fill = constr.NONE;
		this.add(this.startBut);
		
		//Add button functionality
		this.startBut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//Saves the game to the server
				//Opens the game in question
				FrameGame fg = new FrameGame();
				fg.add(new ScrGame(new Game("DeathMatch", "Rachel", "Khoi")));
				frame.dispose();
			}
		});
		
		//Add autofill functionality
		this.searchUserName.getDocument().addDocumentListener(new DocumentListener() {
			ArrayList<String> userNames = new ArrayList<>();
			@Override
			public void removeUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				action(e);
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				action(e);
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				action(e);
			}
			
			public void action(DocumentEvent e){
				try{
					String s = searchUserName.getText();
					if(s.length()==3){
						//Get the database info for the names
					} else if(s.length()<3){
						//Do nothing
					} else{
						//if it is greater than 3 then just search off the list you have now
						
					}
				}catch(Exception b){
					
				}
			}
		});
	}
	
}
