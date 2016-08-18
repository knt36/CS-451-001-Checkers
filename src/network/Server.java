package network;

import java.io.IOException;
import java.net.ServerSocket;

/**
 *
 */
public class Server {
    private static int SERVER_PORT = 4443;

    public static void startServer() throws IOException {
        int clientNumber = 0;
        ServerSocket listener = new ServerSocket(SERVER_PORT);
        try {
            while (true) {
                new ServerReceiver(listener.accept(), clientNumber++).start();
            }
        } finally {
            listener.close();
        }
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
