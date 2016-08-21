package network;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Random;

/**
 * Unit tested 8/19/16, 100% methods, 14/16 lines
 */
public class Utils {
    private static final int ITERATIONS = 100;
    private static final int KEY_LENGTH = 256;

    /**
     * Returns a salt.
     * <p>
     * !!!Remember to store this with the password!!!
     * If you do not, you will be sad.
     *
     * @return salt as string
     */
    public static String generateSalt() {
        final Random r = new SecureRandom();
        byte[] salt = new byte[8];
        r.nextBytes(salt);
        //byte[] saltEncoded = Base64.getEncoder().encode(salt);
        //System.out.println("GENERATED SALT: " + toHex(salt));
        return toHex(salt);
    }

    /**
     * This is the internal hashing function.
     * <p>
     * !!!INTERNAL USE ONLY!!!
     * <p>
     * --- DO NOT UNDER ANY CIRCUMSTANCES CALL THIS DIRECTLY!---
     * <p>
     * Taken from the OWASP Secure Java Coding Guide
     *
     * @param p          the password to be hashed
     * @param s          the salt that goes with this hash
     * @param iterations the number of rounds, defined as a static const ITERATIONS
     * @param keyLength  the keylength for the hash derivation, defined as a static const KEY_LENGTH
     * @return PBKDF2 hash to be stored in the database with the salt.
     */
    private static String hash(String p, String s, int iterations, int keyLength) {
        //final byte[] salt = Base64.getDecoder().decode(s);
        final byte[] salt = fromHex(s);
        //System.out.println("SALT: " + s);
        final char[] password = p.toCharArray();
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
            PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, keyLength);
            SecretKey key = skf.generateSecret(spec);
            byte[] hash = key.getEncoded();
            //byte[] hashEncoded = Base64.getEncoder().encode(hash);
            //System.out.println("HASH: " + toHex(hash));
            return toHex(hash);

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            // Something's gone VERY VERY WRONG!!!
            throw new RuntimeException(e);
        }

    }

    /**
     * This is the external hashing function. This calls the internal function
     * <p>
     * ---USE THIS FUNCTION---
     *
     * @param p the password to be hashed
     * @param s the salt that goes with this hash
     * @return PBKDF2 hash to be stored in the database with the salt.
     */
    public static String hash(String p, String s) {
        return hash(p, s, ITERATIONS, KEY_LENGTH);
    }

    /**
     * Verifies a password against a database password hash + salt
     *
     * @param password the password to be verified
     * @param hash     the hash stored for the user in the database
     * @param salt     the salt stored with the hash
     * @return true if the password and salt go together, false if they do not
     */
    public static Boolean verifyHash(String password, String hash, String salt) {

        return hash.equals(hash(password, salt));
    }

    /**
     * This converts Bytes to hex strings. Written by jtan189@Github.
     * [https://gist.github.com/jtan189/3804290]
     *
     * @param array salt or hash to convert
     * @return hex string that can be stored in a database.
     */
    private static String toHex(byte[] array) {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if (paddingLength > 0)
            return String.format("%0" + paddingLength + "d", 0) + hex;
        else
            return hex;
    }

    /**
     * This converts hex strings to Byte Arrays. Written by jtan189@Github.
     *
     * @param hex hex string that represents a salt or hash
     * @return byte array to be processed by the Key spec
     */
    private static byte[] fromHex(String hex) {
        byte[] binary = new byte[hex.length() / 2];
        for (int i = 0; i < binary.length; i++) {
            binary[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return binary;
    }


}
