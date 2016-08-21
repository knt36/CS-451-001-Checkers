package ux.Screens;

import ux.Buttons.OptionButton;
import ux.Labels.NoteLabel;
import ux.Utilities.Timing;

import java.awt.*;
import java.awt.event.ActionEvent;

public class ScrDisconnect extends ScrFactory {
    private OptionButton quitBut = new OptionButton(Color.RED, STRINGS.QUITBUT);
    private NoteLabel msg = new NoteLabel(STRINGS.DISCONNECTMSG);
    private NoteLabel dcTime = new NoteLabel(STRINGS.DCTIME);

    public ScrDisconnect() {
        //Something is wrong with centering of the text
        //get the time the window opened
        this.constr.anchor = GridBagConstraints.CENTER;
        this.constr.gridwidth = 2;
        this.add(msg);
        this.constr.gridy++;
        this.constr.fill = GridBagConstraints.NONE;
        this.constr.gridwidth = 1;
        this.add(quitBut);
        this.constr.gridx++;
        this.add(this.dcTime);

        Timing timing = new Timing(this.dcTime);
        Thread t = new Thread(timing);
        t.start();
        quitBut.addActionListener((ActionEvent e) -> {
            System.out.println("quit button pressed");
            timing.running = false;
            System.exit(0);
        });
    }

}
