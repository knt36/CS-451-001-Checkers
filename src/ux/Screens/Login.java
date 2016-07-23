package Screens;

import javax.swing.JFrame;

import Buttons.Button;
import Labels.HeaderLabel;
import Labels.MyTextField;

public class Login extends JFrame{
	protected Button signUpBut = new Button();
	protected Button signInBut = new Button();
	protected MyTextField userName = new MyTextField();
	protected MyTextField passWord = new MyTextField();
	
	protected HeaderLabel title = new HeaderLabel();
}
