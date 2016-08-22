package main;

import network.Server;

/**
 *
 *
 */
public class ServerMain {
    public static void main(String[] args) {
        try {
            // And here we've refactored for testability
            // Gotta get them good stats man
            testConnectDB();
        } catch (Exception ex) {
            // weeeeell shit
            ex.printStackTrace();
        }
        while (true) {
            try {
                Server.startServer();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void testConnectDB() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        // The newInstance() call is a work around for some
        // broken Java implementations
        Class.forName("com.mysql.jdbc.Driver").newInstance();
    }
}
