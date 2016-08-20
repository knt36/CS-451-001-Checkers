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
        String output = null;
        try {
            System.out.println("\nConnected to client");
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
            if (packet == null || packet.getData() == null) {
                output = Packet.error("Could not parse " + input);
            } else {
                this.token = packet.getToken();
                Packet result = process(packet);
                if (result == null) {
                    output = Packet.error("Server error, failed to process " + input);
                } else {
                    output = result.toJson();
                }
            }
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
                Game game = (Game) message;
                System.out.println("Got a game: " + game.toJson());
                return new Packet(token, updateGame(game));
            case SIGNUP:
                Signup signup = (Signup) message;
                System.out.println("Signing up: " + signup.getUsername());
                token = signup(signup);
                if (token != null && !token.equals("")) {
                    return new Packet(token, new Ack("Logged in", true));
                } else {
                    return Packet.perror("Incorrect username or password");
                }
            case LOGIN:
                Login login = (Login) message;
                System.out.println("Logging in: " + login.getUsername());
                token = login(login);
                if(token != null && !token.equals("")) {
                    return new Packet(token, new Ack("Logged in", true));
                } else {
                    return Packet.perror("Incorrect username or password");
                }
            case GAME_LIST_REQUEST:
                GameListRequest request = (GameListRequest) message;
                System.out.println("Request for game list from: " + request.user);
                GameList gameList = getGameList((GameListRequest) message);
                return new Packet(token, gameList);
            case GAME_REQUEST:
                GameRequest grequest = (GameRequest) message;
                System.out.println("Requested game: " + grequest.name);
                Game requested = getGame(grequest);
                return new Packet(token, requested);
            case GAME_DELETE:
                GameDelete delete = (GameDelete) message;
                System.out.println("Deleting game: " + delete.name);
                Boolean success = deleteGame(delete);
                return new Packet(token, new Ack("Attempted to delete game", success));
            case USER_LIST_REQUEST:
                UserListRequest ulr = (UserListRequest) message;
                System.out.println("Requested user list starting with: " + ulr.str);
                UserList userList = getUserList(token, (UserListRequest) message);
                return new Packet(token, userList);
            default:
                System.out.println("Received unexpected message from client: " + message.toJson());
                return Packet.perror("Unexpected message");
        }
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
        } else if (user.equals(serverGame.turn.getName()) && serverGame.move(clientGame).success()) {
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
            System.out.println("Did not find user");
            return "";
        }
        // password verification failed.
        if (!Utils.verifyHash(p, savedUser.getHash(), savedUser.getSalt())) {
            System.out.println("Password Auth failed");
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
            System.out.println("Invalid password");
            return "";
        }
        // user already exists with this username
        if (savedUser != null) {
            System.out.println("User already exists");
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
