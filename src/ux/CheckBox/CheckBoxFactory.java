package ux.CheckBox;

import java.awt.Color;

import javax.swing.JCheckBox;

import ux.Screens.STYLE;

public class CheckBoxFactory extends JCheckBox {
	public CheckBoxFactory(String text) {
		// TODO Auto-generated constructor stub
		this.setBackground(STYLE.BACKGROUND);
		this.setText(text);
	}
}
