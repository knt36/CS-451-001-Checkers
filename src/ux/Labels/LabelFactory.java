package ux.Labels;

import ux.Screens.STRINGS;
import ux.Screens.STYLE;

import javax.swing.*;

class LabelFactory extends JLabel {
    LabelFactory(String label) {
        this.setText(STRINGS.DIVTEXTCENTER + label + STRINGS.ENDDIV);
        this.setBackground(STYLE.BACKGROUND);
        this.setHorizontalAlignment(CENTER);
    }
}
