package game;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static game.Color.*;
import static org.junit.Assert.*;

/**
 *
 */
public class PlayerTest {
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
    public void setColor() throws Exception {
        Player testPlayer = new Player("test", RED);
        testPlayer.setColor(WHITE);
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

    @Test
    public void setColorNone() throws Exception {
        Player testPlayer = new Player("test", RED);
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Player cannot have color 'None'");
        testPlayer.setColor(NONE);
    }
}