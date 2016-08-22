package main;

import org.junit.Test;

/**
 * MUST BE RUN ON THE SERVER!
 */
public class ServerMainTest {
    @Test
    //Check to see if the database connector is working, since this exception is the evil bit
    public void testDatabase() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        ServerMain.testConnectDB();
    }
}
