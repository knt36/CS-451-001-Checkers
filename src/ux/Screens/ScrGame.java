package ux.Screens;

import java.awt.Color;
import java.util.Date;
import java.util.Timer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import game.Game;
import game.MoveStatus;
import ux.Buttons.GuiBoard;
import ux.Buttons.ListenerBoard;
import ux.Buttons.OptionButton;
import ux.Labels.HeaderLabel;
import ux.Labels.NoteLabel;

public class ScrGame extends ScrFactory{
	protected GuiBoard board = null;
	protected OptionButton helpBt = new OptionButton(STYLE.GREEN,STRINGS.HELPBUT);
	protected OptionButton quitBt= new OptionButton(STYLE.GREEN,STRINGS.QUITBUT);
	protected OptionButton endBt = new OptionButton(Color.red,STRINGS.ENDGAME);
	protected String turnIndicator = "'s Turn! ";
	protected NoteLabel playerTurn = new NoteLabel("");
	protected Game game = null;
	
	public ScrGame(Game game) {
		// TODO Auto-generated constructor stub
		this.game = game;
		this.board = new GuiBoard(this.game);
		this.constr.gridheight=4;
		this.add(this.board);
		this.constr.gridheight=1;
		this.constr.gridx++;
		this.constr.fill=this.constr.NONE;
		this.add(this.playerTurn);
		this.constr.gridy++;
		this.add(helpBt);
		this.constr.gridy++;
		this.add(quitBt);
		this.constr.gridy++;
		this.add(endBt);
		this.constr.gridy++;
		
		//Set label data
		this.playerTurn.setText(game.turn.getName()+turnIndicator);
		
		//Set up pieces on checker board
		this.board.addListenerBoard(new ListenerBoard() {
			
			@Override
			public void performAction(int start, int finish) {
				// TODO Auto-generated method stub
				//Detects a move has been made on the board and then tries to move it in the game
				MoveStatus result = game.move(start, finish);
				
				if(result.success()){
					//there may be more jumps but the board is updated
					board.setBoard(game);
					setTurnText();
					revalidate();
					repaint();
					}
			}
		});
	}
	
	public void setTurnText(){
		this.playerTurn.setText(this.game.turn.getName()+this.turnIndicator);
	}
	
	public void setPieces(){
	
	}
}
