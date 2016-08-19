package ux.Screens;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import network.Client;
import network.messages.Ack;
import network.messages.Login;
import network.messages.Message;
import network.messages.MessageTypes;
import network.messages.Packet;
import ux.Buttons.OptionButton;
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
				//Access the network
				Client.client.send(new Login(userName.getText(), passWord.getText()), (p) -> networkLogin(p));
			}
		});
	
	}
	
	public void networkLogin(Packet p){
		Message m = p.getData();
		if(m.type()==MessageTypes.LOGIN){
			Ack ak = (Ack)m;
			if(ak.getSuccess()){
				frame.dispose();
				FrameMain fm = new FrameMain();
				fm.add(new ScrMainMenu());
			}else{
				FrameNotify fn = new FrameNotify();
				fn.add(new ScrNotify(ak.getMessage()));
			}
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
