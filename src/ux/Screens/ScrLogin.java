package ux.Screens;

import network.Client;
import network.messages.Ack;
import network.messages.Login;
import network.messages.Message;
import network.messages.Packet;
import ux.Buttons.OptionButton;
import ux.Labels.TitleLabel;
import ux.TextField.UserTextField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class ScrLogin extends ScrFactory{
	protected OptionButton signUpBut = new OptionButton(STYLE.GREEN,STRINGS.SIGNUP);
	protected OptionButton signInBut = new OptionButton(Color.RED,STRINGS.SIGNIN);
	protected UserTextField userName = new UserTextField(STRINGS.USERNAME_HINT);
	protected UserTextField passWord = new UserTextField(STRINGS.PASSWORD_HINT);

	protected TitleLabel title = new TitleLabel(STRINGS.TITLE);

	public ScrLogin() {
		// TODO Auto-generated constructor stub
		this.add(leftPanel());
		this.constr.gridx++;
		this.add(rightPanel());
		this.signUpBut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				frame.OpenLinkFrame(new FrameSignUp(), new ScrSignUp());
			}
		});
		this.signInBut.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//Check if the user name is the right length
				if(!isValidPassUser()){
					//Failed and send notification screen
					FrameNotify fn = new FrameNotify();
					fn.add(new ScrNotify(STRINGS.CREDENTIALLENGTHERROR));
				}else{
					//Success and logging in
					Client.client.send(new Login(userName.getText(), passWord.getText()), (p) -> networkLogin(p));
				}
				
			}
		});

	}

	public void networkLogin(Packet p) {
		Message message = p.getData();
		switch (message.type()) {
			case ACK:
				Ack ack = (Ack) message;
				if (ack.getSuccess()) {
					//this login is successful;
					frame.dispose();
					FrameMain fm = new FrameMain();
					fm.add(new ScrMainMenu());
				} else {
					//this login has failed
					FrameNotify fn = new FrameNotify();
					fn.add(new ScrNotify(ack.getMessage()));
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

	public boolean isValidPassUser(){
		//Checks for null and lengt
		if(this.userName.isValidPassUser()&&this.passWord.isValidPassUser()){
			return(true);
		}else{
			return(false);
		}
	}
}