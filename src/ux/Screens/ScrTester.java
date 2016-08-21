package ux.Screens;

import game.Game;

public class ScrTester {
    public static void main(String args[]) {
        FrameMain createGame = new FrameCreateGame();
        createGame.add(new ScrCreateGame());

        FrameMain mainMenu = new FrameMain();
        mainMenu.add(new ScrMainMenu());

        FrameMain loginFr = new FrameMain();
        loginFr.add(new ScrLogin());

        FrameMain signupFr = new FrameSignUp();
        signupFr.add(new ScrSignUp());

        FrameMain disconnectFr = new FrameNotify();
        disconnectFr.add(new ScrDisconnect());


        FrameMain gameFr = new FrameMain();
        gameFr.add(new ScrGame(new Game("BattleGround", "Rachel", "Khoi")));
    }
}
