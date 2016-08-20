package UXTest;

import static org.junit.Assert.*;

import java.util.Scanner;

import org.junit.Test;

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

public class ScreenTester {

	@Test
	public void test() {
		//testing create game
		FrameMain createGame = new FrameCreateGame();
		ScrCreateGame scrCreateGame = new ScrCreateGame();
		createGame.add(scrCreateGame);
		TestUtilities.applyDictionaryScreen(scrCreateGame);
		createGame.dispose();
		
		FrameMain mainMenu = new FrameMain();
		ScrMainMenu scrMainMenu = new ScrMainMenu();
		mainMenu.add(scrMainMenu);
		TestUtilities.applyDictionaryScreen(scrMainMenu);
		mainMenu.dispose();
		
		FrameMain loginFr = new FrameMain();
		ScrLogin scrLogin = new ScrLogin();
		loginFr.add(scrLogin);
		TestUtilities.applyDictionaryScreen(scrLogin);
		loginFr.dispose();
		
		FrameMain signupFr = new FrameSignUp();
		ScrSignUp scrSignUp = new ScrSignUp();
		signupFr.add(scrSignUp);
		TestUtilities.applyDictionaryScreen(scrSignUp);
		signupFr.dispose();
		
		FrameMain disconnectFr = new FrameNotify();
		ScrDisconnect srcDisconnect = new ScrDisconnect();
		disconnectFr.add(srcDisconnect);
		TestUtilities.applyDictionaryScreen(srcDisconnect);
		disconnectFr.dispose();
		
		FrameMain deleteFr = new FrameNotify();
		ScrDeleteConfirm scrDeleteConfirm = new ScrDeleteConfirm();
		deleteFr.add(scrDeleteConfirm);
		TestUtilities.applyDictionaryScreen(scrDeleteConfirm);
	
		FrameMain gameFr = new FrameMain();
		ScrGame scrGame = new ScrGame(new Game("BattleGround", "Rachel", "Khoi"));
		gameFr.add(scrGame);
		
		return;
	}

}
