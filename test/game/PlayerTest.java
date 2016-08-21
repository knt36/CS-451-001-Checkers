package game;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static game.Color.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

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
    public void equalsTest() {

        Player first = new Player("test", RED);
        Player third = new Player("test", RED);
        Player second = new Player("test1", WHITE);

        // check identities
        assertEquals(first, third);
        assertNotEquals(second, first);
        assertNotEquals(second, third);

        // Now that identity is correct, check hashcode function
        // this must be deterministic, we repeat it
        assertEquals(first.hashCode(), third.hashCode());
        assertEquals(first.hashCode(), third.hashCode());

        assertNotEquals(first.hashCode(), second.hashCode());
        assertNotEquals(first.hashCode(), second.hashCode());

        assertNotEquals(third.hashCode(), second.hashCode());
        assertNotEquals(third.hashCode(), second.hashCode());

    }
}