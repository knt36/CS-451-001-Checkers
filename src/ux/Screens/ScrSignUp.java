package ux.Screens;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ux.Buttons.OptionButton;
import ux.TextField.TextField;

public class ScrSignUp extends ScrFactory{
	protected TextField userName = new TextField(STRINGS.USERNAME_HINT);
	protected TextField passWord = new TextField(STRINGS.PASSWORD_HINT);
	
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
				
				//Need to address some error handling for already existing users.
				
				//Exit signup screen
				frame.dispose();
			}
		});
	}
	
}
