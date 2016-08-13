package ux.Labels;

import java.awt.Color;

import javax.swing.JLabel;

import ux.Screens.STYLE;

public class LabelFactory extends JLabel{
	public LabelFactory(String label){
		this.setText(label);
		this.setBackground(STYLE.BACKGROUND);
		this.setHorizontalAlignment(this.CENTER);
	}
}
