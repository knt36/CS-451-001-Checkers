package ux.Screens;

import network.Client;
import org.junit.Test;
import ux.Utilities.FakeServer;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by rachelgoeken on 8/21/16.
 */
public class TestScrNotify {
    private class ScrNotifyTest extends ScrNotify {

        ScrNotifyTest(String txt){
            super(txt);
        }

        public Boolean okCalled = false;

        @Override
        public void nextFrameOkBtn(){
            okCalled = true;
        }
    }

    @Test
    public void NotifyScrTests(){
        Client.client = new FakeServer();

        FrameNotify fr = new FrameNotify();
        ScrNotifyTest scr = new ScrNotifyTest("Hi");
        fr.add(scr);

        scr.okBut.doClick();
        assertEquals(true, scr.okCalled.booleanValue());
    }
}
