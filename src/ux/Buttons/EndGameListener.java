package ux.Buttons;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ux.Screens.FrameMain;
import ux.Screens.FrameNotify;
import ux.Screens.ScrNotify;


/**
 * Created by mblaho on 8/11/16.
 *
 * This is the listener for all "End Game Buttons." Pass a new instance of the class to the .addCond()
 * method for a button.
 */
public class EndGameListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        //pop open a dialog, this will handle
        FrameMain deleteFr = new FrameNotify();
        deleteFr.addComp(new ScrNotify());
    }
}
