package Screens;

import java.util.ArrayList;

import javax.swing.JFrame;

import Buttons.Button;
import Labels.BulletLabel;
import Labels.HeaderLabel;
import Labels.Label;

public class MainMenu extends JFrame{
	protected HeaderLabel curGameLabel = new HeaderLabel();
	//Bullets
	ArrayList<BulletLabel> curGameBullets = new ArrayList<>();
	
	protected HeaderLabel pubGame = new HeaderLabel();
	//Bullets
	ArrayList<BulletLabel>pubGameBullets = new ArrayList<>();
	
	protected Button contBt = new Button();
	protected Button newGameBt = new Button();
	protected Button helpBt = new Button();
	protected Button qutBt= new Button();
	

	
}
