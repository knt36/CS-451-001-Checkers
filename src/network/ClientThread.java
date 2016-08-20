package network;

import network.messages.Packet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.function.Consumer;

/**
 *
 */
public class ClientThread extends Thread {
    private static final String SERVER_HOSTNAME = "www.centralark.org";
    private static final int SERVER_PORT = 4443;
    private String data;
    private Consumer<Packet> callback;

    public ClientThread(String data, Consumer<Packet> callback) {
        this.data = data;
        this.callback = callback;
    }

    @Override
    public void run() {
        Socket socket = null;
        BufferedReader in = null;
        PrintWriter out = null;
        try {
            socket = new Socket(SERVER_HOSTNAME, SERVER_PORT);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            out.write(data + "\n");
            out.flush();
            System.out.println("After read line1");
            String input = null;
            while(input == null){
            	System.out.println("Trying to read");
            	input = in.readLine();
            }
            System.out.println("After read line2");
            Packet packet = Packet.fromJson(input);
            if (input == null || input.equals(".")) {
                return;
            }
            if (packet == null || packet.getData() == null) {
                out.write(Packet.error("Could not parse data"));
                callback.accept(Packet.perror("Could not parse data"));
                return; // Error from client side, nothing to do
            }
            callback.accept(packet);
        } catch (IOException e) {
            callback.accept(Packet.perror("Could not connect"));
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
            } catch (IOException e) {
                // This is not fatal, but where there's smoke there's fire!
                e.printStackTrace();
            }
        }
    }
}
