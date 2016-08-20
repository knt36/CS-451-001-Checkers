package ux.Utilities;

import org.junit.Test;
import ux.Labels.NoteLabel;

import static org.junit.Assert.*;

/**
 * Created by rachelgoeken on 8/18/16.
 */
public class TimingTest {
    @Test
    public void run() throws Exception {
        NoteLabel j = new NoteLabel("hi");
        NoteLabel l = new NoteLabel("hi");
        Thread t = new Thread(new Timing(l));
        t.start();
        t.sleep(10);
        t.interrupt();
        assertNotEquals(l, j);
    }

}