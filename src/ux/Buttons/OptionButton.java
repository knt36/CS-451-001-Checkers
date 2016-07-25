package Buttons;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;

import Screens.STYLE;

public class OptionButton extends ButtonFactory{

	public OptionButton(Color c, String s) {
		super(c, s);
		this.setPreferredSize(new Dimension(200, 50));
		this.setFont(STYLE.OPTBUTFONT);
		this.setBorder(BorderFactory.createLineBorder(STYLE.OPTIONBORDER, STYLE.OPTIONBORDERSIZE, true));
		this.setForeground(STYLE.OPTIONTEXTCOLOR);
		// TODO Auto-generated constructor stub
	}
}
