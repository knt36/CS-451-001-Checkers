package ux.CheckBox;

import ux.Screens.STYLE;

import javax.swing.*;

public class CheckBoxFactory extends JCheckBox {
    public CheckBoxFactory(String text) {
        // TODO Auto-generated constructor stub
        this.setBackground(STYLE.BACKGROUND);
        this.setText(text);
    }
}
