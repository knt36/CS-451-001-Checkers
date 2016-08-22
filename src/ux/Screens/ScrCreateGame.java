package ux.Screens;

import database.UserList;
import game.Game;
import network.Client;
import network.messages.Ack;
import network.messages.Message;
import network.messages.Packet;
import network.messages.UserListRequest;
import ux.Buttons.OptionButton;
import ux.CheckBox.CheckBoxFactory;
import ux.Labels.BulletLabel;
import ux.Labels.GroupBulletLabel;
import ux.Labels.HeaderLabel;
import ux.TextField.TextField;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

public class ScrCreateGame extends ScrFactory {
    private TextField gameNameField = new TextField(STRINGS.GAMENAME);
    private TextField searchUserName = new TextField("");

    private HeaderLabel gameLabel = new HeaderLabel("Game");
    private HeaderLabel usersLabel = new HeaderLabel(STRINGS.HEADERSEARCHUSERS);
    private List<String> playerList = new ArrayList<>();
    private List<BulletLabel> usersList = new ArrayList<>();
    private CheckBoxFactory allUsers = new CheckBoxFactory(STRINGS.ALLUSERSLABEL);
    public OptionButton startBut = new OptionButton(STYLE.GREEN, STRINGS.START);
    private int selectedUserForGame = -1; // -1 for no user
    private GroupBulletLabel groupBullets = new GroupBulletLabel();
    //Bullets
    private ScrFactory usersArea = new ScrFactory();
    private JScrollPane usersScroll = new JScrollPane(usersArea);

    public ScrCreateGame() {
        this.constr.fill = GridBagConstraints.HORIZONTAL;
        this.add(gameLabel);
        this.constr.gridy++;
        this.add(gameNameField);
        this.constr.gridy++;
        this.constr.weighty = 0;
        this.constr.fill = GridBagConstraints.BOTH;
        this.constr.anchor = GridBagConstraints.ABOVE_BASELINE;
        this.add(usersLabel);
        this.constr.gridy++;
        this.add(this.searchUserName);
        this.constr.gridy++;
        this.constr.weighty = 1;
        this.constr.fill = GridBagConstraints.HORIZONTAL;
        this.usersArea.constr.fill = GridBagConstraints.BOTH;
        this.usersScroll.setMinimumSize(new Dimension(0, 300));

        paintUsersArea();

        this.add(usersScroll);
        this.constr.gridy++;

        this.add(this.allUsers);
        allUsers.addActionListener((ActionEvent e) -> {
            //When check all users, should release all buttons....
            groupBullets.releaseAllButton();
            selectedUserForGame = -1;
        });

        this.constr.gridy++;
        this.constr.fill = GridBagConstraints.NONE;
        this.add(this.startBut);

        //Add button functionality
        this.startBut.addActionListener((ActionEvent e) -> {
            //Saves the game to the server
            //Opens the game in question

            if (allUsers.isSelected()) {
                Client.client.send(new Game(gameNameField.getText(), Client.client.getUsername()), (p) -> networkGameRequest(p));
            } else {
                if (selectedUserForGame != -1) {
                    System.out.println("Selected game for user " + selectedUserForGame);
                    //A player has been selected since it is not -1
                    String p1 = playerList.get(selectedUserForGame);
                    Client.client.send(new Game(gameNameField.getText(), Client.client.getUsername(), p1), (p) -> networkGameRequest(p));
                } else {
                    FrameNotify fn = new FrameNotify();
                    fn.add(new ScrNotify("Either select a player or checkbox for all players..."));
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

            public void action(DocumentEvent e) {
                try {
                    String s = searchUserName.getText();
                    if (s.length() == 3) {
                        //Get the database info for the names
                        System.out.println(s);
                        Client.client.send(new UserListRequest(s), (p) -> networkUserRequest(p));
                    } else if (s.length() < 3) {
                        //Do nothing
                        System.out.println(s);
                    } else {
                        //if it is greater than 3 then just search off the list you have now
                        //Something wrong with the filtering function
                        //playerList = playerList.stream().filter(p -> p.startsWith(s)).collect(Collectors.toList());
                        //System.out.println(s);
                    }
                } catch (Exception b) {

                }
            }
        });
    }

    public TextField getGameNameField() {
        return gameNameField;
    }

    public void setGameNameField(TextField gameNameField) {
        this.gameNameField = gameNameField;
    }

    public TextField getSearchUserName() {
        return searchUserName;
    }

    public void setSearchUserName(TextField searchUserName) {
        this.searchUserName = searchUserName;
    }

    public HeaderLabel getGameLabel() {
        return gameLabel;
    }

    public void setGameLabel(HeaderLabel gameLabel) {
        this.gameLabel = gameLabel;
    }

    public HeaderLabel getUsersLabel() {
        return usersLabel;
    }

    public void setUsersLabel(HeaderLabel usersLabel) {
        this.usersLabel = usersLabel;
    }

    public ScrFactory getUsersArea() {
        return usersArea;
    }

    public void setUsersArea(ScrFactory usersArea) {
        this.usersArea = usersArea;
    }

    public JScrollPane getUsersScroll() {
        return usersScroll;
    }

    public void setUsersScroll(JScrollPane usersScroll) {
        this.usersScroll = usersScroll;
    }

    public List<String> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(List<String> playerList) {
        this.playerList = playerList;
    }

    public List<BulletLabel> getUsersList() {
        return usersList;
    }

    public void setUsersList(ArrayList<BulletLabel> usersList) {
        this.usersList = usersList;
    }

    public CheckBoxFactory getAllUsers() {
        return allUsers;
    }

    public void setAllUsers(CheckBoxFactory allUsers) {
        this.allUsers = allUsers;
    }

    public OptionButton getStartBut() {
        return startBut;
    }

    public void setStartBut(OptionButton startBut) {
        this.startBut = startBut;
    }

    public int getSelectedUserForGame() {
        return selectedUserForGame;
    }

    public void setSelectedUserForGame(int selectedUserForGame) {
        this.selectedUserForGame = selectedUserForGame;
    }

    private void paintUsersArea() {
        //Add users to the database
        //Add false users to the database
        System.out.println("Paint user area ran");
        this.usersArea.removeAll();
        for (int i = 0; i < this.playerList.size(); i++) {
            String p = this.playerList.get(i);
            BulletLabel bt = new BulletLabel(p);
            groupBullets.add(bt);
            this.usersArea.add(bt);
            final int selection = i;
            bt.addMouseListener(new MouseListener() {

                @Override
                public void mouseReleased(MouseEvent e) {
                    action();
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    action();
                }

                @Override
                public void mouseExited(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    action();
                }

                public void action() {
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
                if (ack.getMessage().contains("connect") && FrameNotifyDisconnect.getCounter() < 1) {
                    FrameNotifyDisconnect fn = new FrameNotifyDisconnect();
                    fn.add(new ScrDisconnect());
                } else if (!ack.getMessage().contains("connect")) {
                    FrameNotify fn = new FrameNotify();
                    fn.add(new ScrNotify(ack.getMessage()));
                }
                break;
            default:
                System.out.println("Unexpected message from server: " + p.toJson());
        }
    }

    private void networkGameRequest(Packet p) {
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
                if (ack.getMessage().contains("connect") && FrameNotifyDisconnect.getCounter() < 1) {
                    FrameNotifyDisconnect fn = new FrameNotifyDisconnect();
                    fn.add(new ScrDisconnect());
                } else if (!ack.getMessage().contains("connect")) {
                    FrameNotify fn = new FrameNotify();
                    fn.add(new ScrNotify(ack.getMessage()));
                }
                break;
            default:
                System.out.println("Unexpected message from server: " + p.toJson());
        }
    }
}
