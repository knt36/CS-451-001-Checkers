package main;

import ux.Screens.FrameMain;
import ux.Screens.ScrLogin;

public class ClientMain {
	public static void main(String args []){
		FrameMain loginFr = new FrameMain();
		loginFr.add(new ScrLogin());
	}
}