package ux.Screens;

import game.Game;
import network.Client;
import org.junit.Assert;
import org.junit.Test;
import ux.Utilities.FakeServer;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by roy_f on 8/21/2016.
 */
public class TestScrDeleteConfirm {
    protected boolean okPressed = false;
    protected boolean cancelPressed = false;

    private class ScrDeleteConfirmTest extends ScrDeleteConfirm {
        public ScrDeleteConfirmTest(ScrGame lastScrGame, String lastGame) {
            super(lastScrGame, lastGame);
        }

        @Override
        public void pressedOk() {
            okPressed = true;
        }

        @Override
        public void pressedCancel() {
            cancelPressed = true;
        }
    }

    @Test
    public void LoggingScrDeleteConfirmTest(){
        ScrGame scrGame = new ScrGame(new Game("Hello", "tran"));
        ScrDeleteConfirm scrDeleteConfirm = new ScrDeleteConfirmTest(scrGame,scrGame.game.name);

        scrDeleteConfirm.okBut.doClick();
        scrDeleteConfirm.cancelBut.doClick();

        Assert.assertTrue(okPressed);
        Assert.assertTrue(cancelPressed);
    }
}
