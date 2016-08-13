package network;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Observable;

/**
 *
 */
public class Client extends Observable {
    private static String SERVER_HOSTNAME = "www.centralark.org";
    private static int SERVER_PORT = 0;
    private static SSLSocketFactory sslsocketfactory = (SSLSocketFactory)SSLSocketFactory.getDefault();
    private static SSLSocket sslSocket;
    private static PrintWriter out;
    private static BufferedReader in;

    static {
        try {
            sslSocket = (SSLSocket) sslsocketfactory.createSocket(SERVER_HOSTNAME, SERVER_PORT);
            out = new PrintWriter(sslSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(sslSocket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String token;
    private Utils utils = new Utils();

    public Boolean login(String username, String password) {
        return false;
    }
}