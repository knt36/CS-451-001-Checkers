package ux.Screens;

import network.Client;
import org.junit.Test;
import ux.Utilities.FakeServer;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by rachelgoeken on 8/21/16.
 */
public class TestScrLogin {

    @Test
    public void LogingScrTests() {
        Client.client = new FakeServer();
        FrameMain loginFr = new FrameMain();
        ScrLoginTest scr = new ScrLoginTest();
        loginFr.add(scr);

        scr.userName.setText("Name12");
        scr.passWord.setText("Password12");

        assertEquals(scr.userName.getText(), "Name12");
        assertEquals(new String(scr.passWord.getPassword()), "Password12");

        scr.signInBut.doClick();
        assertEquals(true, scr.signInCalled.booleanValue());

        scr.signUpBut.doClick();
        assertEquals(true, scr.signUpCalled.booleanValue());

        scr.quitBt.doClick();
        assertEquals(true, scr.quitCalled.booleanValue());

    }

    private class ScrLoginTest extends ScrLogin {
        public Boolean signUpCalled = false;
        public Boolean signInCalled = false;
        public Boolean quitCalled = false;

        @Override
        public void nextFrameSignUpBtn() {
            signUpCalled = true;
        }

        @Override
        public void nextFrameMainMenu() {
            signInCalled = true;
        }

        @Override
        public void nextFrameQuitBtn() {
            quitCalled = true;
        }
    }


}
