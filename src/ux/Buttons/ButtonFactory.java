package ux.Buttons;

import javax.swing.*;
import java.awt.*;

public abstract class ButtonFactory extends JButton {
    public ButtonFactory(Color c, String s) {
        this.setBackground(c);
        this.setText(s);
    }
}
