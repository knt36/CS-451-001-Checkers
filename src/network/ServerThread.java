package network;

import database.Credentials;
import database.DBWrapper;
import database.UserList;
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
    private final int MAX_PASSWORD_CHAR = 160;
    private final int MIN_PASSWORD_CHAR = 8;
    private Socket socket;
    private String token;
    private String user = null;
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
            String output;
            if (packet == null || packet.getData() == null) {
                output = Packet.error("Could not parse data");
            } else {
                this.token = packet.getToken();
                Packet result = process(packet);
                if (result == null) {
                    output = Packet.error("Server error, failed to process data");
                } else {
                    output = result.toJson();
                }
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
            case GAME_DELETE:
                Boolean success = deleteGame((GameDelete) message);
            case USER_LIST_REQUEST:
                UserList userList = getUserList(token, (UserListRequest) message);
                return new Packet(token, userList);
        }
        return Packet.perror("Invalid message type");
    }

    private Boolean deleteGame(GameDelete game) {
        return DBWrapper.deleteGame(game.name);
    }

    private UserList getUserList(String token, UserListRequest userListRequest) {
        return DBWrapper.getUsers(token, userListRequest.str);
    }

    private Game updateGame(Game clientGame) {
        this.user = DBWrapper.getUserByToken(token);
        Game serverGame = DBWrapper.getGame(clientGame.name);
        if (serverGame == null) {
            // TODO: Check if this is the initial state and the users make sense, etc.
            DBWrapper.saveGame(clientGame);
        } else if (serverGame.move(clientGame).success()) {
            // TODO: Check that the logged-in user is the user who's moving, etc.
            DBWrapper.saveGame(serverGame);
        }
        // Whether the update happened or not, the server version is the source of truth, so send it back down
        return serverGame;
    }

    // Returns token if successful, else null or ""
    private String login(Login login) {
        String u = login.getUsername();
        String p = login.getPassword();
        if (!validatePassword(p)) {
            //Password does not meet requirements, we do not need to hash
            return "";
        }
        Credentials savedUser = DBWrapper.getUser(u);
        // no user exists with this username
        if (savedUser == null) {
            return "";
        }
        // password verification failed.
        if (!Utils.verifyHash(p, u, savedUser.salt)) {
            return "";
        }

        String token = UUID.randomUUID().toString();
        savedUser.token = token;
        savedUser.updateTokenDate();
        DBWrapper.saveUser(savedUser);

        return token;
    }

    // Returns token if successful, else null or ""
    private String signup(Signup signup) {
        String username = signup.getUsername();
        String password = signup.getPassword();
        Credentials savedUser = DBWrapper.getUser(username);
        if (!validatePassword(password)) {
            //Password does not meet requirements, we do not need to hash
            return "";
        }
        // no user exists with this username
        if (savedUser != null) {
            return "";
        }
        //Begin updating this credential object with new info

        String salt = Utils.generateSalt();
        String hash = Utils.hash(password, salt);
        // Create the Credentials object
        savedUser = new Credentials(username, salt, hash);
        // Save it
        DBWrapper.saveUser(savedUser);
        // Proceed to the login flow to generate a token
        return login(signup);
    }

    private GameList getGameList(GameListRequest request) {
        return new GameList(DBWrapper.getPublicGames(request.user), DBWrapper.getPrivateGames(request.user));
    }

    private Game getGame(GameRequest request) {
        return DBWrapper.getGame(request.name);
    }

    public String getUser() {
        return user;
    }

    public String getToken() {
        return token;
    }

    private boolean validatePassword(String p) {
        int l = p.length();
        return !(l < MIN_PASSWORD_CHAR || l > MAX_PASSWORD_CHAR);
    }
}
