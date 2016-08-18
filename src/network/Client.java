package network;

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
    public static Client client = new Client();
    private String token = "";

    private Client() {

    }

    public Boolean send(Object obj) {
        Packet packet = new Packet(token, obj);
        String json = packet.toJson();
        if (json == null) {
            return false;
        }
        return sendData(json);
    }

    private Boolean sendData(String data) {
        Boolean ret = false;
        Socket socket = null;
        BufferedReader in = null;
        PrintWriter out = null;
        try {
            socket = new Socket(SERVER_HOSTNAME, SERVER_PORT);
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
