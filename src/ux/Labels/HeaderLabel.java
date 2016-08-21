package ux.Labels;

import ux.Screens.STYLE;


public class HeaderLabel extends LabelFactory {

    public HeaderLabel(String label) {
        super(label);
        // TODO Auto-generated constructor stub
        this.setFont(STYLE.HEADERLABEL_FONT);
        this.setBorder(STYLE.HEADERBORDER);

    }
}
