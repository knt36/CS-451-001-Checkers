package ux.Utilities;

import database.UserList;
import game.Game;
import game.GameList;
import network.Client;
import network.messages.*;

import java.util.function.Consumer;

/**
 * Created by rachelgoeken on 8/21/16.
 */
public class FakeServer extends Client {
    public FakeServer() {

    }

    @Override
    public void send(Message message, Consumer<Packet> callback) {
        callback.accept(new Packet("Login", message));
    }

    public Packet process(Packet packet) {
        Message message = packet.getData();
        switch (packet.getData().type()) {
            case LOGIN:
                Login login = (Login) message;
                System.out.println("Logging in: " + login.getUsername());
                String token = "kalfjibhgre493oe";
                if(token != null && !token.equals("")) {
                    return new Packet(token, new Ack("Logged in", true));
                } else {
                    return Packet.perror("Incorrect username or password");
                }
            case ACK:
                return packet;
            default:
                System.out.println("Received unexpected message from client: " + message.toJson());
                return Packet.perror("Unexpected message");
        }
    }

}
