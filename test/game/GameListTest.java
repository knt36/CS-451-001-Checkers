package game;

import network.messages.Packet;
import org.junit.Test;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

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
}