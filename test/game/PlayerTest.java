package game;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static game.Color.*;
import static org.junit.Assert.assertEquals;

/**
 *
 */
public class PlayerTest {
    @Test
    public void opponent1() throws Exception {

    }

    @Test
    public void getColor2() throws Exception {

    }

    @Test
    public void getName1() throws Exception {

    }

    @Test
    public void oppositeColor2() throws Exception {

    }

    @Test
    public void nobody1() throws Exception {

    }

    @Test
    public void opponent() throws Exception {

    }

    @Test
    public void getColor1() throws Exception {

    }

    @Test
    public void getName() throws Exception {

    }

    @Test
    public void oppositeColor1() throws Exception {

    }

    @Test
    public void nobody() throws Exception {

    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getColor() throws Exception {
        Player testPlayer = new Player("test", RED);
        assertEquals(testPlayer.getColor(), RED);
        testPlayer = new Player("test", WHITE);
        assertEquals(testPlayer.getColor(), WHITE);
    }

    @Test
    public void oppositeColor() throws Exception {
        Player testPlayer = new Player("test", RED);
        assertEquals(testPlayer.oppositeColor(), WHITE);
        testPlayer = new Player("test", WHITE);
        assertEquals(testPlayer.oppositeColor(), RED);
    }

    @Test
    public void newColorNone() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Player cannot have color 'None'");
        Player testPlayer = new Player("test", NONE);
    }
}