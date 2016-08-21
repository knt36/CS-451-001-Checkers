package ux.Buttons;

import ux.Screens.STYLE;

import javax.swing.*;
import java.awt.*;

public class OptionButton extends ButtonFactory {

    public OptionButton(Color c, String s) {
        super(c, s);
        this.setPreferredSize(new Dimension(200, 50));
        this.setFont(STYLE.OPTBUTFONT);
        this.setBorder(BorderFactory.createLineBorder(STYLE.OPTIONBORDER, STYLE.OPTIONBORDERSIZE, true));
        this.setForeground(STYLE.OPTIONTEXTCOLOR);
    }
}
