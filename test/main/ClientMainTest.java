package main;

import org.junit.Test;

import static junit.framework.TestCase.fail;

/**
 */
public class ClientMainTest {
    @Test
    public void startupTest() {
        // We're going to do something stupid to check for exceptions
        try {
            ClientMain.main(new String[]{});
        } catch (Exception e) {
            fail(e.getMessage());
        }

    }
}
