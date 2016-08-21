package ux.Screens;

import network.Client;

import java.awt.*;

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
			ScrGame sg = (ScrGame)comp;
			if (Client.client.getUsername().compareTo(sg.game.p1.getName()) == 0) {
				this.setTitle(sg.game.name + ": (>" + sg.game.p1.getName() + " vs " + sg.game.p2.getName() + ")");
			} else {
				this.setTitle(sg.game.name + ": (" + sg.game.p1.getName() + " vs >" + sg.game.p2.getName() + ")");
			}

			//Add screen ender
			sg.addThreadEnder();
		}
		
		return(comp);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		super.dispose();
	}
}
