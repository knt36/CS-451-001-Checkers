package ux.Screens;

import static game.Color.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.util.Date;
import java.util.Timer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import game.Game;
import game.MoveStatus;
import game.Player;
import javafx.scene.shape.Circle;
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
	protected OptionButton turnColorIndicator = new OptionButton(Color.red, "Color");
	
	public ScrGame(Game game) {
		// TODO Auto-generated constructor stub
		this.game = game;
		this.board = new GuiBoard(this.game);
		this.constr.gridheight=5;
		this.add(this.board);
		this.constr.gridheight=1;
		this.constr.gridx++;
		this.constr.fill=this.constr.NONE;
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
		
		//Adding button functions
		this.quitBt.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				frame.dispose();
			}});
		this.helpBt.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				FrameNotify fn = new FrameNotify();
				fn.add(new JLabel("Not sure what html help menu is"));
			}
		});
		this.endBt.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				FrameNotify fn = new FrameNotify();
				fn.add(new ScrDeleteConfirm());
				frame.dispose();
				
				//Idk some surrendering conditions before leaving the game server or just delete the game period.
			}
		});
	}
	public ScrGame(String name, Game game){
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
					
					revalidate();
					repaint();
					}
				
				Player winner = game.winner();
				if(winner != null){
					//There is a winner
					setWinner(winner);
				}
				else{
					setTurnText();
				}
			}
		});
		
		//Adding button functions
		this.quitBt.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				frame.dispose();
			}});
		this.helpBt.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				FrameNotify fn = new FrameNotify();
				fn.add(new JLabel("Not sure what html help menu is"));
			}
		});
		this.endBt.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				FrameNotify fn = new FrameNotify();
				fn.add(new ScrDeleteConfirm());
				frame.dispose();
				
				//Idk some surrendering conditions before leaving the game server or just delete the game period.
			}
		});

	}
	public void setTurnText(){
		this.playerTurn.setText(this.game.turn.getName()+this.turnIndicator);
		Color c = null;
		changeTurnColorIndicator();
	}
	
	public void changeTurnColorIndicator(){
		if(this.game.turn.getColor() == RED){
			this.turnColorIndicator.setBackground(Color.red);
			this.turnColorIndicator.setForeground(Color.black);
		}else if (this.game.turn.getColor() == WHITE){
			this.turnColorIndicator.setBackground(Color.white);
			this.turnColorIndicator.setForeground(Color.black);
		}
	}
	
	public void setWinner(Player winner){
		System.out.println("Winner!");
		this.turnColorIndicator.setText(winner.getName() + " " + STRINGS.GRATZWINNER);
	}
}
