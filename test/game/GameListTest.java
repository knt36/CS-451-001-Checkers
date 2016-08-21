package game;

import network.messages.MessageTypes;
import network.messages.Packet;
import org.junit.Test;

import static java.util.Arrays.asList;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 *
 */
public class GameListTest {

    @Test
    public void jsonTest() {
        GameList test = new GameList(asList(new Game("blah", "blargh")), asList(new Game("bler", "blee")));
        String json = new Packet("", test).toJson();
        GameList result = (GameList) Packet.fromJson(json).getData();
        assertEquals(json, new Packet("", result).toJson());
    }

    @Test
    public void functionTest() {
        // First == third, (first, third) != second
        GameList first = new GameList(asList(new Game("blah", "blargh")), asList(new Game("bler", "blee")));
        GameList second = new GameList(asList(new Game("bler", "blee")), asList(new Game("blah", "blargh")));
        GameList third = new GameList(asList(new Game("blah", "blargh")), asList(new Game("bler", "blee")));

        // check identities
        assertTrue(first.equals(third));
        assertFalse(second.equals(first));
        assertFalse(second.equals(third));

        // Now that identity is correct, check hashcode function
        // this must be deterministic, we repeat it
        assertEquals(first.hashCode(), third.hashCode());
        assertEquals(first.hashCode(), third.hashCode());

        assertNotEquals(first.hashCode(), second.hashCode());
        assertNotEquals(first.hashCode(), second.hashCode());

        assertNotEquals(third.hashCode(), second.hashCode());
        assertNotEquals(third.hashCode(), second.hashCode());

        // This should also result in the same objects.
        assertEquals(first.allGames(), third.allGames());
        assertNotEquals(second.allGames(), third.allGames());
        assertNotEquals(second.allGames(), third.allGames());

        // Does this return the proper type when you interrogate it?
        assertEquals(first.type(), MessageTypes.GAME_LIST);
        assertEquals(second.type(), MessageTypes.GAME_LIST);
        assertEquals(third.type(), MessageTypes.GAME_LIST);


    }
}