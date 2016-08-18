package network;

import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.util.Random;

/**
 *
 */
public class Utils {
    public static final int ITERATIONS = 100;
    public static final int KEY_LENGTH = 256;
    public Boolean send(Object data) {
        return false;
    }

    public String generateSalt() {
        final Random r = new SecureRandom();
        byte[] salt = new byte[32];
        r.nextBytes(salt);
        return new String(salt, Charset.defaultCharset() );
    }

    private String hash(String password, String salt, int iterations, int key_length) {


    }

    public Boolean verifyHash(String hash, String data) {
        return false;
    }

    private void acknowledge(int error) {

    }
}
