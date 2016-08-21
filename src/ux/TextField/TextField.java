package ux.TextField;


import ux.Screens.STYLE;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class TextField extends TextFieldFactory{
	
	public boolean userInputedText = false;
	public TextField() {
		// TODO Auto-generated constructor stub
		
	}
	
	public TextField(String hint){
		final String hintFinal = hint;
		setForeground(STYLE.HINTCOLOR);
		setText(hint);
		this.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
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
			
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				userInputedText = false;
				setText("");
				setForeground(STYLE.TEXTFIELDCOLOR);
				//System.out.println("Focused gained");
			}
		});
	}
}
