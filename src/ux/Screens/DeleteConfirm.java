package Screens;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Buttons.OptionButton;
import Labels.NoteLabel;


public class ScrDeleteConfirm extends ScrFactory{
	protected OptionButton okBut = new OptionButton(STYLE.GREEN,STRINGS.DELETECONFIRMBUT);
	protected OptionButton quitBut = new OptionButton(Color.red,STRINGS.QUITBUT);
	
	NoteLabel msg = new NoteLabel(STRINGS.PERMADELETE); 
	public ScrDeleteConfirm() {
		// TODO Auto-generated constructor stub
		//The button fills horizontal unlike everything else. This is intended or  I have to increaes the original frame size.
		this.constr.gridwidth =2;
		this.add(msg);
		this.constr.gridwidth=1;
		this.constr.gridy++;
		this.constr.fill = this.constr.NONE;
		this.add(this.okBut);
		this.constr.gridx++;
		this.add(this.quitBut);
	}
}
