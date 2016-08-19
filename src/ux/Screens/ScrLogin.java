package ux.Screens;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import network.Client;
import network.messages.Ack;
import network.messages.Login;
import network.messages.Packet;
import ux.Buttons.OptionButton;
import ux.Labels.HeaderLabel;
import ux.Labels.TitleLabel;
import ux.TextField.TextField;

public class ScrLogin extends ScrFactory{
	protected OptionButton signUpBut = new OptionButton(STYLE.GREEN,STRINGS.SIGNUP);
	protected OptionButton signInBut = new OptionButton(Color.RED,STRINGS.SIGNIN);
	protected TextField userName = new TextField(STRINGS.USERNAME_HINT);
	protected TextField passWord = new TextField(STRINGS.PASSWORD_HINT);

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
				FrameSignUp fs = new FrameSignUp();
				fs.add(new ScrSignUp());
			}
		});
		this.signInBut.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Packet login = new Packet("", new Login(userName.getText(), passWord.getText()));
				System.out.println(login.toJson());
				Client.client.send((Login) login.getData(), (p)->networkLogin(p));
			}
		});

	}
	
	public void networkLogin(Packet p ){
		System.out.println("Never falled");
		Ack k = (Ack)p.getData();
		if(k.getSuccess()){
			//this login is successful;
			frame.dispose();
			FrameMain fm = new FrameMain();
			fm.add(new ScrMainMenu());
		}else{
			//this login has failed
			FrameNotify fn = new FrameNotify();
			fn.add(new ScrNotify(k.getMessage()));
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
}