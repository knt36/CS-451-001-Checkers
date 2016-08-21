package ux.TextField;

import ux.Screens.STYLE;

import javax.swing.*;
import java.awt.*;

public class TextFieldFactory extends JTextField {
    public TextFieldFactory() {
        // TODO Auto-generated constructor stub
        this.setText("");
        this.setCaretColor(Color.black);
        this.setHorizontalAlignment(JTextField.CENTER);
        this.setPreferredSize(new Dimension(50, 50));
        this.setBorder(BorderFactory.createLineBorder(STYLE.TXTFIELDBRDCOLOR, 5, true));
        this.setFont(STYLE.TEXTFIELDFONT);
    }
}
