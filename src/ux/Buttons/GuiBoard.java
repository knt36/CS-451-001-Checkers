package ux.Buttons;

import game.Game;
import ux.Screens.STYLE;
import ux.Screens.ScrFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class GuiBoard extends ScrFactory {
    private ArrayList<BoardButton> board = new ArrayList<>();
    private ArrayList<ListenerBoard> listeners = new ArrayList<>();

    private boolean discSelected = false;
    private BoardButton prevButton = null;

    public GuiBoard(Game game) {
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

            b.addActionListener((ActionEvent e) -> {
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
            });
        }
        repaintBoard();
    }

    private void repaintBoard() {
        this.removeAll();
        this.constr.gridx = 0;
        this.constr.gridy = 0;
        this.constr.gridx++;
        this.add(this.board.get(0));
        int j = 1;
        this.constr.gridx++;
        this.constr.gridx++;
        for (int i = 1; i < Game.boardSize; i++) {
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

    private void notifyAllListenerBoard(int start, int finish) {
        for (ListenerBoard listener : listeners) {
            listener.performAction(start, finish);
        }
    }


}
