package database;

import game.Game;
import main.ServerMain;
import network.Utils;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static database.DBWrapper.*;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by rachelgoeken on 8/18/16.
 */
public class DBWrapperTest {
    public static final String TEST_GAME_NAME = "UNIT_TEST_GAME_UNIT_TEST_GAME";
    public static final String TEST_USER_ONE = "UNIT_TEST_USER1_UNIT_TEST_USER1_UNIT_TEST_USER1";
    public static final String TEST_USER_TWO = "UNIT_TEST_USER2_UNIT_TEST_USER2_UNIT_TEST_USER2";
    private Game test_pub = new Game(TEST_GAME_NAME + TEST_GAME_NAME, TEST_USER_ONE);
    private Game test_priv = new Game(TEST_GAME_NAME, TEST_USER_ONE, TEST_USER_TWO);

    @BeforeClass
    public static void init() throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        DBWrapper.ip = "98.114.253.148";
        ServerMain.testConnectDB();
    }
    @Test
    public void Game() {
        DBWrapper.saveGame(test_priv);
        DBWrapper.saveGame(test_pub);
        List<Game> retrievedPublic = DBWrapper.getPublicGames(TEST_USER_ONE);
        List<Game> retrievedPrivate = DBWrapper.getPrivateGames(TEST_USER_ONE);
        assertNotNull(retrievedPrivate);
        assertNotNull(retrievedPublic);
        Game pulled_game = DBWrapper.getGame(TEST_GAME_NAME);
        // cursory check to see if we got back good data
        assertEquals(pulled_game.getDisk(1), test_priv.getDisk(1));
        DBWrapper.deleteGame(TEST_GAME_NAME);
        DBWrapper.deleteGame(TEST_GAME_NAME + TEST_GAME_NAME);
    }
    @Test
    public void induceException() throws Exception {
        Connection conn = null;
        List<Game> result = new ArrayList<>();
        String sql = "SELECT name, p1, p2, state, turn, red FROM XYZZZZ WHERE p1!=? AND p2=?";
        try {
            conn = connect();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, "foobar");
            stmt.setString(2, "");
            ResultSet rs = query(stmt);
            while (rs.next()) {
                result.add(gameFromSQL(rs));
            }
        } catch (SQLException e) {
            printSQLException(e);
        } finally {
            close(conn);
        }
    }


    @Test
    public void User() throws Exception {
        String salt = Utils.generateSalt();
        String hash = Utils.hash("fksdjfkldsjfkldsjfkljdsf", salt);
        String token = "fdkfkdsjfdf"; //whatever lawl
        Credentials creds = new Credentials(TEST_USER_ONE, salt, hash, token, Date.valueOf(LocalDate.now()));
        // Sneak these in here
        Credentials creds2 = new Credentials(TEST_USER_ONE, salt, hash);
        assertNotEquals(creds2.toString(), creds.toString());
        String retrievedSalt = creds2.getSalt();
        assertEquals(retrievedSalt, salt);
        //Can we change the date?
        creds2.updateTokenDate();
        assertEquals(creds2.tokenDate, Date.valueOf(LocalDate.now()));
        //save user
        DBWrapper.saveUser(creds);
        //can we pull out the user by token
        String retrievedUser = DBWrapper.getUserByToken(token);
        assertNotNull(retrievedUser);
        assertEquals(TEST_USER_ONE, retrievedUser);
        // can we pull out the user by username
        Credentials retrievedCreds = DBWrapper.getUser(TEST_USER_ONE);
        assertEquals(retrievedCreds.getHash(), creds.getHash());

        UserList byToken = DBWrapper.getUsers(token, TEST_USER_ONE);
        assertNotNull(byToken);

    }

}