package network;

import game.Game;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 *
 */
public class PacketTest {
    @Test
    public void json() throws Exception {
        Game testData = new Game("game", "user");
        Packet test = new Packet("token", testData);
        String json = test.toJson();
        System.out.println(json);
        Packet result = Packet.fromJson(json);
        // Just pulling out board so I don't have to write an equals method that will never get used
        assertEquals(Game.class, result.getType());
        assertEquals(
                ((Game) test.getData()).board,
                ((Game) result.getData()).board
        );
    }
}