package ux.Screens;

import static game.Color.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Ellipse2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Observer;
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
import network.Client;
import network.messages.Ack;
import network.messages.GameDelete;
import network.messages.GameRequest;
import network.messages.Message;
import network.messages.Packet;
import ux.Buttons.GuiBoard;
import ux.Buttons.ListenerBoard;
import ux.Buttons.OptionButton;
import ux.Labels.HeaderLabel;
import ux.Labels.NoteLabel;


public class ScrGame extends ScrFactory{
	protected Thread updateThread = null;
	protected ThreadUpdateBoard updateRunnable = null;
	
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
					if(game.turn.getName().equals(Client.client.getUsername())){
						//still your turn
					}else{
						//else run thread to listen for changes to the board as you wait
						runThreadUpdateBoard();
					}
			
					Client.client.send(new Game(game), (p)->networkGame(p));
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
				stopThreadUpdateBoard();
				frame.dispose();
			}});
		this.helpBt.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
                try {
                    File htmlFile = new File("help.html");
                    Desktop.getDesktop().browse(htmlFile.toURI());
                } catch(IOException error){
                    //don't open
                }
			}
		});
		this.endBt.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				FrameNotify fn = new FrameNotify();
				ScrDeleteConfirm scrDeleteConfirm = new ScrDeleteConfirm(ScrGame.this,game.name);
				frame.OpenLinkFrame(fn, scrDeleteConfirm);

			}
		});
	}
	
	public void runThreadUpdateBoard(){
		ThreadUpdateBoard rt = new ThreadUpdateBoard(this);
		updateRunnable = rt;
		updateThread = new Thread(rt);
		updateThread.start();
	}
	
	public void stopThreadUpdateBoard(){
		if(updateRunnable !=null && updateThread != null){
			System.out.println("Stop Update Board Thread");
			this.updateRunnable.running = false;
			this.updateThread.stop();
			this.updateRunnable = null;
			this.updateThread = null;
		}
	}

    public void networkGame(Packet p) {
    	System.out.print("update Game board");
        Message message = p.getData();
        switch (message.type()) {
            case GAME:
                Game game = (Game) message;
                System.out.print("update Game board");
                board.setBoard(game);
                this.game = game;
                setTurnText();
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
                    FrameNotify fn = new FrameNotify();
                    fn.add(new ScrNotify(ack.getMessage()));
                }
                break;
            default:
                System.out.println("Unexpected message from server: " + p.toJson());
        }
    }

    public void setTurnText(){
		if(this.game.winner()!=null){
			//there is a winner
			Player winner = this.game.winner();
			if(winner.getColor() == RED){
				this.turnColorIndicator.setText(STRINGS.WINS);
			}else if (winner.getColor() == WHITE){
				this.turnColorIndicator.setText(STRINGS.WINS);
			}
		}else {
			//there is no winner and game continues
			//changes the turn
			this.playerTurn.setText(this.game.turn.getName()+this.turnIndicator);
			changeTurnColorIndicator();
		}
		
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
	
	public void addThreadEnder(){
		this.frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				stopThreadUpdateBoard();
				super.windowClosing(e);
			}
		});
	}

	
}
