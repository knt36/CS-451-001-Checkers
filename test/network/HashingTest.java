package network;

import network.Utils;
import org.junit.Test;

import static org.junit.Assert.*;


public class HashingTest {
    @Test
    public void TestHashing() throws Exception {
        // The variable will already exist
        Utils utils = new Utils();
        String password = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";

        // Is the salt working?
        String salt = utils.generateSalt();
        assertNotNull(salt);
        assertNotEquals(salt, "");

        // Make another salt with the same function
        String salt2 = utils.generateSalt();
        assertNotNull(salt2);
        assertNotEquals(salt2, "");

        // These salts should be different, they are random!
        assertNotEquals(salt2, salt);

        // Can we hash something?
        String hash1 = utils.hash(password, salt);
        assertNotNull(hash1);
        assertNotEquals(hash1, "");

        // Make another hash from the same inputs
        String hash2 = utils.hash(password, salt);
        assertNotNull(hash2);
        assertNotEquals(hash2, "");

        // Are these two hashes the same? This is a determanistic process.
        assertEquals(hash2, hash1);

        // Can we use the Verify Hash Function to check a password against stored data?
        // These should all be equal.
        assertTrue(utils.verifyHash(password, hash1, salt));
        assertTrue(utils.verifyHash(password, hash2, salt));
    }
}