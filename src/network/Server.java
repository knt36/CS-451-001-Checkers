import java.net.Socket;

import java.io.*;
import java.security.Security;
import java.security.PrivilegedActionException;

import javax.net.ssl.*;
import com.sun.net.ssl.*;
import com.sun.net.ssl.internal.ssl.Provider;
/**
 *
 */
public class Server {

    static int SERVER_PORT = 4443; // Port where the SSL Server needs to listen for new requests from the client


    public String generateToken() {
        return null;
    }

    /**
     * Returns a functional SSL socket. Code taken from the OWASP wiki, the definitive guide to secure programming.
     *
     * @return functional SSL socket OR NULL in the case of an exception.
     */
    public static void createSSLSocket() {
        SSLSocket sslSocket;
        // Registering the JSSE provider
        Security.addProvider(new Provider());
        //Specifying the Keystore details
        System.setProperty("javax.net.ssl.keyStore","keystore.jks");
        //In a proper system, we'd have a way of "black-boxing" this key. However, this
        // is not too important for now.
        System.setProperty("javax.net.ssl.keyStorePassword","checkers");
        // Enable debugging to view the handshake and communication which happens between the SSLClient and the SSLServer
        System.setProperty("javax.net.debug","all");
        // Initialize the Server Socket
        SSLServerSocketFactory sslServerSocketfactory = (SSLServerSocketFactory)SSLServerSocketFactory.getDefault();
        SSLServerSocket sslServerSocket = null;
        try {
            sslServerSocket = (SSLServerSocket)sslServerSocketfactory.createServerSocket(SERVER_PORT);
            sslSocket = (SSLSocket)sslServerSocket.accept();
            while(true) {
                PrintWriter out = new PrintWriter(sslSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(sslSocket.getInputStream()));
                String inputLine, outputLine;
                while ((inputLine = in.readLine()) != null) {
                    out.println(inputLine);
                    System.out.println(inputLine);
                }
            }
        } catch(Exception exp) {
            PrivilegedActionException priexp = new PrivilegedActionException(exp);
            System.out.println(" Priv exp --- " + priexp.getMessage());

            System.out.println(" Exception occurred .... " +exp);
            exp.printStackTrace();
            }
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
