package network;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by rachelgoeken on 8/18/16.
 */
public class UtilsTest {
    @Test
    public void Rachel() throws Exception {
        String salt = "1rMAPDAyME6vAInPA3M+CGP5omqmRzKETrtTXfIOQ1w=";
        String hash = "3kB/7EBGHkVsLa33cbvk/G6fMH5EjUY6I/spu6NrbQ0=";
        String password = "Checkersgame";
        System.out.println(Utils.hash(password, salt));
        assertTrue(Utils.verifyHash(password, hash, salt));
    }

    @Test
    public void gaakldf() throws Exception {
        String salt = "a1DQEKOaQdRPZMkwbH0XkPrUD5FMzDuSiSsrRN2grpM=";
        String hash = "WpQwAmYr6ySNwLS7cLKxE251jUTkHuh9R9+8dhZ9mxU=";
        String password = "alkdjfalkjf";
        System.out.println(Utils.hash(password, salt));
        assertTrue(Utils.verifyHash(password, hash, salt));
    }

}