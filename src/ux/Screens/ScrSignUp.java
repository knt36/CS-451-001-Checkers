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
import java.awt.event.ActionListener;

public class ScrSignUp extends ScrFactory {
    protected UserTextField userName = new UserTextField(STRINGS.USERNAME_HINT);
    protected TextFieldPassword passWord = new TextFieldPassword(STRINGS.PASSWORD_HINT);

    protected OptionButton createBt = new OptionButton(STYLE.GREEN, STRINGS.CREATE);
    protected OptionButton quitBt = new OptionButton(Color.red, STRINGS.QUITBUT);

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
        this.createBt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //Add the network adding user function into here
                System.out.println("Create button pressed");
                Client.client.send(new Signup(userName.getText(), passWord.getText()), (p) -> networkSignup(p));

            }
        });
        this.quitBt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //Exits out of program entirely
                System.exit(0);
            }
        });
    }

    public void networkSignup(Packet p) {
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