package network;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import java.io.IOException;
import java.net.ServerSocket;


/**
 *
 */
public class Server {
    //TODO CHANGE BEFORE MERGING
    private static final int SERVER_PORT = 4448;

    public static void startServer() throws IOException {

        // SSL Taken from the Oracle Docs
        ServerSocket listener = null;
        // Instantiate the ssl socket factory with default setting
        SSLServerSocketFactory sslSrvFact =
                (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();


        try {
            listener = (SSLServerSocket) sslSrvFact.createServerSocket(SERVER_PORT);
            System.out.println("Server warmed up and waiting for connections");
            while (true) {
                new ServerThread(listener.accept()).start();
            }
        } finally {
            if (listener != null) {
                listener.close();
            }
        }
    }
}
