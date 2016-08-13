package ux.Buttons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by mblaho on 8/11/16.
 *
 * This is the listener for all "Quit Buttons." Pass a new instance of the class to the .addCond() method for a button.
 */
public class QuitListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        //probably going to be something more? Idk.
        System.exit(0);
    }
}
