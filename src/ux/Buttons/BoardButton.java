package ux.Buttons;

import java.awt.Color;

import javax.swing.BorderFactory;

import ux.Screens.STYLE;
import game.Disk;

public class BoardButton extends ButtonFactory {
	public BoardButton() {
		super(STYLE.BOARDCOLOR, "");
		// TODO Auto-generated constructor stub
		this.setBorder(BorderFactory.createLineBorder(STYLE.BOARDBORDERLINECOLOR, STYLE.BOARDBORDERSTHICK, true));
	}	
}
