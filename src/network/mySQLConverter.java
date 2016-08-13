package network;

import game.Game;

import java.sql.*;
import java.util.Properties;

/**
 * Created by rachelgoeken on 8/13/16.
 */

//from https://docs.oracle.com/javase/tutorial/jdbc/basics/index.html
public class mySQLConverter {

    public static void getGameTable(Connection con) throws SQLException {

        String query = "select name, turn, p0, p1, state, invite, first from GameData";

        try (Statement stmt = con.createStatement()) {

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                String gameName = rs.getString("name");
                int turn = rs.getInt("turn");
                int p0 = rs.getInt("p0");
                int p1 = rs.getInt("p1");
                //blob state = rs.get..("state");
                int invite = rs.getInt("invite");
                int first = rs.getInt("first");


            }
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    public static void getUserTable(Connection con) throws SQLException {

        String query = "select name, password from UserData";

        try (Statement stmt = con.createStatement()) {

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                String userName = rs.getString("name");
                String userPassword = rs.getString("password");

            }
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    public Connection getConnection() throws SQLException {

        Connection conn = null;
        Properties connectionProps = new Properties();
        connectionProps.put("user", "checker");
        connectionProps.put("password", "C01l3e18999");
        conn = DriverManager.getConnection("jdbc:mysql://Checker:3306/", connectionProps);

        System.out.println("Connected to database");
        return conn;
    }

    public static void printSQLException(SQLException ex) {

        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                if (ignoreSQLException(
                        ((SQLException)e).
                                getSQLState()) == false) {

                    e.printStackTrace(System.err);
                    System.err.println("SQLState: " +
                            ((SQLException)e).getSQLState());

                    System.err.println("Error Code: " +
                            ((SQLException)e).getErrorCode());

                    System.err.println("Message: " + e.getMessage());

                    Throwable t = ex.getCause();
                    while(t != null) {
                        System.out.println("Cause: " + t);
                        t = t.getCause();
                    }
                }
            }
        }
    }

    public static boolean ignoreSQLException(String sqlState) {

        if (sqlState == null) {
            System.out.println("The SQL state is not defined!");
            return false;
        }

        // X0Y32: Jar file already exists in schema
        if (sqlState.equalsIgnoreCase("X0Y32"))
            return true;

        // 42Y55: Table already exists in schema
        if (sqlState.equalsIgnoreCase("42Y55"))
            return true;

        return false;
    }

    //update game - keyed off the name of the game
    public void updateGame(Connection con, Game game) throws SQLException {
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet uprs = stmt.executeQuery("SELECT * FROM GameData");

            while (uprs.next()) {
                uprs.updateString("name", game.name);
                //uprs.updateInt("turn", game.turn);
                //uprs.updateInt("p0", game.p1);
                //uprs.updateInt("p1", game.p2);
                //uprs.updateInt("state", game.state);
                //uprs.updateInt("invite", game.invite);
                //uprs.updateInt("first", game.first);

                uprs.updateRow();
            }

        } catch (SQLException e ) {
            printSQLException(e);
        } finally {
            if (stmt != null) { stmt.close(); }
        }

    }

    //save new game
    public void saveGame(Connection con, String name, int turn, int p0, int p1, Object state, int invite, int first) throws SQLException{
        Statement stmt = null;
        try {
            stmt = con.createStatement(
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            ResultSet uprs = stmt.executeQuery("SELECT * FROM GameData");

            uprs.moveToInsertRow();
            uprs.updateString("name", name);
            uprs.updateInt("turn", turn);
            uprs.updateInt("p0", p0);
            uprs.updateInt("p1", p1);
            //uprs.updateInt("state", state);
            uprs.updateInt("invite", invite);
            uprs.updateInt("first", first);

            uprs.insertRow();
            uprs.beforeFirst();
        } catch (SQLException e ) {
            printSQLException(e);
        } finally {
            if (stmt != null) { stmt.close(); }
        }
    }

    //save a new user
    public void saveUser(Connection con, String name, String password) throws SQLException {

        Statement stmt = null;
        try {
            stmt = con.createStatement(
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            ResultSet uprs = stmt.executeQuery("SELECT * FROM UserData");

            uprs.moveToInsertRow();
            uprs.updateString("name", name);
            uprs.updateString("password", password);

            uprs.insertRow();
            uprs.beforeFirst();
        } catch (SQLException e) {
            printSQLException(e);
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

}
