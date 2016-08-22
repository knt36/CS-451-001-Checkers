package ux.Labels;

import ux.Screens.STYLE;

public class NoteLabel extends LabelFactory {

    public NoteLabel(String label) {
        super(label);
        this.setFont(STYLE.NOTELABELFONT);
        this.setHorizontalAlignment(CENTER);
        this.setHorizontalTextPosition(CENTER);

    }
}
