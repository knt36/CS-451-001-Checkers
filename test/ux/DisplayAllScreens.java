package ux;

import ux.Screens.FrameCreateGame;
import ux.Screens.FrameMain;
import ux.Screens.ScrCreateGame;

public class DisplayAllScreens {
	public static void main (String args []){ 
		FrameMain createGame = new FrameCreateGame();
		ScrCreateGame scrCreateGame = new ScrCreateGame();
		createGame.add(scrCreateGame);		
		
//		FrameMain mainMenu = new FrameMain();
//		mainMenu.add(new ScrMainMenu());
//		
//		FrameMain loginFr = new FrameMain();
//		loginFr.add(new ScrLogin());
//		
//		FrameMain signupFr = new FrameSignUp();
//		signupFr.add(new ScrSignUp());
//		
//		FrameMain disconnectFr = new FrameNotify();
//		disconnectFr.add(new ScrDisconnect());
//		
//
//		FrameMain deleteFr = new FrameNotify();
//		deleteFr.add(new ScrDeleteConfirm());
//		
//		FrameMain gameFr = new FrameMain();
//		gameFr.add(new ScrGame(new Game("BattleGround", "Rachel", "Khoi")));
	}
}
