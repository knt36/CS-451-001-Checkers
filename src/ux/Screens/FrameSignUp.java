package ux.Screens;

import java.awt.Dimension;

public class FrameSignUp extends FrameMain {
	protected static final int MAXAMOUNT = 1;
	protected static int thisInstances = 0;
	public FrameSignUp() {
		// TODO Auto-generated constructor stub
		super();
		thisInstances++;
		if(thisInstances > MAXAMOUNT){
			this.dispose();
		}
		setSize(new Dimension(400, 400));
	}
	
	//Code to prevent multiple sign ups to appear. Prevent something bad from happening.
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		thisInstances--;
		super.dispose();
	}
}
