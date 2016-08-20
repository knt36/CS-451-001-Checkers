package network;

import network.messages.Login;
import network.messages.Message;
import network.messages.Packet;

import java.util.Observable;
import java.util.function.Consumer;

/**
 *
 */
public class Client extends Observable {
    public static Client client = new Client();
    private String token = "";
    private String username = null; //Keep as null so it returns an error and we know why
    private Client() {

    }

    public void send(Message message, Consumer<Packet> callback) {
        switch (message.type()) {
            case LOGIN:
            case SIGNUP:
                this.username = ((Login) message).getUsername();
        }
        Packet packet = new Packet(token, message);
        String json = packet.toJson();
        if (json == null) {
            callback.accept(Packet.perror("Invalid data"));
        }
        sendData(json, callback);
    }

    public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	private void sendData(String data, Consumer<Packet> callback) {
        new ClientThread(data, callback).start();
    }

}
