package ux.Screens;

import network.Client;
import network.messages.Ack;
import network.messages.Message;
import network.messages.Packet;
import network.messages.Signup;
import ux.Buttons.OptionButton;
import ux.TextField.TextFieldPassword;
import ux.TextField.UserTextField;

import java.awt.*;
import java.awt.event.ActionEvent;

public class ScrSignUp extends ScrFactory {
    private UserTextField userName = new UserTextField(STRINGS.USERNAME_HINT);
    private TextFieldPassword passWord = new TextFieldPassword();

    private OptionButton createBt = new OptionButton(STYLE.GREEN, STRINGS.CREATE);
    private OptionButton quitBt = new OptionButton(Color.red, STRINGS.QUITBUT);

    public ScrSignUp() {
        this.constr.fill = GridBagConstraints.HORIZONTAL;
        this.add(this.userName);
        this.constr.gridy++;
        this.add(this.passWord);
        this.constr.gridy++;
        this.constr.fill = GridBagConstraints.NONE;
        this.add(createBt);
        this.constr.gridy++;
        this.add(quitBt);

        //Add button functionalities
        this.createBt.addActionListener((ActionEvent e) -> {
            //Add the network adding user function into here
            System.out.println("Create button pressed");
            Client.client.send(new Signup(userName.getText(), new String(passWord.getPassword())), this::networkSignup);
        });
        this.quitBt.addActionListener((ActionEvent e) -> {
            //Exits out of program entirely
            System.exit(0);
        });
    }

    private void networkSignup(Packet p) {
        Message message = p.getData();
        switch (message.type()) {
            case ACK:
                Ack ack = (Ack) message;
                if (ack.getSuccess()) {
                    //Username and password made successfully.
                    FrameMain fm = new FrameMain();
                    fm.add(new ScrMainMenu());
                    frame.link.dispose();
                    frame.dispose();
                } else {
                    if (ack.getMessage().contains("connect") && FrameNotifyDisconnect.getCounter() < 1) {
                        FrameNotifyDisconnect fn = new FrameNotifyDisconnect();
                        fn.add(new ScrDisconnect());
                    } else if (!ack.getMessage().contains("connect")) {
                        FrameNotify fn = new FrameNotify();
                        fn.add(new ScrNotify(ack.getMessage()));
                    }
                }
            default:
                System.out.println("Unexpected message from server: " + p.toJson());
        }
    }

}