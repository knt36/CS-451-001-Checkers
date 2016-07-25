package Screens;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Labels.BulletLabel;
import TextField.TextField;

public class CreateGame extends JPanel {
	protected TextField gameName = new TextField();
	protected JLabel usersLabel = new JLabel();
	protected ArrayList<BulletLabel> usersList = new ArrayList<>();
	
	protected JCheckBox allUsers = new JCheckBox();
	protected JButton startBut = new JButton();
	
	
}
