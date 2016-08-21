package ux.TextField;


import ux.Screens.STYLE;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class TextField extends TextFieldFactory {
    Boolean userInputedText = false;

    public TextField(String hint) {
        final String hintFinal = hint;
        setForeground(STYLE.HINTCOLOR);
        setText(hint);
        this.addFocusListener(new FocusListener() {
            @Override
            public void focusLost(FocusEvent e) {
                if (getText().length() > 0) {
                    userInputedText = true;
                } else {
                    userInputedText = false;
                    setForeground(STYLE.HINTCOLOR);
                    setText(hintFinal);
                }
            }

            @Override
            public void focusGained(FocusEvent e) {
                userInputedText = false;
                setText("");
                setForeground(STYLE.TEXTFIELDCOLOR);
                //System.out.println("Focused gained");
            }
        });
    }
}
