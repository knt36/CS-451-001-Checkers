package ux.Labels;

import java.awt.Color;

import javax.swing.JLabel;

import ux.Screens.STRINGS;
import ux.Screens.STYLE;

public class LabelFactory extends JLabel{
	public LabelFactory(String label){
		this.setText(STRINGS.DIVTEXTCENTER +label + STRINGS.ENDDIV);
		this.setBackground(STYLE.BACKGROUND);
		this.setHorizontalAlignment(this.CENTER);
	}
}
