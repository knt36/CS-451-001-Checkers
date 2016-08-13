package ux.Screens;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ux.Buttons.OptionButton;
import ux.TextField.TextField;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ScrSignUp extends ScrFactory{
	protected TextField userName = new TextField(STRINGS.USERNAME_HINT);
	protected TextField passWord = new TextField(STRINGS.PASSWORD_HINT);
	
	protected OptionButton createBt = new OptionButton(STYLE.GREEN, STRINGS.CREATE);
	public ScrSignUp(Frame frame, Frame login) {
		// TODO Auto-generated constructor stub
		this.constr.fill=this.constr.HORIZONTAL;
		this.add(this.userName);
		this.constr.gridy++;
		this.add(this.passWord);
		this.constr.gridy++;
		this.constr.fill = constr.NONE;
		this.add(createBt);
		createBt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//sendAccountCreate(userName, passWord);
				System.out.println("create user");
                FrameMain mainMenu = new FrameMain();
                mainMenu.addComp(new ScrMainMenu());
                frame.dispose();
                login.dispose();
			}
		});
	}
	
}
