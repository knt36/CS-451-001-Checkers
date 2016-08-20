package ux.Labels;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;

import ux.Screens.STRINGS;
import ux.Screens.STYLE;

public class BulletLabel extends LabelFactory{
	protected boolean pressed = false;
	public BulletLabel(String label) {
		super(STRINGS.BULLETCHAR + label);
		// TODO Auto-generated constructor stub
		this.setFont(STYLE.BULLETLABEL_FONT);
	}
}
