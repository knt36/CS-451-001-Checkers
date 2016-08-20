package UXTest;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.nio.charset.MalformedInputException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import game.Game;
import ux.Screens.FrameCreateGame;
import ux.Screens.FrameMain;
import ux.Screens.FrameNotify;
import ux.Screens.FrameSignUp;
import ux.Screens.ScrCreateGame;
import ux.Screens.ScrDeleteConfirm;
import ux.Screens.ScrDisconnect;
import ux.Screens.ScrGame;
import ux.Screens.ScrLogin;
import ux.Screens.ScrMainMenu;
import ux.Screens.ScrSignUp;

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
