package ux.Screens;

import network.Client;
import network.messages.Message;
import network.messages.Packet;
import org.junit.Test;
import ux.TextField.TextField;
import ux.Utilities.FakeServer;

import java.util.function.Consumer;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

/**
 * Created by rachelgoeken on 8/21/16.
 */
public class TestScrLogin {

    private class ScrLoginTest extends ScrLogin {
        public Boolean signUpCalled = false;
        public Boolean signInCalled = false;
        public Boolean quitCalled = false;

        @Override
        public void nextFrameSignUpBtn(){
            signUpCalled = true;
        }

        @Override
        public void nextFrameMainMenu(){
            signInCalled = true;
        }

        @Override
        public void nextFrameQuitBtn(){
            quitCalled = true;
        }
    }

    @Test
    public void LogingScrTests(){
        Client.client = new FakeServer();
        FrameMain loginFr = new FrameMain();
        loginFr.add(new ScrLogin());

        ScrLoginTest scr = new ScrLoginTest();
        scr.signUpBut.doClick();
        assertEquals(true, scr.signUpCalled.booleanValue());

        scr.userName.requestFocus();
        scr.userName.setText("Name12");
        scr.passWord.requestFocus();
        scr.passWord.setText("Password12");

        assertEquals(scr.userName.getText(), "Name12");
        assertEquals(scr.passWord.getText(), "Password12");

        scr.signInBut.doClick();
        assertTrue(scr.signInCalled);

        scr.quitBt.doClick();
        assertEquals(true, scr.quitCalled.booleanValue());

    }



}
