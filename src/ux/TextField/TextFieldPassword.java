package ux.TextField;

import ux.Screens.STYLE;

import javax.swing.*;
import java.awt.*;

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
