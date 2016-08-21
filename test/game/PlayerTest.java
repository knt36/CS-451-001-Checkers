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
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getColor() {
        Player testPlayer = new Player("test", RED);
        assertEquals(testPlayer.getColor(), RED);
        testPlayer = new Player("test", WHITE);
        assertEquals(testPlayer.getColor(), WHITE);
    }

    @Test
    public void oppositeColor() {
        Player testPlayer = new Player("test", RED);
        assertEquals(testPlayer.oppositeColor(), WHITE);
        testPlayer = new Player("test", WHITE);
        assertEquals(testPlayer.oppositeColor(), RED);
    }

    @Test
    public void newColorNone() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Player cannot have color 'None'");
        Player testPlayer = new Player("test", NONE);
    }
}