package network;

/**
 * Created by mblaho on 8/17/16.
 */
public class NetworkTesterClient {
    public static void main(String[] args) {
       // while (true) {
            System.setProperty("javax.net.debug","all");
            Client client = new Client();
       // }
    }
}
