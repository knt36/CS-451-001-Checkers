package ux.Screens;

import ux.Buttons.OptionButton;
import ux.Labels.NoteLabel;
import ux.Utilities.Timing;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ScrDisconnect extends ScrFactory {
	protected OptionButton quitBut = new OptionButton(Color.RED, STRINGS.QUITBUT);
	protected NoteLabel msg = new NoteLabel(STRINGS.DISCONNECTMSG);
	protected NoteLabel dcTime = new NoteLabel(STRINGS.DCTIME);
	
	public ScrDisconnect() {
		// TODO Auto-generated constructor stub
		//Something is wrong with centering of the text
		//get the time the window opened
		this.constr.anchor = constr.CENTER;
		this.constr.gridwidth = 2;
		this.add(msg);
		this.constr.gridy++;
		this.constr.fill = constr.NONE;
		this.constr.gridwidth = 1;
		this.add(quitBut);
		this.constr.gridx++;
		this.add(this.dcTime);

		Thread t = new Thread(new Timing(this.dcTime));
		t.start();
        quitBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("quit button pressed");
                System.exit(0);
            }
        });
	}

}
