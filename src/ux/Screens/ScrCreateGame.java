package ux.Screens;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import ux.Buttons.OptionButton;
import ux.Labels.BulletLabel;
import ux.Labels.HeaderLabel;
import ux.TextField.TextField;

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
		this.constr.fill = constr.HORIZONTAL;
		this.usersArea.constr.fill = this.usersArea.constr.BOTH;
		this.usersScroll.setMinimumSize(new Dimension(0, 300));
		for(int i = 0 ; i < 99; i++){
			this.usersArea.add(new BulletLabel("Dick " + i));
			this.usersArea.constr.gridy++;
		}
		this.add(usersScroll);
		this.constr.gridy++;
		this.constr.fill = constr.NONE;
		this.add(this.startBut);
		startBut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Start button pressed");
			}
		});
	}
	
}
