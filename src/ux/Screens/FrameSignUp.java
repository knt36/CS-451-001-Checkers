package ux.Screens;

import java.awt.*;

public class FrameSignUp extends FrameMain {
    private static final int MAXAMOUNT = 1;
    private static int thisInstances = 0;

    public FrameSignUp() {
        super();
        thisInstances++;
        if (thisInstances > MAXAMOUNT) {
            this.dispose();
        }
        setSize(new Dimension(400, 400));
    }

    //Code to prevent multiple sign ups to appear. Prevent something bad from happening.
    @Override
    public void dispose() {
        thisInstances--;
        super.dispose();
    }
}
