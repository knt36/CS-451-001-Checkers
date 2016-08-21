package ux.Buttons;

import game.Game;
import ux.Screens.STYLE;
import ux.Screens.ScrFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GuiBoard extends ScrFactory {
    protected static final int REDPEICES = 12;
    protected static final int WHITEPEICES = 12;
    protected static final int NUM_MOVABLE_TILES = 32;
    protected boolean ranBoardListener = false;
    protected ArrayList<BoardButton> board = new ArrayList<>();
    protected ArrayList<ListenerBoard> listeners = new ArrayList<>();

    protected boolean discSelected = false;
    protected BoardButton prevButton = null;
    protected Game lastGame = null;

    public GuiBoard(Game game) {
        this.lastGame = game;
        this.setPreferredSize(new Dimension(400, 400));
        this.setBackground(STYLE.BOARD_BACKGROUND_CLR);
        this.setBorder(BorderFactory.createLineBorder(Color.black, STYLE.BOARDFRAMEBORDERTK));
        setBoard(game);
        repaint();
    }

    public void setBoard(Game game) {
        this.board.clear();
        for (int i = 0; i < game.board.size(); i++) {
            final BoardButton b;
            if (game.board.get(i).red()) {
                if (game.board.get(i).king()) {
                    b = new BoardButton(Color.red, true);
                } else {
                    b = new BoardButton(Color.red, false);
                }
            } else if (game.board.get(i).white()) {
                if (game.board.get(i).king()) {
                    b = new BoardButton(Color.white, true);
                } else {
                    b = new BoardButton(Color.white, false);
                }
            } else {
                //Empty Space
                b = new BoardButton();
            }
            board.add(b);
            //Sets index so it knows its location on the board
            b.index = i;


            b.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    if (discSelected) {
                        //Making a move
                        prevButton.setBackground(STYLE.BOARDCOLOR);
                        notifyAllListenerBoard(prevButton.index, b.index);
                        discSelected = false;
                    } else {
                        //Selecting  a piece
                        b.setBackground(STYLE.BOARDSELECTEDCLR);
                        discSelected = true;
                        prevButton = b;
                    }
                }
            });
        }
        repaintBoard();
    }

    public void repaintBoard() {
        this.removeAll();
        this.constr.gridx = 0;
        this.constr.gridy = 0;
        this.constr.gridx++;
        this.add(this.board.get(0));
        int j = 1;
        this.constr.gridx++;
        this.constr.gridx++;
        for (int i = 1; i < NUM_MOVABLE_TILES; i++) {
            if (i % 4 == 0) {
                this.constr.gridy++;
                if (j % 2 == 0) {
                    this.constr.gridx = 1;
                } else {
                    this.constr.gridx = 0;
                }
                j++;
            }
            this.add(this.board.get(i));
            this.constr.gridx++;
            this.constr.gridx++;

        }
        revalidate();
        repaint();
    }

    public void addListenerBoard(ListenerBoard l) {
        this.listeners.add(l);
    }

    public void notifyAllListenerBoard(int start, int finish) {
        for (int i = 0; i < this.listeners.size(); i++) {
            listeners.get(i).performAction(start, finish);
        }
    }


}
