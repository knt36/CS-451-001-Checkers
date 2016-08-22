package ux.Screens;

import network.Client;
import org.junit.Test;
import ux.Utilities.FakeServer;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by rachelgoeken on 8/21/16.
 */
public class TestScrSignUp {

    @Test
    public void SignUpScrTests() {
        Client.client = new FakeServer();

        FrameMain login = new FrameMain();


        FrameSignUp signUpFr = new FrameSignUp();
        ScrSignUpTest scr = new ScrSignUpTest();
        login.OpenLinkFrame(login, scr);

        scr.userName.setText("Name12");
        scr.passWord.setText("Password12");

        assertEquals(scr.userName.getText(), "Name12");
        assertEquals(new String(scr.passWord.getPassword()), "Password12");

        scr.createBt.doClick();

        scr.quitBt.doClick();
        assertEquals(true, scr.quitCalled.booleanValue());
    }

    private class ScrSignUpTest extends ScrSignUp {

        public Boolean quitCalled = false;

        @Override
        public void nextFrameQuitBtn() {
            quitCalled = true;
        }
    }


}
