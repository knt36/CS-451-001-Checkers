package database;

import game.Disk;
import game.Game;
import game.Player;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static game.Color.RED;
import static game.Color.WHITE;

/**
 * We don't do any lengthy operations with the database, so this wraps up connecting and executing queries all into one
 * package
 */
public final class DBWrapper {
    private static final String user = "checker";
    private static final String password = "C01l3e18999";
    private static final String database = "Checker";
    private static final String port = "3306";

    private DBWrapper() {
    }

    private static Connection connect() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://127.0.0.1:" + port + "/" + database, user, password);
    }

    private static void close(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                printSQLException(e);
                System.out.println("Failed to close database connection");
            }
        }
    }

    private static void update(PreparedStatement stmt) throws SQLException {
        System.out.println(stmt);
        stmt.executeUpdate();
    }

    private static ResultSet query(PreparedStatement stmt) throws SQLException {
        System.out.println(stmt);
        return stmt.executeQuery();
    }

    private static Game gameFromSQL(ResultSet rs) throws SQLException {
        String name = rs.getString("name");
        String u1 = rs.getString("p1");
        String u2 = rs.getString("p2");
        String stateBlob = rs.getString("state");
        String turn = rs.getString("turn");
        String red = rs.getString("red");

        List<Disk> state = Game.deserializeBoard(stateBlob);

        Player p1;
        Player p2;
        if (red.equals(u1)) {
            p1 = new Player(u1, RED);
        } else {
            p1 = new Player(u1, WHITE);
        }
        p2 = p1.opponent(u2);
        if (turn.equals(u1)) {
            return new Game(name, p1, p2, state, p1, new ArrayList<>());
        } else {
            return new Game(name, p1, p2, state, p2, new ArrayList<>());
        }
    }

    private static Credentials credentialsFromSQL(ResultSet rs) throws SQLException {
        return new Credentials(
                rs.getString("name"),
                rs.getString("password"),
                rs.getString("token"),
                rs.getDate("tokenDate")
        );
    }

    private static void printSQLException(SQLException e) {
        if (!ignoreSQLException(e.getSQLState())) {
            e.printStackTrace(System.err);
            System.err.println("SQLState: " + e.getSQLState());
            System.err.println("Error Code: " + e.getErrorCode());
            System.err.println("Message: " + e.getMessage());
            Throwable t = e.getCause();
            while (t != null) {
                System.out.println("Cause: " + t);
                t = t.getCause();
            }
        }
    }

    private static boolean ignoreSQLException(String sqlState) {
        // X0Y32: Jar file already exists in schema
        // 42Y55: Table already exists in schema
        return (sqlState != null && (sqlState.equalsIgnoreCase("X0Y32") || sqlState.equalsIgnoreCase("42Y55")));
    }

    public static Game getGame(String name) {
        Connection conn = null;
        Game result = null;
        String sql = "SELECT name, p1, p2, state, turn, red FROM Games WHERE name=?";
        try {
            conn = connect();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            ResultSet rs = query(stmt);
            while (rs.next()) {
                result = gameFromSQL(rs);
            }
        } catch (SQLException e) {
            printSQLException(e);
        } finally {
            close(conn);
        }
        return result;
    }

    public static List<Game> getPublicGames(String username) {
        return getGames(username).stream().filter(g -> g.p2.nobody()).collect(Collectors.toList());
    }

    public static List<Game> getPrivateGames(String username) {
        return getGames(username).stream().filter(g -> !g.p2.nobody()).collect(Collectors.toList());
    }

    private static List<Game> getGames(String username) {
        Connection conn = null;
        List<Game> result = new ArrayList<>();
        String sql = "SELECT name, p1, p2, state, turn, red FROM Games WHERE p1=? OR p2=?";
        try {
            conn = connect();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, username);
            ResultSet rs = query(stmt);
            while (rs.next()) {
                result.add(gameFromSQL(rs));
            }
        } catch (SQLException e) {
            printSQLException(e);
        } finally {
            close(conn);
        }
        return result;
    }

    public static Credentials getUser(String name) {
        Connection conn = null;
        Credentials result = null;
        String sql = "SELECT name, password, token, tokenDate FROM Users WHERE name=?";
        try {
            conn = connect();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            ResultSet rs = query(stmt);
            while (rs.next()) {
                result = credentialsFromSQL(rs);
            }
        } catch (SQLException e) {
            printSQLException(e);
        } finally {
            close(conn);
        }
        return result;
    }

    public static String getUserByToken(String token) {
        Connection conn = null;
        String result = null;
        String sql = "SELECT name FROM Users WHERE token=?";
        try {
            conn = connect();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, token);
            ResultSet rs = query(stmt);
            while (rs.next()) {
                result = rs.getString("name");
            }
        } catch (SQLException e) {
            printSQLException(e);
        } finally {
            close(conn);
        }
        return result;
    }

    public static void saveGame(Game game) {
        Connection conn = null;
        String sql = "INSERT INTO Games (name, p1, p2, state, turn, red) VALUES (?, ?, ?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE p1=?, p2=?, state=?, turn=?, red=?";
        String state = game.serializeBoard();
        String red = game.red().getName();
        try {
            conn = connect();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, game.name);
            stmt.setString(2, game.p1.getName());
            stmt.setString(3, game.p2.getName());
            stmt.setString(4, state);
            stmt.setString(5, game.turn.getName());
            stmt.setString(6, red);
            stmt.setString(7, game.p1.getName());
            stmt.setString(8, game.p2.getName());
            stmt.setString(9, state);
            stmt.setString(10, game.turn.getName());
            stmt.setString(11, red);
            update(stmt);
        } catch (SQLException e) {
            printSQLException(e);
        } finally {
            close(conn);
        }
    }

    public static void saveUser(Credentials credentials) {
        Connection conn = null;
        String sql = "INSERT INTO Users (name, password, token, tokenDate) VALUES (?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE password=?, token=?, tokenDate=?";
        try {
            conn = connect();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, credentials.username);
            stmt.setString(2, credentials.password());
            stmt.setString(3, credentials.token);
            stmt.setDate(4, credentials.tokenDate);
            stmt.setString(5, credentials.password());
            stmt.setString(6, credentials.token);
            stmt.setDate(7, credentials.tokenDate);
            update(stmt);
        } catch (SQLException e) {
            printSQLException(e);
        } finally {
            close(conn);
        }
    }

    public static UserList getUsers(String token, String str) {
        Connection conn = null;
        List<String> users = new ArrayList<>();
        String sql = "SELECT name, password, token, tokenDate FROM Users WHERE token!=? AND name LIKE ?";
        try {
            conn = connect();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, token);
            stmt.setString(2, str + "%");
            ResultSet rs = query(stmt);
            while (rs.next()) {
                users.add(credentialsFromSQL(rs).username);
            }
        } catch (SQLException e) {
            printSQLException(e);
        } finally {
            close(conn);
        }
        return new UserList(users);
    }

    public static Boolean deleteGame(String name) {
        Connection conn = null;
        Boolean success = false;
        String sql = "DELETE FROM Games WHERE name=? LIMIT 1";
        try {
            conn = connect();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            update(stmt);
            success = true;
        } catch (SQLException e) {
            printSQLException(e);
        } finally {
            close(conn);
        }
        return success;
    }
}
