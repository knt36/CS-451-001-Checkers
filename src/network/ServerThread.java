package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import database.DBWrapper;
import game.Game;
import network.messages.Ack;
import network.messages.Message;
import network.messages.Packet;

/**
 * This is the thread handler for the server.
 */
public class ServerThread extends Thread {
    private Socket socket;

    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            // Get messages from the client, line by line
            while (true) {
                String input = in.readLine();
                Packet packet = Packet.fromJson(input);
                if (input == null || input.equals(".")) {
                    break;
                }
                if (packet == null || packet.getData() == null) {
                    out.write(Packet.error("Could not parse data"));
                    break; // Error from client side, nothing to do
                }
                Packet result = process(packet);
                String output = result.toJson();
                if (output == null) {
                    break; // We fucked up, this is bad
                }
                out.write(output);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Packet process(Packet packet) {
        Message message = packet.getData();
        switch (packet.getData().type()) {
            case GAME:
                DBWrapper db = new DBWrapper();
                db.saveGame((Game) message);
                return new Packet(packet.getToken(), new Ack("Saved game", true));
            case LOGIN:
                return new Packet(generateToken(), new Ack("Logged in", true));
            case SIGNUP:
                return new Packet(generateToken(), new Ack("Created user", true));
        }
        return Packet.perror("Invalid message type");
    }

    public String generateToken() {
        return null;
    }

    private void createUserRecord(String username, String token) {
    }

    private Boolean destroyToken(String token) {
        return false;
    }

    private Boolean pruneTokens() {
        return false;
    }

}
