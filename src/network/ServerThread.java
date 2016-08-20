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
	System.out.println("Bricke 2");
            while (input == null || input.equals("") || input.equals(".")) {
                input = in.readLine();
                retries--;
                if (retries.equals(0)) {
                    System.out.println("No data received, disconnecting");
			try{
				this.socket.close();
			}catch(Exception e){
				System.out.println("Failed to close socket on no data recieved");
			}
		    return;
                }
            }
            System.out.println("Got " + input);
            if (packet == null || packet.getData() == null) {
                out.write(Packet.error("Could not parse data"));
            }
		System.out.println("Brick3");
            Packet result = process(packet);
		System.out.println("Brick5");
            String output = result.toJson();
		System.out.println("Brike4");
            if (output == null) {
		out.write(Packet.error("Server side error"));
            }
	System.out.println("Brick 1");
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
	System.out.println("Brike9");
        switch (packet.getData().type()) {
            case GAME:
		System.out.println("Brick7");
                return new Packet(token, updateGame((Game) message));
            case LOGIN:
                token = login((Login) message);
		System.out.println("Brikck6");
                if(token != null && !token.equals("")) {
                    return new Packet(token, new Ack("Logged in", true));
                } else {
                    return new Packet("", new Ack("Incorrect username or password", false));
                }
            case SIGNUP:
		System.out.println("Brike10");
                token = signup((Signup) message);
                if(token != null && !token.equals("")) {
                    return new Packet(token, new Ack("Logged in", true));
                } else {
                    return new Packet("", new Ack("Incorrect username or password", false));
                }
            case GAME_LIST_REQUEST:
		System.out.println("Brike11");
                GameList gameList = getGameList((GameListRequest) message);
                return new Packet(token, gameList);
            case GAME_REQUEST:
		System.out.println("Brik12");
                Game game = getGame((GameRequest) message);
                return new Packet(token, game);
        }
	System.out.println("Brike13");
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

    private void createUserRecord(String username, String token) {
    }

    private Boolean destroyToken(String token) {
        return false;
    }

    private Boolean pruneTokens() {
        return false;
    }

}
