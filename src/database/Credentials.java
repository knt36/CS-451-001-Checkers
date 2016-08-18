package database;

import java.sql.Date;
import java.time.LocalDate;

/**
 *
 */
public class Credentials {
    public String username;
    public String salt;
    public String hash;
    public String token;
    public Date tokenDate;

    public Credentials(String username, String password, String token, Date tokenDate) {
        this.username = username;
        this.token = token;
        this.tokenDate = tokenDate;
        String[] pass = password.split(":");
        this.salt = pass[0];
        this.hash = pass[1];
    }

    public Credentials(String username, String salt, String hash, String token, Date tokenDate) {
        this.username = username;
        this.token = token;
        this.tokenDate = tokenDate;
        this.salt = salt;
        this.hash = hash;
    }

    public Credentials(String username, String salt, String hash, String token) {
        this.username = username;
        this.token = token;
        this.tokenDate = Date.valueOf(LocalDate.now());
        this.salt = salt;
        this.hash = hash;
    }

    public String password() {
        return salt + ":" + hash;
    }
}
