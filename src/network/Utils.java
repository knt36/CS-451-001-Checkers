package network;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 */
public class Utils {


    public Boolean send(Object data) {
        //TODO implement send -- not sure if this is needed
        return false;
    }

    private String hash(String str, String salt) {
        //TODO implement hash
        return null;
    }

    public Boolean verifyHash(String hash, String data) {
        //TODO implement verifyHash
        return false;
    }

    private void acknowledge(int error) {
    }

    private Boolean isWellFormed(String json) {
        //TODO implement isWellFormed
        return false;
    }

    static String serialize(Object data) {
        //TODO implement serialize
        return null;
    }

    static Object deserialize(String json) {
        //TODO implement deserialize
        return null;
    }
}
