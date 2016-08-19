package ux.Screens;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ux.Buttons.OptionButton;
import ux.Labels.NoteLabel;

public class ScrNotify extends ScrFactory {
	protected OptionButton okBut = new OptionButton(STYLE.GREEN,STRINGS.DELETECONFIRMBUT);
	protected OptionButton quitBut = new OptionButton(Color.red,STRINGS.CANCELBUT);
	
	NoteLabel msg = new NoteLabel(STRINGS.PERMADELETE); 
	public ScrNotify(String text) {
		// TODO Auto-generated constructor stub
		//The button fills horizontal unlike everything else. This is intended or  I have to increase the original frame size.
		msg.setText(text);
		this.constr.gridwidth =2;
		this.add(msg);
		this.constr.gridwidth=1;
		this.constr.gridy++;
		this.constr.fill = this.constr.NONE;
		this.add(this.okBut);
		this.constr.gridx++;
		this.add(this.quitBut);
		
		//Add button functionality
		this.okBut.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//Server call to delete the game
				
				//Then exits out of the delete confirmation page
				frame.dispose();
			}
		});
		this.quitBut.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//Delete the message box without doing anything if action is canceled / quited
				frame.dispose();
			}
		});
	}
}
