package network;

import java.util.Observable;

/**
 *
 */
public class Client extends Observable {
    public String token;
    private Utils utils = new Utils();

    public Boolean login(String username, String password) {
        return false;
    }
}
