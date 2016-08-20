package database;

import game.Disk;
import game.Game;
import game.Player;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import static game.Color.RED;
import static game.Color.WHITE;

/**
 */

public class DBWrapper {
    private static final String user = "checker";
    private static final String password = "C01l3e18999";
    private static final String database = "Checker";
    private static final String port = "3306";
    private static final String server = "127.0.0.1"; //Local Since The Server Code is running Local
    private Connection conn;

    public DBWrapper() {
        this.conn = null;
        try {
        		this.conn = DriverManager.getConnection("jdbc:mysql://"+server+":"+ port +"/"+database,user,password);
        	} catch (SQLException e) {
            printSQLException(e);
        }
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

    public static boolean ignoreSQLException(String sqlState) {
        // X0Y32: Jar file already exists in schema
        // 42Y55: Table already exists in schema
        return (sqlState != null && (sqlState.equalsIgnoreCase("X0Y32") || sqlState.equalsIgnoreCase("42Y55")));
    }

    public Game getGame(String name) {
        Game result = null;
        String query = "SELECT name, p1, p2, state, turn, red FROM Games WHERE name=?";
        try {
            PreparedStatement stmt = this.conn.prepareStatement(query);
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                result = gameFromSQL(rs);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return result;
    }

    public List<Game> getPublicGames(String username) {
        return getGames(username).stream().filter(g -> g.p2.nobody()).collect(Collectors.toList());
    }

    public List<Game> getPrivateGames(String username) {
        return getGames(username).stream().filter(g -> !g.p2.nobody()).collect(Collectors.toList());
    }

    public List<Game> getGames(String username) {
        List<Game> result = new ArrayList<>();
        String query = "SELECT name, p1, p2, state, turn, red FROM Games WHERE p1=? OR p2=?";
        try {
            PreparedStatement stmt = this.conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, username);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                result.add(gameFromSQL(rs));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return result;
    }

    public Credentials getUser(String name) {
        Credentials result = null;
        String query = "SELECT name, password, token, tokenDate FROM Users WHERE name=?";
        try {
            PreparedStatement stmt = this.conn.prepareStatement(query);
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                result = credentialsFromSQL(rs);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return result;
    }

    public void saveGame(Game game) {
        String query = "INSERT INTO Games (name, p1, p2, state, turn, red) VALUES (?, ?, ?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE p1=?, p2=?, state=?, turn=?, red=?";
        String state = game.serializeBoard();
        String red = game.red().getName();
        try {
            PreparedStatement stmt = this.conn.prepareStatement(query);
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
            stmt.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    public void saveUser(Credentials credentials) {
        String query = "INSERT INTO Users (name, password, token, tokenDate) VALUES (?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE password=?, token=?, tokenDate=?";
        try {
            PreparedStatement stmt = this.conn.prepareStatement(query);
            stmt.setString(1, credentials.username);
            stmt.setString(2, credentials.password());
            stmt.setString(3, credentials.token);
            stmt.setDate(4, credentials.tokenDate);
            stmt.setString(5, credentials.password());
            stmt.setString(6, credentials.token);
            stmt.setDate(7, credentials.tokenDate);
            stmt.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }
}
