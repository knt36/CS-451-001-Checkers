package ux.Utilities;

import org.junit.Test;
import ux.Labels.NoteLabel;

import static org.junit.Assert.assertNotEquals;

/**
 */
public class TimingTest {
    @Test
    public void run() throws Exception {
        NoteLabel j = new NoteLabel("hi");
        NoteLabel l = new NoteLabel("hi");
        Thread t = new Thread(new Timing(l));
        t.start();
        Thread.sleep(10);
        t.interrupt();
        assertNotEquals(l, j);
    }

}