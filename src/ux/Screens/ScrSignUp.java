package ux.Screens;

import network.Client;
import network.messages.Ack;
import network.messages.Message;
import network.messages.Packet;
import network.messages.Signup;
import ux.Buttons.OptionButton;
import ux.TextField.UserTextField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ScrSignUp extends ScrFactory{
	protected UserTextField userName = new UserTextField(STRINGS.USERNAME_HINT);
	protected UserTextField passWord = new UserTextField(STRINGS.PASSWORD_HINT);

	protected OptionButton createBt = new OptionButton(STYLE.GREEN, STRINGS.CREATE);
	public ScrSignUp() {
		// TODO Auto-generated constructor stub
		this.constr.fill=this.constr.HORIZONTAL;
		this.add(this.userName);
		this.constr.gridy++;
		this.add(this.passWord);
		this.constr.gridy++;
		this.constr.fill = constr.NONE;
		this.add(createBt);

		//Add button functionalities
		this.createBt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//Add the network adding user function into here
				System.out.println("Create button pressed");
				if(userName.isValidPassUser() && passWord.isValidPassUser()){
					Client.client.send(new Signup(userName.getText(), passWord.getText()), (p)->networkSignup(p));
				}else {
					FrameNotify nf = new FrameNotify();
					nf.add(new ScrNotify(STRINGS.CREDENTIALLENGTHERROR));
				}
				
			}
		});
	}
	
	public void networkSignup(Packet p){
		Message message = p.getData();
		switch (message.type()) {
			case ACK:
				Ack ack = (Ack) message;
				if (ack.getSuccess()) {
					//Username and password made successfully.
					FrameMain fm = new FrameMain();
					fm.add(new ScrMainMenu());
					FrameNotify fn = new FrameNotify();
					fn.add(new ScrNotify(ack.getMessage()));
					frame.link.dispose();
					frame.dispose();
				} else {
					//Creation failed
					System.out.println("Something failed");
					FrameNotify fn = new FrameNotify();
					fn.add(new ScrNotify(ack.getMessage()));
				}
			default:
				System.out.println("Unexpected message from server: " + p.toJson());
		}
	}

}