package ux.Screens;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ux.Buttons.OptionButton;
import ux.Labels.NoteLabel;

public class ScrNotify extends ScrFactory {
	protected OptionButton okBut = new OptionButton(STYLE.GREEN,STRINGS.DELETECONFIRMBUT);	
	NoteLabel msg = null;
	public ScrNotify(String text) {
		// TODO Auto-generated constructor stub
		//The button fills horizontal unlike everything else. This is intended or  I have to increase the original frame size.
		msg = new NoteLabel(text);
		this.constr.gridwidth =2;
		this.add(msg);
		this.constr.gridwidth=1;
		this.constr.gridy++;
		this.constr.fill = this.constr.HORIZONTAL;
		this.add(this.okBut);
		this.constr.gridx++;
		
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
	
	}
}
