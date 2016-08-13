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
        String serialized = Utils.serialize(newLogin);
        return (sendData(serialized));
    }

    public boolean sendAccountCreate(String username, String password){
        return false;
    }

    private boolean sendData(String data){
        boolean ret = false;
        try {
            sslSocket = (SSLSocket) sslsocketfactory.createSocket(SERVER_HOSTNAME, SERVER_PORT);
            out = new PrintWriter(sslSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(sslSocket.getInputStream()));
            ret = true;
        } catch (IOException e) {
            e.printStackTrace();
            ret = false;
        } finally {
            try {
                if (sslSocket != null) {
                    sslSocket.close();
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
