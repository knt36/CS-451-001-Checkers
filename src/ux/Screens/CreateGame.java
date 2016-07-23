package Screens;

import java.util.ArrayList;

import Buttons.Button;
import Labels.BulletLabel;
import Labels.CheckBoxLabel;
import Labels.HeaderLabel;
import Labels.Label;
import Labels.MyTextField;

public class CreateGame {
	protected MyTextField gameName = new MyTextField();
	protected Label usersLabel = new Label();
	protected ArrayList<BulletLabel> usersList = new ArrayList<>();
	
	protected CheckBoxLabel allUsers = new CheckBoxLabel();
	protected Button startBut = new Button();
	
}
