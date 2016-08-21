package ux.Labels;

import ux.Screens.STYLE;

public class NoteLabel extends LabelFactory {

    public NoteLabel(String label) {
        super(label);
        // TODO Auto-generated constructor stub
        this.setFont(STYLE.NOTELABELFONT);
        this.setHorizontalAlignment(CENTER);
        this.setHorizontalTextPosition(CENTER);

    }
}
