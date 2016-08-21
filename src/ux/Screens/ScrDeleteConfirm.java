package ux.Screens;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import game.Game;
import network.Client;
import network.messages.GameDelete;
import ux.Buttons.OptionButton;
import ux.Labels.NoteLabel;


public class ScrDeleteConfirm extends ScrFactory{
	protected OptionButton okBut = new OptionButton(STYLE.GREEN,STRINGS.DELETECONFIRMBUT);
	protected OptionButton cancelBut = new OptionButton(Color.red,STRINGS.CANCELBUT);
	protected ScrGame lastScrGame = (ScrGame)intent.get("screen");
	protected String lastGame = (String)intent.get("game");
	NoteLabel msg = new NoteLabel(STRINGS.PERMADELETE); 
	public ScrDeleteConfirm(ScrGame lastScrGame, String lastGame) {
		System.out.println(this.intent.size() + "Size of intent hash");
		// TODO Auto-generated constructor stub
		//The button fills horizontal unlike everything else. This is intended or  I have to increase the original frame size.
		this.lastScrGame = lastScrGame;
		this.lastGame = lastGame;
		this.constr.gridwidth =2;
		this.add(msg);
		this.constr.gridwidth=1;
		this.constr.gridy++;
		this.constr.fill = this.constr.NONE;
		this.add(this.okBut);
		this.constr.gridx++;
		this.add(this.cancelBut);
		
		//Add button functionality
		this.okBut.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//Server call to delete the game
				//Then exits out of the delete confirmation page
				if(lastScrGame == null){
					System.out.println("lastScrGame is null");
				}
				if(lastGame == null){
					System.out.println("lastgame is null");
				}
			    lastScrGame.stopThreadUpdateBoard();
				Client.client.send(new GameDelete(lastGame), (p)->lastScrGame.networkGame(p));				
				frame.link.dispose();
				frame.dispose();
			}
		});
		this.cancelBut.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//Delete the message box without doing anything if action is canceled / quited
				frame.dispose();
			}
		});
	}
}
