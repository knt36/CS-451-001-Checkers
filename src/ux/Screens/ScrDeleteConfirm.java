package ux.Screens;

import network.Client;
import network.messages.GameDelete;
import ux.Buttons.OptionButton;
import ux.Labels.NoteLabel;

import java.awt.*;
import java.awt.event.ActionEvent;


public class ScrDeleteConfirm extends ScrFactory {
    private OptionButton okBut = new OptionButton(STYLE.GREEN, STRINGS.DELETECONFIRMBUT);
    private OptionButton cancelBut = new OptionButton(Color.red, STRINGS.CANCELBUT);
    private NoteLabel msg = new NoteLabel(STRINGS.PERMADELETE);

    public ScrDeleteConfirm(ScrGame lastScrGame, String lastGame) {
        //The button fills horizontal unlike everything else. This is intended or  I have to increase the original frame size.
        this.constr.gridwidth = 2;
        this.add(msg);
        this.constr.gridwidth = 1;
        this.constr.gridy++;
        this.constr.fill = GridBagConstraints.NONE;
        this.add(this.okBut);
        this.constr.gridx++;
        this.add(this.cancelBut);

        //Add button functionality
        this.okBut.addActionListener((ActionEvent e) -> {
            //Server call to delete the game
            //Must also delete the game in the database
            //Then exits out of the delete confirmation page
            lastScrGame.stopThreadUpdateBoard();
            Client.client.send(new GameDelete(lastGame), lastScrGame::networkGame);
            frame.link.dispose();
            frame.dispose();
        });
        this.cancelBut.addActionListener((ActionEvent e) -> {
            //Delete the message box without doing anything if action is canceled / quited
            frame.dispose();
        });
    }
}
