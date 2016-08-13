package ux.Screens;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ux.Buttons.OptionButton;
import ux.Labels.NoteLabel;

public class ScrDisconnect extends ScrFactory {
	protected OptionButton quitBut = new OptionButton(Color.RED, STRINGS.QUITBUT);
	protected NoteLabel msg = new NoteLabel(STRINGS.DISCONNECTMSG);
	protected NoteLabel dcTime = new NoteLabel(STRINGS.DCTIME);
	
	public ScrDisconnect() {
		// TODO Auto-generated constructor stub
		//Something is wrong with centering of the text
		this.constr.anchor = constr.CENTER;
		this.constr.gridwidth = 2;
		this.add(msg);
		this.constr.gridy++;
		this.constr.fill = constr.NONE;
		this.constr.gridwidth = 1;
		this.add(quitBut);
		this.constr.gridx++;
		this.add(this.dcTime);
	}
}
