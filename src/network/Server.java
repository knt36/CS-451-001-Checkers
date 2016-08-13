package network;

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

    int SERVER_PORT = 4443; // Port where the SSL Server needs to listen for new requests from the client


    public String generateToken() {
        return null;
    }

    /**
     * Returns a functional SSL socket. Code taken from the OWASP wiki, the definitive guide to secure programming.
     *
     * @return functional SSL socket OR NULL in the case of an exception.
     */
    public Socket createSSLSocket() {
        SSLSocket sslSocket;
            // Registering the JSSE provider
            Security.addProvider(new Provider());

            //Specifying the Keystore details
            System.setProperty("javax.net.ssl.keyStore","server.ks");
            //In a proper system, we'd have a way of "black-boxing" this key. However, this
            // is not too important for now.
            System.setProperty("javax.net.ssl.keyStorePassword","Gl0wing-D0ll0p-CS451@@@");

            // Enable debugging to view the handshake and communication which happens between the SSLClient and the SSLServer
            // System.setProperty("javax.net.debug","all");
        // Initialize the Server Socket
        SSLServerSocketFactory sslServerSocketfactory = (SSLServerSocketFactory)SSLServerSocketFactory.getDefault();
        SSLServerSocket sslServerSocket = null;
        try {
            sslServerSocket = (SSLServerSocket)sslServerSocketfactory.createServerSocket(SERVER_PORT);
            sslSocket = (SSLSocket)sslServerSocket.accept();
            return sslSocket;
        } catch(Exception exp) {
            PrivilegedActionException priexp = new PrivilegedActionException(exp);
            System.out.println(" Priv exp --- " + priexp.getMessage());

            System.out.println(" Exception occurred .... " +exp);
            exp.printStackTrace();
            return null;
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
