package ux.Labels;

import ux.Screens.STYLE;


public class HeaderLabel extends LabelFactory {

    public HeaderLabel(String label) {
        super(label);
        this.setFont(STYLE.HEADERLABEL_FONT);
        this.setBorder(STYLE.HEADERBORDER);

    }
}
