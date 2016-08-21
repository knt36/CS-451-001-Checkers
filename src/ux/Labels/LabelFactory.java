package ux.Labels;

import ux.Screens.STRINGS;
import ux.Screens.STYLE;

import javax.swing.*;

public class LabelFactory extends JLabel {
    public LabelFactory(String label) {
        this.setText(STRINGS.DIVTEXTCENTER + label + STRINGS.ENDDIV);
        this.setBackground(STYLE.BACKGROUND);
        this.setHorizontalAlignment(CENTER);
    }
}
