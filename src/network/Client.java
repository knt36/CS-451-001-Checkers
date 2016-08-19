package network;

import java.util.Observable;
import java.util.function.Consumer;

import network.messages.Login;
import network.messages.Message;
import network.messages.MessageTypes;
import network.messages.Packet;

/**
 *
 */
public class Client extends Observable {
    public static Client client = new Client();
    private String token = "";
    private String username = null;
    private Client() {

    }

    public void send(Message message, Consumer<Packet> callback) {
    	//Drawing information from message before being sent to server to save to the client.
    	if(message.type()==MessageTypes.LOGIN){
    		this.username =((Login)message).getUsername();
    	}
    	
        Packet packet = new Packet(token, message);
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
