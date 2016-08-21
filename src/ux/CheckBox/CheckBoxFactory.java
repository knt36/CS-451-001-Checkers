package ux.CheckBox;

import ux.Screens.STYLE;

import javax.swing.*;

public class CheckBoxFactory extends JCheckBox {
    public CheckBoxFactory(String text) {
        this.setBackground(STYLE.BACKGROUND);
        this.setText(text);
    }
}
