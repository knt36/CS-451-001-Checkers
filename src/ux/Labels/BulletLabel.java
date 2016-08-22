package ux.Labels;

import ux.Screens.STRINGS;
import ux.Screens.STYLE;

public class BulletLabel extends LabelFactory {
    boolean pressed = false;

    public BulletLabel(String label) {
        super(STRINGS.BULLETCHAR + label);
        this.setFont(STYLE.BULLETLABEL_FONT);
    }
}
