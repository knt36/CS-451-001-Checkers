package ux;

import game.Game;
import org.junit.Test;
import ux.Screens.*;

public class ScreenTester {

    @Test
    public void test() {
        //testing create game
        FrameMain createGame = new FrameCreateGame();
        ScrCreateGame scrCreateGame = new ScrCreateGame();
        createGame.add(scrCreateGame);
        createGame.dispose();

        FrameMain mainMenu = new FrameMain();
        ScrMainMenu scrMainMenu = new ScrMainMenu();
        mainMenu.add(scrMainMenu);
        mainMenu.dispose();

        FrameMain loginFr = new FrameMain();
        ScrLogin scrLogin = new ScrLogin();
        loginFr.add(scrLogin);
        loginFr.dispose();

        FrameMain signupFr = new FrameSignUp();
        ScrSignUp scrSignUp = new ScrSignUp();
        signupFr.add(scrSignUp);
        signupFr.dispose();

        FrameMain disconnectFr = new FrameNotify();
        ScrDisconnect srcDisconnect = new ScrDisconnect();
        disconnectFr.add(srcDisconnect);
        disconnectFr.dispose();

        FrameMain gameFr = new FrameMain();
        ScrGame scrGame = new ScrGame(new Game("BattleGround", "Rachel", "Khoi"));
        gameFr.add(scrGame);

        FrameMain deleteFr = new FrameNotify();
        ScrDeleteConfirm scrDeleteConfirm = new ScrDeleteConfirm(scrGame, "");
        deleteFr.add(scrDeleteConfirm);
    }

}
