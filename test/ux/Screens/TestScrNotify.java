package ux.Screens;

import network.Client;
import org.junit.Test;
import ux.Utilities.FakeServer;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by rachelgoeken on 8/21/16.
 */
public class TestScrNotify {
    @Test
    public void NotifyScrTests() {
        Client.client = new FakeServer();

        FrameNotify fr = new FrameNotify();
        ScrNotifyTest scr = new ScrNotifyTest("Hi");
        fr.add(scr);

        scr.okBut.doClick();
        assertEquals(true, scr.okCalled.booleanValue());
    }

    private class ScrNotifyTest extends ScrNotify {

        public Boolean okCalled = false;

        ScrNotifyTest(String txt) {
            super(txt);
        }

        @Override
        public void nextFrameOkBtn() {
            okCalled = true;
        }
    }
}
