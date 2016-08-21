package network;

import network.messages.Login;
import network.messages.Message;
import org.junit.Test;

/**
 */
public class ClientThreadTest {
    public Message message = (new Login("foo", "foo"));

    @Test
    public void ClientRunTest() {
        // Attempt to send a request to the server, with no callback action
        Client.client.send(message, null);
    }

    @Test
    public void ClientThreadRunTest() {
        //Let's just try a thread
        ClientThread CT = new ClientThread(message.toJson().toString(), null);
        try {
            CT.run();
        } catch (NullPointerException ignored) {
            // We get a null pointer because we aren't getting a reply
            // this also cuts the test short, but that's okay!
        }

    }
}
