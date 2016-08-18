package network;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Observable;

/**
 *
 */
public class Client extends Observable {
    private static final String SERVER_HOSTNAME = "www.centralark.org";
    private static final int SERVER_PORT = 4443;
    //private static SSLSocketFactory sslsocketfactory = (SSLSocketFactory)SSLSocketFactory.getDefault();
    //private static SSLSocket sslSocket;
    private static PrintWriter out;
    private static BufferedReader in;
    Socket socket;
    /*
    static {
        try {
            sslSocket = (SSLSocket) sslsocketfactory.createSocket(SERVER_HOSTNAME, SERVER_PORT);
            out = new PrintWriter(sslSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(sslSocket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    */
    public String token = "";

    public boolean sendLogin(String username, String password) {
        LoginRequest newLogin = new LoginRequest(username, password);
        String serialized = newLogin.toString();
        return (sendData(serialized));
    }

    public boolean sendAccountCreate(String username, String password){
        AcctCreateRequest newAcct = new AcctCreateRequest(username, password);
        String serialized = newAcct.toString();
        return (sendData(serialized));
    }

    private boolean sendData(String data){
        boolean ret = false;
        try {
         //   System.setProperty("javax.net.ssl.trustStore","client.jks");
         //   System.setProperty("javax.net.ssl.trustStorePassword", "checkers");

         //   sslSocket = (SSLSocket) sslsocketfactory.createSocket(SERVER_HOSTNAME, SERVER_PORT);
         //   sslSocket.setEnabledCipherSuites(new String[] {"TLS_RSA_WITH_AES_128_CBC_SHA"});
         //   out = new PrintWriter(sslSocket.getOutputStream(), true);
         //   in = new BufferedReader(new InputStreamReader(sslSocket.getInputStream()));
            Socket socket = new Socket(SERVER_HOSTNAME, SERVER_PORT);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);


            out.write(data);
            out.flush();
            ret = true;
        } catch (IOException e) {
            e.printStackTrace();
            ret = false;
        } finally {
            try {
                if (socket != null) {
                    socket.close();
                }
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException e){
                // This is not fatal, but where there's smoke there's fire!
                e.printStackTrace();
            }
        }
        return ret;
    }

}
