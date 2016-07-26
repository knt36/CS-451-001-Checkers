package Screens;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Buttons.OptionButton;
import Labels.NoteLabel;

public class ScrDisconnect extends ScrFactory {
	protected OptionButton quitBut = new OptionButton(Color.RED, STRINGS.QUITBUT);
	protected NoteLabel msg = new NoteLabel(STRINGS.DISCONNECTMSG);
	
	public ScrDisconnect() {
		// TODO Auto-generated constructor stub
		//Somthering is wrong with centering of the text
		this.constr.anchor = constr.CENTER;
		this.add(msg);
		this.constr.gridy++;
		this.constr.fill = constr.NONE;
		this.add(quitBut);
	}
}
