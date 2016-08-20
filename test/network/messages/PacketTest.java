package network.messages;

import game.Game;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 *
 */
public class PacketTest {
    // Dummy token, this is close to what the real data will be
    // TODO: Import implementation from the Packet Class
    public static final String token = UUID.randomUUID().toString();

    @Test
    public void game() throws Exception {
        Game testData = new Game("game", "user");
        Packet test = new Packet(token, testData);
        String json = test.toJson();
        System.out.println(json);
        Packet result = Packet.fromJson(json);
        // Just pulling out board so I don't have to write an equals method that will never get used
        assertNotNull(result);
        assertEquals(
                ((Game) test.getData()).board,
                ((Game) result.getData()).board
        );
    }

    @Test
    public void Login() throws Exception {
        // Make basic login's, one constructed, one set
        Login LoginTrue = new Login("foo", "bar");

        Login LoginFalse = new Login();
        LoginFalse.setPassword("bar");
        LoginFalse.setUsername("baz");

        // Make packets from login's
        Packet testTrue = new Packet(token, LoginTrue);
        Packet testFalse = new Packet(token, LoginFalse);

        // Make JSON from packets
        String jsonTrue = testTrue.toJson();
        String jsonFalse = testFalse.toJson();

        // Perform test Battery on constructed
        System.out.println("Constructed Login packet: " + jsonTrue);
        Packet resultTrue = Packet.fromJson(jsonTrue);
        assertNotNull(resultTrue);
        assertEquals((Login) resultTrue.getData(), testTrue.getData());

        // Perform test Battery on generated
        System.out.println("Generated Login packet: " + jsonFalse);
        Packet resultFalse = Packet.fromJson(jsonFalse);
        assertNotNull(resultFalse);
        assertEquals((Login) resultFalse.getData(), testFalse.getData());

    }

    @Test
    public void ack() throws Exception {
        // Make basic ack's, one true one false
        Ack ackTrue = new Ack("foo", true);
        Ack ackFalse = new Ack("bar", false);

        // Make packets from ack's
        Packet testTrue = new Packet(token, ackTrue);
        Packet testFalse = new Packet(token, ackFalse);

        // Make JSON from packets
        String jsonTrue = testTrue.toJson();
        String jsonFalse = testFalse.toJson();

        // Perform test Battery on True
        System.out.println("True Ack packet: " + jsonTrue);
        Packet resultTrue = Packet.fromJson(jsonTrue);
        assertNotNull(resultTrue);
        assertEquals((Ack) resultTrue.getData(), testTrue.getData());

        // Perform test Battery on False
        System.out.println("False Ack packet: " + jsonFalse);
        Packet resultFalse = Packet.fromJson(jsonFalse);
        assertNotNull(resultFalse);
        assertEquals((Ack) resultFalse.getData(), testFalse.getData());

    }

    @Test
    public void GameRequest() throws Exception {
        // Make basic GameRequest's, one constructed, one set
        GameRequest GameRequestTrue = new GameRequest("test");
        // Make packets from GameRequest's
        Packet testTrue = new Packet(token, GameRequestTrue);

        // Make JSON from packets
        String jsonTrue = testTrue.toJson();

        // Perform test Battery on Request
        System.out.println("Constructed GameRequest packet: " + jsonTrue);
        Packet resultTrue = Packet.fromJson(jsonTrue);
        assertNotNull(resultTrue);
        assertEquals((GameRequest) resultTrue.getData(), testTrue.getData());

    }
}

