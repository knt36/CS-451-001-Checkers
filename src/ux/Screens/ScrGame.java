package ux.Screens;

import game.Game;
import game.MoveStatus;
import game.Player;
import network.Client;
import network.messages.Ack;
import network.messages.Message;
import network.messages.Packet;
import ux.Buttons.GuiBoard;
import ux.Buttons.OptionButton;
import ux.Labels.NoteLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import static game.Color.RED;
import static game.Color.WHITE;


public class ScrGame extends ScrFactory {
    protected GuiBoard board = null;
    Game game = null;
    private Thread updateThread = null;
    private ThreadUpdateBoard updateRunnable = null;
    private OptionButton helpBt = new OptionButton(STYLE.GREEN, STRINGS.HELPBUT);
    private OptionButton quitBt = new OptionButton(STYLE.GREEN, STRINGS.QUITBUT);
    private OptionButton endBt = new OptionButton(Color.red, STRINGS.ENDGAME);
    private String turnIndicator = "'s Turn! ";
    private NoteLabel playerTurn = new NoteLabel("");
    private OptionButton turnColorIndicator = new OptionButton(Color.red, "Color");

    public ScrGame(Game game) {
        this.game = game;
        this.board = new GuiBoard(this.game);
        this.constr.gridheight = 5;
        this.add(this.board);
        this.constr.gridheight = 1;
        this.constr.gridx++;
        this.constr.fill = GridBagConstraints.NONE;
        this.add(this.turnColorIndicator);
        this.constr.gridy++;
        this.add(this.playerTurn);
        this.constr.gridy++;
        this.add(helpBt);
        this.constr.gridy++;
        this.add(quitBt);
        this.constr.gridy++;
        this.add(endBt);
        this.constr.gridy++;

        //Set color of turn indicator
        this.changeTurnColorIndicator();
        turnColorIndicator.setBorder(BorderFactory.createLoweredBevelBorder());

        //Set label data
        this.playerTurn.setText(game.turn.getName() + turnIndicator);

        //Set up pieces on checker board
        refreshBoard();
        //Adding button functions
        this.quitBt.addActionListener((ActionEvent e) -> {
            stopThreadUpdateBoard();
            frame.dispose();
        });
        this.helpBt.addActionListener((ActionEvent e) -> {
            try {
                File htmlFile = new File("help.html");
                Desktop.getDesktop().browse(htmlFile.toURI());
            } catch (IOException error) {
                //don't open
            }
        });
        this.endBt.addActionListener((ActionEvent e) -> {
            FrameNotify fn = new FrameNotify();
            ScrDeleteConfirm scrDeleteConfirm = new ScrDeleteConfirm(ScrGame.this, game.name);
            frame.OpenLinkFrame(fn, scrDeleteConfirm);
        });

        runThreadUpdateBoard();
    }

    private void runThreadUpdateBoard() {
        ThreadUpdateBoard rt = new ThreadUpdateBoard(this);
        updateRunnable = rt;
        updateThread = new Thread(rt);
        updateThread.start();
    }

    void stopThreadUpdateBoard() {
        if (updateRunnable != null && updateThread != null) {
            System.out.println("Stop Update Board Thread");
            this.updateRunnable.running = false;
            this.updateRunnable = null;
            this.updateThread = null;
        }
    }

    public void networkGame(Packet p) {
        System.out.print("update Game board");
        System.out.println(p.toJson());
        Message message = p.getData();
        switch (message.type()) {
            case GAME:
                Game game = new Game((Game) message);
                System.out.print("update Game board");
                this.game = game;
                refreshBoard();
                setTurnText();
                resumeThreadUpdateBoard();
                revalidate();
                repaint();
                break;
            case ACK:
                Ack ack = (Ack) message;
                if (ack.getSuccess()) {
                    //delete game was successful
                    frame.dispose();
                } else {
                    //this login has failed
                    if (ack.getMessage().contains("connect") && FrameNotifyDisconnect.getCounter() < 1) {
                        FrameNotifyDisconnect fn = new FrameNotifyDisconnect();
                        fn.add(new ScrDisconnect());
                    } else if (!ack.getMessage().contains("connect")) {
                        FrameNotify fn = new FrameNotify();
                        fn.add(new ScrNotify(ack.getMessage()));
                    }
                }
                break;
            default:
                System.out.println("Unexpected message from server: " + p.toJson());
        }
    }

    private void setTurnText() {
        if (this.game.winner() != null) {
            //there is a winner
            Player winner = this.game.winner();
            if (winner.getColor() == RED) {
                this.turnColorIndicator.setText(STRINGS.WINS);
            } else if (winner.getColor() == WHITE) {
                this.turnColorIndicator.setText(STRINGS.WINS);
            }
        } else {
            //there is no winner and game continues
            //changes the turn
            this.playerTurn.setText(this.game.turn.getName() + this.turnIndicator);
            changeTurnColorIndicator();
        }

    }

    private void changeTurnColorIndicator() {
        if (this.game.turn.getColor() == RED) {
            this.turnColorIndicator.setBackground(Color.red);
            this.turnColorIndicator.setForeground(Color.black);
        } else if (this.game.turn.getColor() == WHITE) {
            this.turnColorIndicator.setBackground(Color.white);
            this.turnColorIndicator.setForeground(Color.black);
        }
    }

    public void addThreadEnder() {
        this.frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                stopThreadUpdateBoard();
                super.windowClosing(e);
            }
        });
    }

    private void refreshBoard() {
        board.setBoard(game);
        this.board.addListenerBoard((int start, int finish) -> {
            //Detects a move has been made on the board and then tries to move it in the game
            MoveStatus result = game.move(start, finish);
            pauseThreadUpdateBoard();
            if (result.success()) {
                //there may be more jumps but the board is updated
                board.setBoard(game);
                Client.client.send(new Game(game), this::networkGame);
                revalidate();
                repaint();
            }
        });
    }

    private void pauseThreadUpdateBoard() {
        this.updateRunnable.suspended = true;
    }

    private void resumeThreadUpdateBoard() {
        this.updateRunnable.suspended = false;
    }
}
