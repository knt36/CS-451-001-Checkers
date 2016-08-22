package ux.Screens;

import game.Game;
import network.Client;
import org.junit.Test;
import ux.Utilities.FakeServer;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by rachelgoeken on 8/21/16.
 */
public class TestScrMainMenu {
    @Test
    public void SignUpScrTests() {
        Client.client = new FakeServer();
        FrameMain fr = new FrameMain();
        ScrMainMenuTest scr = new ScrMainMenuTest();
        fr.add(scr);

        scr.helpBt.doClick();
        assertEquals(true, scr.helpCalled.booleanValue());

        scr.newGameBt.doClick();
        assertEquals(true, scr.createGameCalled.booleanValue());

        scr.quitBt.doClick();
        assertEquals(true, scr.quitCalled.booleanValue());

    }

    private class ScrMainMenuTest extends ScrMainMenu {

        public Boolean quitCalled = false;
        public Boolean helpCalled = false;
        public Boolean createGameCalled = false;
        public Boolean publicGameCalled = false;
        public Boolean currentGameCalled = false;

        @Override
        public void nextFrameQuitBtn() {
            quitCalled = true;
        }

        @Override
        public void nextFrameHelpBtn() {
            helpCalled = true;
        }

        public void nextFrameNewGameBtn() {
            createGameCalled = true;
        }

        public void nextFrameMouseReleasedPublic(Game g) {
            publicGameCalled = true;
        }

        public void nextFrameMouseReleasedCurrent(Game g) {
            currentGameCalled = true;
        }
    }


}
