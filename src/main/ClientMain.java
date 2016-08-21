package main;

import java.io.File;

import ux.Screens.FrameMain;
import ux.Screens.ScrLogin;


public class ClientMain {
	public static void main(String args []){
		System.out.println(new File(".").getAbsolutePath());
		//System.setProperty("javax.net.debug", "ssl");
		FrameMain loginFr = new FrameMain();
		loginFr.add(new ScrLogin());
	}
}