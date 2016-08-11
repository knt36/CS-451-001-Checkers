package Buttons;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JFrame;

import Screens.STYLE;
import Screens.ScrFactory;

public class GuiBoard extends ScrFactory {
	protected static final int NUM_MOVABLE_TILES = 32;
	protected ArrayList<BoardButton> board = new ArrayList<>();
	
	protected boolean discSelected = false;
	protected BoardButton prevButton = null;
	
	public GuiBoard() {
		// TODO Auto-generated constructor stub
		initializeBoard();
		this.setPreferredSize(new Dimension(400, 400));
		this.setBackground(STYLE.BOARD_BACKGROUND_CLR);
		this.setBorder(BorderFactory.createLineBorder(Color.black, STYLE.BOARDFRAMEBORDERTK));
	}
	
	public void initializeBoard(){
		for(int i =0 ; i<NUM_MOVABLE_TILES; i ++){
			BoardButton b = new BoardButton();
			board.add(b);
			
			b.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					if(discSelected){
						//Making a move
						prevButton.setBackground(STYLE.BOARDCOLOR);
						discSelected = false;
					}
					else {
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
	
	public void repaintBoard(){
		this.constr.gridx=0;
		this.constr.gridy=0;
		this.constr.gridx++;
		this.add(this.board.get(0));
		int j = 1;
		this.constr.gridx++;
		this.constr.gridx++;
		for(int i = 1 ; i <NUM_MOVABLE_TILES; i++){
			if(i%4 ==0){
				this.constr.gridy++;
				if(j%2 == 0){
					this.constr.gridx=1;
				}
				else{
					this.constr.gridx=0;
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
}
