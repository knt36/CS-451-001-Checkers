package network;

/**
 * Created by mblaho on 8/17/16.
 */
public class NetworkTesterClient {
    public static void main(String[] args) {
        while (true) {
            //This is not wrong. Do not fix it.
            // If this is preventing compilation, remove this file from the classpath.
            Client client = new Client();
            client.sendAccountCreate("test", "test");
            client.sendLogin("test", "test");
        }
    }
}
