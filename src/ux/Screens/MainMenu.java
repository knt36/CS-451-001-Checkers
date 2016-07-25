package Screens;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Scrollable;

import Buttons.OptionButton;
import Labels.BulletLabel;
import Labels.HeaderLabel;
public class MainMenu extends ScreenFactory{
	protected HeaderLabel curGameLabel = new HeaderLabel(STRINGS.CURGAMELABEL);
	//Bullets
	ScreenFactory curGameArea = new ScreenFactory();
	JScrollPane curGameScroll = new JScrollPane(curGameArea);
	
	
	protected HeaderLabel pubGameLabel = new HeaderLabel(STRINGS.PUBGAMELABEL);
	//Bullets
	ScreenFactory pubGameArea = new ScreenFactory();
	JScrollPane pubGameScroll = new JScrollPane(pubGameArea);
	
	protected OptionButton contBt = new OptionButton(STYLE.GREEN,STRINGS.CONTINUEBUT);
	protected OptionButton newGameBt = new OptionButton(STYLE.GREEN,STRINGS.NEW_GAMEBUT);
	protected OptionButton helpBt = new OptionButton(STYLE.GREEN,STRINGS.HELPBUT);
	protected OptionButton quitBt= new OptionButton(Color.red,STRINGS.QUITBUT);
	
	public MainMenu() {
		// TODO Auto-generated constructor stub
		this.add(leftPanel());
		this.constr.gridx++;
		this.add(rightPanel());
	}
	
	public ScreenFactory leftPanel(){
		ScreenFactory left = new ScreenFactory();
		left.constr.fill = left.constr.NONE;
		left.add(contBt);
		left.constr.gridy++;
		left.add(newGameBt);
		left.constr.gridy++;
		left.add(helpBt);
		left.constr.gridy++;
		left.add(quitBt);
		return(left);
	}
	
	public ScreenFactory rightPanel(){
		ScreenFactory right = new ScreenFactory();
		right.constr.fill = right.constr.HORIZONTAL;
		right.constr.weighty = 0;
		right.add(curGameLabel);
		right.constr.gridy++;
		
		//Bullets
		
		right.constr.fill = right.constr.BOTH;
		this.curGameArea.constr.fill= curGameArea.constr.HORIZONTAL;
		right.constr.weighty = 1;
		for(int i = 0 ; i < 10; i ++){
			BulletLabel lb = new BulletLabel("Current Game");
			this.curGameArea.add(lb);
			this.curGameArea.constr.gridy++;
		}
		right.add(this.curGameScroll);
		
		
		right.constr.gridy++;
		right.constr.fill = right.constr.HORIZONTAL;
		right.constr.weighty = 0;
		right.add(pubGameLabel);
		right.constr.gridy++;
		
		//Bullets
		right.constr.fill = right.constr.BOTH;
		this.pubGameArea.constr.fill= pubGameArea.constr.HORIZONTAL;
		right.constr.weighty = 1;
		for(int i = 0 ; i < 10 ; i ++){
			this.pubGameArea.add(new BulletLabel("Public Game"));
			this.pubGameArea.constr.gridy++;
		}
		right.add(this.pubGameScroll);
		
		return(right);
	}
	
}
