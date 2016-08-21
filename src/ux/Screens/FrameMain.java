package ux.Screens;

import javax.swing.*;
import java.awt.*;

public class FrameMain extends JFrame {
    protected static int instances = 0;
    protected FrameMain link = null;
    protected GridBagConstraints constr = new GridBagConstraints();

    public FrameMain() {
        instances++;
        this.setLayout(new GridBagLayout());
        constr.weightx = 1;
        constr.weighty = 1;
        constr.fill = GridBagConstraints.BOTH;
        this.setSize(800, 500);
        this.setVisible(true);
        this.setBackground(STYLE.BACKGROUND);
        this.show();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    @Override
    public Component add(Component comp) {
        super.add(comp, this.constr);
        //Everytime you add a component to the frame, if it is a ScrFactory made frame,
        //It will give the screen the reference to the frame that it is in for closing purposes mostly.
        if (comp instanceof ScrFactory) {
            ((ScrFactory) comp).frame = this;
        }
        this.revalidate();
        this.repaint();
        return null;
    }

    @Override
    public void removeAll() {
        super.removeAll();
        revalidate();
        repaint();
    }

    public void addComp(Component comp) {
        super.add(comp, this.constr);
        this.validate();
        this.repaint();
    }

    public void OpenLinkFrame(FrameMain frame, JPanel screen) {
        frame.link = this;
        frame.add(screen);
    }

    @Override
    public void dispose() {
        super.dispose();
    }


}
