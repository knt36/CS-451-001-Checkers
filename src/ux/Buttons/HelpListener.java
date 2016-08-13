package ux.Buttons;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

/**
 * Created by mblaho on 8/11/16.
 * This is the listener for all "Help Buttons." Pass a new instance of the class to the .addCond()
 * method for a button.
 */
public class HelpListener implements ActionListener{
    public static final String HELP_FILE_LOCATION = "help.html";
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        File htmlFile = new File(HELP_FILE_LOCATION);
        try {
            Desktop.getDesktop().browse(htmlFile.toURI());
        } catch (IOException e) {
            //TODO: Proper logging?
            e.printStackTrace();
        }
    }
}
