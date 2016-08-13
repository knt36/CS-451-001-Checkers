package main;

import ux.Screens.*;

/**
 * Created by rachelgoeken on 8/13/16.
 */
public class mainDriver {
    public static void main (String args []){
        FrameMain loginFr = new FrameMain();
        loginFr.addComp(new ScrLogin(loginFr));

    }
}
