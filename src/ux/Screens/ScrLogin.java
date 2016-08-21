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
import java.awt.event.ActionListener;

public class ScrLogin extends ScrFactory{
	protected OptionButton signUpBut = new OptionButton(STYLE.GREEN,STRINGS.SIGNUP);
	protected OptionButton signInBut = new OptionButton(Color.RED,STRINGS.SIGNIN);
	protected OptionButton quitBt = new OptionButton(Color.red, STRINGS.QUITBUT);
	protected UserTextField userName = new UserTextField(STRINGS.USERNAME_HINT);
	protected TextFieldPassword passWord = new TextFieldPassword(STRINGS.PASSWORD_HINT);

	protected TitleLabel title = new TitleLabel(STRINGS.TITLE);
    protected Runnable rt = new ThreadHeartBeat(this);
    protected Thread th = new Thread(rt);

	public ScrLogin() {
		// TODO Auto-generated constructor stub
		this.add(leftPanel());
		this.constr.gridx++;
		this.add(rightPanel());
		this.signUpBut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.OpenLinkFrame(new FrameSignUp(), new ScrSignUp());
			}
		});
		this.signInBut.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//Check if the user name is the right length
					//Success and logging in
					String s = new String(passWord.getPassword());
					Client.client.send(new Login(userName.getText(), s), (p) -> networkLogin(p));
				
			}
		});
		this.quitBt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //Exits out of program entirely
                System.exit(0);
            }
        });

        //Start refreshing game thread
        th.start();

	}

	public void networkLogin(Packet p) {
		Message message = p.getData();
		switch (message.type()) {
			case ACK:
				Ack ack = (Ack) message;
				if (ack.getSuccess()) {
					//this login is successful;
					frame.dispose();
                    th.stop();
                    
					FrameMain fm = new FrameMain();
					fm.add(new ScrMainMenu());

                    //if getting this, close disconnect window if open?
                    if(FrameNotifyDisconnect.getCounter() >= 1){
                        //close FrameNotifyDisconnect
                        Frame[] frame = FrameNotifyDisconnect.getFrames();
                        for(Frame f : frame){
                            if(f instanceof FrameNotifyDisconnect){
                                f.dispose();
                            }
                        }
                    }

				} else {
					//this login has failed
                    if(ack.getMessage().contains("connect") && FrameNotifyDisconnect.getCounter() < 1){
                        FrameNotifyDisconnect fn = new FrameNotifyDisconnect();
                        fn.add(new ScrDisconnect());

                    } else if (!ack.getMessage().contains("connect")){
                        FrameNotify fn = new FrameNotify();
                        fn.add(new ScrNotify(ack.getMessage()));
                    }
				}
				break;
			default:
				System.out.println("Unexpected message from server: " + p.toJson());
		}
	}

	public JPanel rightPanel(){
		ScrFactory right = new ScrFactory();
		right.constr.fill = right.constr.HORIZONTAL;
		right.add(this.userName);
		right.constr.gridy++;
		right.add(this.passWord);
		right.constr.fill = right.constr.NONE;
		right.constr.gridy++;
		right.add(this.signInBut);
		right.constr.gridy++;
		right.add(this.quitBt);
		return(right);
	}

	public JPanel leftPanel(){
		ScrFactory left = new ScrFactory();
		left.constr.anchor = left.constr.ABOVE_BASELINE;
		left.add(title);
		left.constr.gridy++;
		left.constr.fill = left.constr.NONE;
		left.constr.anchor = left.constr.NORTH;
		left.add(signUpBut);
		return(left);
	}

    public void networkHB(Packet p) {
        Message message = p.getData();
        switch (message.type()) {
            case ACK:
                Ack ack = (Ack) message;
                if (ack.getSuccess()) {
                    //server is up
                    if(FrameNotifyDisconnect.getCounter() >= 1){
                        //close FrameNotifyDisconnect
                        Frame[] frame = FrameNotifyDisconnect.getFrames();
                        for(Frame f : frame){
                            if(f instanceof FrameNotifyDisconnect){
                                f.dispose();
                            }
                        }
                    }

                } else {
                    //server is down
                    if(ack.getMessage().contains("connect") && FrameNotifyDisconnect.getCounter() < 1){
                        FrameNotifyDisconnect fn = new FrameNotifyDisconnect();
                        fn.add(new ScrDisconnect());

                    } else if (!ack.getMessage().contains("connect")){
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