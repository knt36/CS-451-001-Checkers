package ux.Screens;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

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
	
	public ScrLogin(Frame frame) {
		// TODO Auto-generated constructor stub
		this.add(leftPanel());
		this.constr.gridx++;
		this.add(rightPanel());
		signInBut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//sendLogin(userName, passWord);
				System.out.println("clicked login");
                FrameMain mainMenu = new FrameMain();
                mainMenu.addComp(new ScrMainMenu());
                frame.dispose();
            }
		});

		signUpBut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FrameMain signupFr = new FrameSignUp();
				signupFr.addComp(new ScrSignUp(signupFr, frame));
			}
		});
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
