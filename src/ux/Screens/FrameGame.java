package ux.Screens;

import java.awt.Component;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class FrameGame extends FrameMain {
	public FrameGame() {
		// TODO Auto-generated constructor stub
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	@Override
	public Component add(Component comp) {
		// TODO Auto-generated method stub
		super.add(comp);
		if(comp instanceof ScrGame){
		this.addComponentListener(new ComponentListener() {
			ScrGame sg = (ScrGame)comp;
			@Override
			public void componentShown(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void componentResized(ComponentEvent e) {
				// TODO Auto-generated method stub
				//sg.board.repaintAllButtons();
			}
			
			@Override
			public void componentMoved(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void componentHidden(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		}
		
		return(comp);
	}
}
