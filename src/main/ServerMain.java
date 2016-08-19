package main;

import network.Server;

/**
 *
 */
public class ServerMain {
    public static void main(String[] args) {
        try {
            // The newInstance() call is a work around for some
            // broken Java implementations
            Class.forName("com.mysql.jdbc.Driver").newInstance();
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
}
