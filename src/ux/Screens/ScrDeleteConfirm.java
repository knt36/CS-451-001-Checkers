package ux.Screens;

import network.Client;
import network.messages.Ack;
import network.messages.GameDelete;
import network.messages.Message;
import network.messages.Packet;
import ux.Buttons.OptionButton;
import ux.Labels.NoteLabel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ScrDeleteConfirm extends ScrFactory{
	protected OptionButton okBut = new OptionButton(STYLE.GREEN,STRINGS.DELETECONFIRMBUT);
	protected OptionButton cancelBut = new OptionButton(Color.red,STRINGS.CANCELBUT);
	NoteLabel msg = new NoteLabel(STRINGS.PERMADELETE);

	public ScrDeleteConfirm(ScrGame lastScrGame, String lastGame) {
		// TODO Auto-generated constructor stub
		//The button fills horizontal unlike everything else. This is intended or  I have to increase the original frame size.
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
				//Must also delete the game in the database
				//Then exits out of the delete confirmation page
				lastScrGame.stopThreadUpdateBoard();
				Client.client.send(new GameDelete(lastGame), (p) -> lastScrGame.networkGame(p));
				frame.link.dispose();
				frame.dispose();
			}
		});
		this.cancelBut.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//Delete the message box without doing anything if action is canceled / quited
				frame.dispose();
			}
		});
	}

	private void networkGame(Packet p) {
		Message message = p.getData();
		switch (message.type()) {
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
}
