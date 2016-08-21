package ux.Screens;

import java.awt.*;

/**
 */
class FrameNotifyDisconnect extends FrameNotify {

    private static int counter = 0;

    FrameNotifyDisconnect() {
        super();
        counter++;
        if (counter <= 1) {
            setSize(new Dimension(500, 400));
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        } else {
            setVisible(false);
            dispose();
        }

    }

    static int getCounter() {
        return counter;
    }

    @Override
    public void dispose() {
        super.dispose();
        counter--;
    }
}
