package network;

/**
 * Created by mblaho on 8/16/16.
 *
 * Test driver
 */
public class NetworkTesterServer {
    public static void main(String[] args) {
        while(true) {
            // Enable debugging to view the handshake and communication which happens between the SSLClient and the SSLServer
            System.setProperty("javax.net.debug","all");
            //This is not wrong. Do not fix it.
            // If this is preventing compilation, remove this file from the classpath.
            try {
                Server.startServer();
            } catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
