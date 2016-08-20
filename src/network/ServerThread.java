package network;

import database.DBWrapper;
import game.Game;
import game.GameList;
import network.messages.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.UUID;

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
            System.out.println("Connected to client");
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            // Get messages from the client, line by line
            String input = in.readLine();
            Packet packet = Packet.fromJson(input);
            Integer retries = 10;
            while (input == null || input.equals("") || input.equals(".")) {
                input = in.readLine();
                retries--;
                if (retries.equals(0)) {
                    System.out.println("No data received, disconnecting");
                    return;
                }
            }
            System.out.println("Got " + input);
            if (packet == null || packet.getData() == null) {
                out.write(Packet.error("Could not parse data"));
            }
            Packet result = process(packet);
            String output = result.toJson();
            if (output == null) {
                output = Packet.error("Server error, failed to process data");
            }
            System.out.println("Sending: " + output);
            out.write(output + "\n");
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                System.out.println("Closing Socket");
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Packet process(Packet packet) {
        Message message = packet.getData();
        String token = packet.getToken();
        switch (packet.getData().type()) {
            case GAME:
                return new Packet(token, updateGame((Game) message));
            case LOGIN:
                token = login((Login) message);
                if(token != null && !token.equals("")) {
                    return new Packet(token, new Ack("Logged in", true));
                } else {
                    return new Packet("", new Ack("Incorrect username or password", false));
                }
            case SIGNUP:
                token = signup((Signup) message);
                if(token != null && !token.equals("")) {
                    return new Packet(token, new Ack("Logged in", true));
                } else {
                    return new Packet("", new Ack("Incorrect username or password", false));
                }
            case GAME_LIST_REQUEST:
                GameList gameList = getGameList((GameListRequest) message);
                return new Packet(token, gameList);
            case GAME_REQUEST:
                Game game = getGame((GameRequest) message);
                return new Packet(token, game);
        }
        return Packet.perror("Invalid message type");
    }

    private Game updateGame(Game clientGame) {
        DBWrapper db = new DBWrapper();
        Game serverGame = db.getGame(clientGame.name);
        if(serverGame.move(clientGame).success()) {
            db.saveGame(serverGame);
        }
        // Whether the update happened or not, the server version is the source of truth, so send it back down
        return serverGame;
    }

    // Returns token if successful, else null or ""
    private String login(Login login) {
        DBWrapper db = new DBWrapper();
        return UUID.randomUUID().toString();
    }

    // Returns token if successful, else null or ""
    private String signup(Signup signup) {
        DBWrapper db = new DBWrapper();
        return login(signup);
    }

    private GameList getGameList(GameListRequest request) {
        DBWrapper db = new DBWrapper();
        return new GameList(db.getPublicGames(request.user), db.getPrivateGames(request.user));
    }

    private Game getGame(GameRequest request) {
        DBWrapper db = new DBWrapper();
        return db.getGame(request.name);
    }
}
