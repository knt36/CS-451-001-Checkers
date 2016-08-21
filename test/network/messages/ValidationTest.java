package network.messages;

import network.ServerThread;
import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

/**
 */
public class ValidationTest {
    private static final String PASSWORD_TOO_BIG = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String PASSWORD_TOO_SMALL = "AAAAAAA";
    private static final String PASSWORD_WITH_WHITESPACE = "AAAAAAAA A";
    private static final String PASSWORD_WITH_NONPRINT = "AAA";
    private static final String PASSWORD_VALID = "AAAAAAAA";

    private static final String USERNAME_TOO_BIG = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String USERNAME_TOO_SMALL = "AA";
    private static final String USERNAME_WITH_WHITESPACE = "AA A";
    private static final String USERNAME_WITH_NONPRINT = "AAA";
    private static final String USERNAME_VALID = "AAAAAAAA";

    private static final String GAMENAME_TOO_BIG = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String GAMENAME_TOO_SMALL = "";
    private static final String GAMENAME_WITH_WHITESPACE = " ";
    private static final String GAMENAME_WITH_NONPRINT = "AAA";
    private static final String GAMENAME_VALID = "AAAAAAAA";

    @Test
    public void testUsernameValidation() {
        assertFalse(ServerThread.validateUsername(USERNAME_TOO_BIG));
        assertFalse(ServerThread.validateUsername(USERNAME_TOO_SMALL));
        assertFalse(ServerThread.validateUsername(USERNAME_WITH_WHITESPACE));
        assertFalse(ServerThread.validateUsername(USERNAME_WITH_NONPRINT));
        assertTrue(ServerThread.validateUsername(USERNAME_VALID));
    }

    @Test
    public void testPasswordValidation() {
        assertFalse(ServerThread.validatePassword(PASSWORD_TOO_BIG));
        assertFalse(ServerThread.validatePassword(PASSWORD_TOO_SMALL));
        assertFalse(ServerThread.validatePassword(PASSWORD_WITH_NONPRINT));
        assertTrue(ServerThread.validatePassword(PASSWORD_VALID));
        assertTrue(ServerThread.validatePassword(PASSWORD_WITH_WHITESPACE));
    }

    @Test
    public void testGameNameValidation() {
        assertFalse(ServerThread.validateGameName(GAMENAME_TOO_BIG));
        assertFalse(ServerThread.validateGameName(GAMENAME_TOO_SMALL));
        assertFalse(ServerThread.validateGameName(GAMENAME_WITH_WHITESPACE));
        assertFalse(ServerThread.validateGameName(GAMENAME_WITH_NONPRINT));
        assertTrue(ServerThread.validateGameName(GAMENAME_VALID));
    }


}
