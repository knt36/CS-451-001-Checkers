package main;

import network.Server;

/**
 *
 */
public class ServerMain {
    public static void main(String[] args) {
        while (true) {
            try {
                Server.startServer();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
