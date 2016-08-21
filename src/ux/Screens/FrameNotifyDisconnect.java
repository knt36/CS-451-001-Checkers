package ux.Screens;

import java.awt.*;

/**
 * Created by rachelgoeken on 8/21/16.
 */
public class FrameNotifyDisconnect extends FrameNotify {

    protected static int counter = 0;

    public FrameNotifyDisconnect() {
        super();
        counter++;
        if(counter <= 1){
            setSize(new Dimension(500, 400));
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        } else {
            setVisible(false);
            dispose();
        }

    }

    @Override
    public void dispose() {
        super.dispose();
        counter--;
    }

    public static int getCounter(){
        return counter;
    }
}
