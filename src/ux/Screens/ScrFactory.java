package ux.Screens;

import javax.swing.*;
import java.awt.*;

public class ScrFactory extends JPanel {
    protected GridBagConstraints constr = new GridBagConstraints();
    FrameMain frame = null;


    protected ScrFactory() {
        this.constr.weightx = 1;
        this.constr.weighty = 1;
        this.constr.gridwidth = 1;
        this.constr.gridheight = 1;
        this.constr.gridx = 0;
        this.constr.gridy = 0;
        this.constr.fill = GridBagConstraints.BOTH;
        this.setBackground(STYLE.BACKGROUND);
        this.setBorder(BorderFactory.createLineBorder(STYLE.PANELBORDERCOLOR, STYLE.PANELBORDERSIZE, true));
        this.setLayout(new GridBagLayout());
    }

    @Override
    public Component add(Component comp) {
        super.add(comp, this.constr);
        this.revalidate();
        this.repaint();
        return (comp);
    }
}
