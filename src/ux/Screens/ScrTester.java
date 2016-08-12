package ux.Screens;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.nio.charset.MalformedInputException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import game.Game;

public class ScrTester {
	public static void main (String args []){ 
		FrameMain createGame = new FrameCreateGame();
		createGame.addComp(new ScrCreateGame());
		
		FrameMain mainMenu = new FrameMain();
		mainMenu.addComp(new ScrMainMenu());
		
		FrameMain loginFr = new FrameMain();
		loginFr.addComp(new ScrLogin());
		
		FrameMain signupFr = new FrameSignUp();
		signupFr.addComp(new ScrSignUp());
		
		FrameMain disconnectFr = new FrameNotify();
		disconnectFr.addComp(new ScrDisconnect());
		

		FrameMain deleteFr = new FrameNotify();
		deleteFr.addComp(new ScrDeleteConfirm());
		
		FrameMain gameFr = new FrameMain();
		gameFr.addComp(new ScrGame(new Game("BattleGround", "Rachel", "Khoi")));
	}
}
