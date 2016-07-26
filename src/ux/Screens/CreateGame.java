package Screens;

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import Buttons.OptionButton;
import Labels.BulletLabel;
import Labels.HeaderLabel;
import TextField.TextField;

public class ScrCreateGame extends ScrFactory {
	protected TextField gameNameField = new TextField(STRINGS.GAMENAME);
	
	protected HeaderLabel usersLabel = new HeaderLabel(STRINGS.HEADERUSERS);
	//Bullets
		ScrFactory usersArea = new ScrFactory();
		JScrollPane usersScroll = new JScrollPane(usersArea);
		
	protected ArrayList<BulletLabel> usersList = new ArrayList<>();
	
	protected JCheckBox allUsers = new JCheckBox();
	protected OptionButton startBut = new OptionButton(STYLE.GREEN,STRINGS.START);
	
	public ScrCreateGame() {
		// TODO Auto-generated constructor stub
		this.constr.fill = constr.HORIZONTAL;
		this.add(gameNameField);
		this.constr.gridy++;
		this.constr.weighty = 0;
		this.constr.fill=constr.BOTH;
		this.constr.anchor = this.constr.ABOVE_BASELINE;
		this.add(usersLabel);
		this.constr.gridy++;
		this.constr.weighty = 1;
		this.constr.fill = constr.BOTH;
		this.add(usersScroll);
		this.constr.gridy++;
		this.constr.fill = constr.NONE;
		this.add(this.startBut);
	}
	
}
