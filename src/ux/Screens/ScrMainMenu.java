package ux.Screens;

import game.Game;
import game.GameList;
import network.Client;
import network.messages.Ack;
import network.messages.Message;
import network.messages.Packet;
import ux.Buttons.OptionButton;
import ux.Labels.BulletGameLabel;
import ux.Labels.HeaderLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

public class ScrMainMenu extends ScrFactory {
    OptionButton newGameBt = new OptionButton(STYLE.GREEN, STRINGS.NEW_GAMEBUT);
    OptionButton helpBt = new OptionButton(STYLE.GREEN, STRINGS.HELPBUT);
    OptionButton quitBt = new OptionButton(Color.red, STRINGS.QUITBUT);
    private GameList gameList = null;
    private HeaderLabel curGameLabel = new HeaderLabel(STRINGS.CURGAMELABEL);
    private HeaderLabel pubGameLabel = new HeaderLabel(STRINGS.PUBGAMELABEL);
    //Bullets
    private ScrFactory curGameArea = new ScrFactory();
    private JScrollPane curGameScroll = new JScrollPane(curGameArea);
    //Bullets
    private ScrFactory pubGameArea = new ScrFactory();
    private JScrollPane pubGameScroll = new JScrollPane(pubGameArea);

    public ScrMainMenu() {
        this.add(leftPanel());
        this.constr.gridx++;
        this.add(rightPanel());

        //Adding Button listners
        quitBt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                nextFrameQuitBtn();
            }
        });
        helpBt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                nextFrameHelpBtn();
            }
        });
        newGameBt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                nextFrameNewGameBtn();
            }
        });

        //Start refreshing game thread
        Runnable rt = new ThreadRefreshGameList(this);
        Thread th = new Thread(rt);
        th.start();
    }

    private ScrFactory leftPanel() {
        ScrFactory left = new ScrFactory();
        left.constr.fill = GridBagConstraints.NONE;
        left.add(newGameBt);
        left.constr.gridy++;
        left.add(helpBt);
        left.constr.gridy++;
        left.add(quitBt);
        return (left);
    }

    private ScrFactory rightPanel() {
        ScrFactory right = new ScrFactory();
        right.constr.fill = GridBagConstraints.HORIZONTAL;
        right.constr.weighty = 0;
        right.add(curGameLabel);
        right.constr.gridy++;

        //Bullets
        right.constr.fill = GridBagConstraints.BOTH;
        this.curGameArea.constr.fill = GridBagConstraints.HORIZONTAL;
        right.constr.weighty = 1;
        this.curGameScroll.setMinimumSize(new Dimension(0, 300));
        right.add(this.curGameScroll);


        right.constr.gridy++;
        right.constr.fill = GridBagConstraints.HORIZONTAL;
        right.constr.weighty = 0;
        right.add(pubGameLabel);
        right.constr.gridy++;

        //Bullets
        right.constr.fill = GridBagConstraints.BOTH;
        this.pubGameArea.constr.fill = GridBagConstraints.HORIZONTAL;
        right.constr.weighty = 1;
        this.pubGameScroll.setMinimumSize(new Dimension(0, 300));
        right.add(this.pubGameScroll);

        //Add the bullets
        refreshGameList();

		return (right);
	}
	
	public void refreshGameList(){
		if(this.gameList == null){
			return;
			// don't do anything since it did not return anything
		}
		this.curGameArea.removeAll();
		this.pubGameArea.removeAll();
		this.curGameArea.constr.fill = curGameArea.constr.HORIZONTAL;
		this.curGameScroll.setMinimumSize(new Dimension(0, 300));
		for (Game g : this.gameList.current) {
			BulletGameLabel lb = new BulletGameLabel(g.name);
			lb.addMouseListener(new MouseListener() {
				
				@Override
				public void mouseReleased(MouseEvent e) {
                    nextFrameMouseReleasedCurrent(g);
				}

                @Override
                public void mousePressed(MouseEvent e) {
                    // TODO Auto-generated method stub

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

                }
            });

            this.curGameArea.add(lb);
            this.curGameArea.constr.gridy++;
        }

        this.pubGameArea.constr.fill = pubGameArea.constr.HORIZONTAL;
        this.pubGameScroll.setMinimumSize(new Dimension(0, 300));
        for (Game g : this.gameList.pub) {
            BulletGameLabel lb = new BulletGameLabel(g.name);
            lb.addMouseListener(new MouseListener() {

				@Override
				public void mouseReleased(MouseEvent e) {

                    //Start the selected game
                    g.p2 = g.p1.opponent(Client.client.getUsername());
                    if(g.turn.getName().equals("")){
                        g.turn = g.p2;
                    }
                    Client.client.send(g, (p) -> networkGameUpdate(p));
                    nextFrameMouseReleasedPublic(g);
				}

                @Override
                public void mousePressed(MouseEvent e) {
                    // TODO Auto-generated method stub

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

                }
            });

            this.pubGameArea.add(lb);
            this.pubGameArea.constr.gridy++;
        }
        revalidate();
        repaint();
    }

    public void networkGameListRefresh(Packet p) {
        System.out.println("Game network Game List Refreshed");
        Message message = p.getData();
        System.out.println(p.getData().toJson().toString());
        switch (message.type()) {
            case GAME_LIST:
                //if getting this, close disconnect window if open?
                if (FrameNotifyDisconnect.getCounter() >= 1) {
                    //close FrameNotifyDisconnect
                    Frame[] frame = FrameNotifyDisconnect.getFrames();
                    for (Frame f : frame) {
                        if (f instanceof FrameNotifyDisconnect) {
                            f.dispose();
                        }
                    }
                }

                gameList = (GameList) message;
                refreshGameList();
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

    private void networkGameUpdate(Packet p) {
        Message message = p.getData();
        switch (message.type()) {
            case GAME:
                //if getting this, close disconnect window if open?
                if (FrameNotifyDisconnect.getCounter() >= 1) {
                    //close FrameNotifyDisconnect
                    Frame[] frame = FrameNotifyDisconnect.getFrames();
                    for (Frame f : frame) {
                        if (f instanceof FrameNotifyDisconnect) {
                            f.dispose();
                        }
                    }
                }

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

    public void nextFrameQuitBtn() {
        //Exits out of program entirely
        System.exit(0);
    }

    public void nextFrameHelpBtn() {
        try {
            File htmlFile = new File("help.html");
            Desktop.getDesktop().browse(htmlFile.toURI());
        } catch (IOException error) {
            //don't open
        }
    }

    public void nextFrameNewGameBtn() {
        FrameCreateGame fcg = new FrameCreateGame();
        fcg.add(new ScrCreateGame());
    }

    public void nextFrameMouseReleasedPublic(Game g) {
        FrameGame fg = new FrameGame();
        fg.add(new ScrGame(g));
    }

    public void nextFrameMouseReleasedCurrent(Game g) {
        FrameGame fg = new FrameGame();
        fg.add(new ScrGame(g));
    }
}