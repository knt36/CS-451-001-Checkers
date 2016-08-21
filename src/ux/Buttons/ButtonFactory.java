package ux.Buttons;

import javax.swing.*;
import java.awt.*;

abstract class ButtonFactory extends JButton {
    ButtonFactory(Color c, String s) {
        this.setBackground(c);
        this.setText(s);
    }
}
