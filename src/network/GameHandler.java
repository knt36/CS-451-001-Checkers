import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by mblaho on 8/17/16.
 *
 * This is the thread handler for the server.
 */
public class GameHandler extends Thread{
    private Socket socket;
    private int clientNumber;

    public GameHandler(Socket socket, int clientNumber) {
        this.socket = socket;
        this.clientNumber = clientNumber;
        log("New connection with client# " + clientNumber + " at " + socket);
    }

    public void run() {
        try {

            // Decorate the streams so we can send characters
            // and not just bytes.  Ensure output is flushed
            // after every newline.
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // Get messages from the client, line by line; return them
            // capitalized
            while (true) {
                String input = in.readLine();
                if (input == null || input.equals(".")) {
                    break;
                }
                out.println(input);
            }
        } catch (IOException e) {
            log("Error handling client# " + clientNumber + ": " + e);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                log("Couldn't close a socket, what's going on?");
            }
            log("Connection with client# " + clientNumber + " closed");
        }
    }

    private void log(String message) {
        System.out.println(message);
    }
}
