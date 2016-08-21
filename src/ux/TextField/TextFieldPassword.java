package ux.TextField;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.EventListenerList;

import ux.Screens.STYLE;

public class TextFieldPassword extends JPasswordField {
	protected String hint = "";
	public TextFieldPassword(String hint) {
		// TODO Auto-generated constructor stub
		this.hint = hint;
		this.setCaretColor(Color.black);
		this.setHorizontalAlignment(JTextField.CENTER);
		this.setPreferredSize(new Dimension(50, 50));
		this.setBorder(BorderFactory.createLineBorder(STYLE.TXTFIELDBRDCOLOR, 5, true));
		this.setFont(STYLE.TEXTFIELDFONT);
	}
	
}