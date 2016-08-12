package ux.Screens;

import java.awt.TextField;

import ux.Labels.NoteLabel;

public class Timing implements Runnable{
	
	NoteLabel l = null;
	public Timing(NoteLabel l) {
		// TODO Auto-generated constructor stub
		this.l = null;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try{
			Thread.sleep(1000);
			l.setText("HELLLO");
		}catch(Exception e){
		
		}
		
	}

}
