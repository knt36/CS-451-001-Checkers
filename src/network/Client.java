package network;

import java.util.Observable;
import java.util.function.Consumer;

/**
 *
 */
public class Client extends Observable {
    public static Client client = new Client();
    private String token = "";

    private Client() {

    }

    public void send(Object obj, Consumer<Packet> callback) {
        Packet packet = new Packet(token, obj);
        String json = packet.toJson();
        if (json == null) {
            callback.accept(Packet.perror("Invalid data"));
        }
        sendData(json, callback);
    }

    private void sendData(String data, Consumer<Packet> callback) {
        new ClientThread(data, callback).start();
    }

}
