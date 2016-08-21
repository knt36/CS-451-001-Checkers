package ux.Screens;

import network.Client;
import network.messages.Ack;
import network.messages.Login;
import network.messages.Message;
import network.messages.Packet;
import ux.Buttons.OptionButton;
import ux.Labels.TitleLabel;
import ux.TextField.TextFieldPassword;
import ux.TextField.UserTextField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ScrLogin extends ScrFactory {
    private OptionButton signUpBut = new OptionButton(STYLE.GREEN, STRINGS.SIGNUP);
    private OptionButton signInBut = new OptionButton(Color.RED, STRINGS.SIGNIN);
    private OptionButton quitBt = new OptionButton(Color.red, STRINGS.QUITBUT);
    private UserTextField userName = new UserTextField(STRINGS.USERNAME_HINT);
    private TextFieldPassword passWord = new TextFieldPassword();

    private TitleLabel title = new TitleLabel(STRINGS.TITLE);
    private ThreadHeartBeat rt = new ThreadHeartBeat(this);

    public ScrLogin() {
        this.add(leftPanel());
        this.constr.gridx++;
        this.add(rightPanel());
        this.signUpBut.addActionListener((ActionEvent e) ->
                frame.OpenLinkFrame(new FrameSignUp(), new ScrSignUp())
        );
        this.signInBut.addActionListener((ActionEvent e) -> {
            //Check if the user name is the right length
            //Success and logging in
            String s = new String(passWord.getPassword());
            Client.client.send(new Login(userName.getText(), s), this::networkLogin);
        });
        this.quitBt.addActionListener((ActionEvent e) -> {
            //Exits out of program entirely
            System.exit(0);
        });
        //Start refreshing game thread
        new Thread(rt).start();

    }

    private void networkLogin(Packet p) {
        Message message = p.getData();
        switch (message.type()) {
            case ACK:
                Ack ack = (Ack) message;
                if (ack.getSuccess()) {
                    //this login is successful;
                    frame.dispose();
                    rt.running = false;

                    FrameMain fm = new FrameMain();
                    fm.add(new ScrMainMenu());

                    //if getting this, close disconnect window if open?
                    if (FrameNotifyDisconnect.getCounter() >= 1) {
                        //close FrameNotifyDisconnect
                        Frame[] frame = FrameNotifyDisconnect.getFrames();
                        for (Frame f : frame) {
                            if (f instanceof FrameNotifyDisconnect) {
                                f.dispose();
                            }
                        }
                    }

                } else {
                    //this login has failed
                    if (ack.getMessage().contains("connect") && FrameNotifyDisconnect.getCounter() < 1) {
                        FrameNotifyDisconnect fn = new FrameNotifyDisconnect();
                        fn.add(new ScrDisconnect());

                    } else if (!ack.getMessage().contains("connect")) {
                        FrameNotify fn = new FrameNotify();
                        fn.add(new ScrNotify(ack.getMessage()));
                    }
                }
                break;
            default:
                System.out.println("Unexpected message from server: " + p.toJson());
        }
    }

    private JPanel rightPanel() {
        ScrFactory right = new ScrFactory();
        right.constr.fill = GridBagConstraints.HORIZONTAL;
        right.add(this.userName);
        right.constr.gridy++;
        right.add(this.passWord);
        right.constr.fill = GridBagConstraints.NONE;
        right.constr.gridy++;
        right.add(this.signInBut);
        right.constr.gridy++;
        right.add(this.quitBt);
        return (right);
    }

    private JPanel leftPanel() {
        ScrFactory left = new ScrFactory();
        left.constr.anchor = GridBagConstraints.ABOVE_BASELINE;
        left.add(title);
        left.constr.gridy++;
        left.constr.fill = GridBagConstraints.NONE;
        left.constr.anchor = GridBagConstraints.NORTH;
        left.add(signUpBut);
        return (left);
    }

    void networkHB(Packet p) {
        Message message = p.getData();
        switch (message.type()) {
            case ACK:
                Ack ack = (Ack) message;
                if (ack.getSuccess()) {
                    //server is up
                    if (FrameNotifyDisconnect.getCounter() >= 1) {
                        //close FrameNotifyDisconnect
                        Frame[] frame = FrameNotifyDisconnect.getFrames();
                        for (Frame f : frame) {
                            if (f instanceof FrameNotifyDisconnect) {
                                f.dispose();
                            }
                        }
                    }

                } else {
                    //server is down
                    if (ack.getMessage().contains("connect") && FrameNotifyDisconnect.getCounter() < 1) {
                        FrameNotifyDisconnect fn = new FrameNotifyDisconnect();
                        fn.add(new ScrDisconnect());

                    } else if (!ack.getMessage().contains("connect")) {
                        FrameNotify fn = new FrameNotify();
                        fn.add(new ScrNotify(ack.getMessage()));
                    }
                }
                break;
            default:
                System.out.println("Unexpected message from server: " + p.toJson());
        }
    }
}