package network;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Random;

import static java.nio.charset.Charset.defaultCharset;

/**
 * Unit tested 8/19/16, 100% methods, 14/16 lines
 */
public class Utils {
    private static final int ITERATIONS = 100;
    private static final int KEY_LENGTH = 256;

    /*
     * Returns a salt.
     *
     *  !!!Remember to store this with the password!!!
     *  If you do not, you will be sad.
     *
     * @return  salt as string
     */
    public static String generateSalt() {
        final Random r = new SecureRandom();
        byte[] salt = new byte[32];
        r.nextBytes(salt);
        return new String(salt, defaultCharset() );
    }
    /*
     * This is the internal hashing function.
     *
     *              !!!INTERNAL USE ONLY!!!
     *
     * --- DO NOT UNDER ANY CIRCUMSTANCES CALL THIS DIRECTLY!---
     *
     * @param   p the password to be hashed
     * @param   s the salt that goes with this hash
     * @param   iterations the number of rounds, defined as a static const ITERATIONS
     * @param   keyLength the keylength for the hash derivation, defined as a static const KEY_LENGTH
     *
     * @return  PBKDF2 hash to be stored in the database with the salt.
     *
     */
    private static String hash(String p, String s, int iterations, int keyLength) {
        //Conver
        final byte[] salt = s.getBytes(defaultCharset());
        final char[] password = p.toCharArray();
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance( "PBKDF2WithHmacSHA512" );
            PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, keyLength);
            SecretKey key = skf.generateSecret( spec );
            byte[] res = key.getEncoded( );
            return new String(res, defaultCharset());

        } catch( NoSuchAlgorithmException | InvalidKeySpecException e ) {
            // Something's gone VERY VERY WRONG!!!
            throw new RuntimeException( e );
        }

    }
    /*
     * This is the external hashing function. This calls the internal function
     *
     * ---USE THIS FUNCTION---
     *
     * @param   p the password to be hashed
     * @param   s the salt that goes with this hash
     *
     * @return  PBKDF2 hash to be stored in the database with the salt.
     *
     */
    public static String hash(String p, String s) {
        return hash(p, s, ITERATIONS, KEY_LENGTH);
    }

    /*
     * Verifies a password against a database password hash + salt
     *
     * @param   password the password to be verified
     * @param   hash the hash stored for the user in the database
     * @param   salt the salt stored with the hash
     *
     * @return  true if the password and salt go together, false if they do not
     *
     */
    public static Boolean verifyHash(String password, String hash, String salt) {
        return hash.equals(hash(password, salt));
    }
}
