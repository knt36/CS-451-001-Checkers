package ux.TextField;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.EventListenerList;

import ux.Screens.STYLE;

public class TextFieldPassword extends TextFieldFactory {
	protected String hintFinal = "";
	public boolean userInputedText = false;
	protected String userInputTextString = "";
	
	public TextFieldPassword(String hint){
		hintFinal = hint;
		setForeground(STYLE.HINTCOLOR);
		setText(hint);
		this.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				focusLostAction();
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				focusGainAction();
			}
		});
		this.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	public void focusLostAction(){
		if(getText().length()>0){
			//System.out.println(getText());
			userInputedText = true;
		}
		else{
			userInputedText = false;
			setForeground(STYLE.HINTCOLOR);
			setText(hintFinal);
		}
	}
	
	public void focusGainAction(){
		userInputedText = false;
		setText("");
		setForeground(STYLE.TEXTFIELDCOLOR);
		//System.out.println("Focused gained");
	}
}
