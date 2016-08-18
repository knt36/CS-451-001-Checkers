package network;

import java.io.IOException;
import java.net.ServerSocket;

/**
 *
 */
public class Server {
    private static int SERVER_PORT = 4443;

    public static void startServer() throws IOException {
        ServerSocket listener = new ServerSocket(SERVER_PORT);
        try {
            while (true) {
                new ServerThread(listener.accept()).start();
            }
        } finally {
            listener.close();
        }
    }
}
