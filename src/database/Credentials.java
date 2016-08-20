package database;

import java.sql.Date;
import java.time.LocalDate;

/**
 *
 */
public class Credentials {
    public String username;
    public String token;
    public Date tokenDate;
    private String salt;
    private String hash;

    public Credentials(String username, String password, String token, Date tokenDate) {
        this.username = username;
        this.token = token;
        this.tokenDate = tokenDate;
        String[] pass = password.split(":");
        this.setSalt(pass[0]);
        this.setHash(pass[1]);
    }

    public Credentials(String username, String salt, String hash, String token, Date tokenDate) {
        this.username = username;
        this.token = token;
        this.tokenDate = tokenDate;
        this.setSalt(salt);
        this.setHash(hash);
    }

    public Credentials(String username, String salt, String hash, String token) {
        this(username, salt, hash, token, Date.valueOf(LocalDate.now()));
    }
    public Credentials(String username, String salt, String hash) {
        this(username, salt, hash, "", Date.valueOf(LocalDate.now()));
    }

    public String getSalt() {
        return this.salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getHash() {
        return this.hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String password() {
        return salt + ":" + hash;
    }


    public void updateTokenDate() {
        this.tokenDate = Date.valueOf(LocalDate.now());
    }

    @Override
    public String toString() {
        return "Credentials{" +
                "username='" + username + '\'' +
                ", token='" + token + '\'' +
                ", tokenDate=" + tokenDate +
                ", salt='" + salt + '\'' +
                ", hash='" + hash + '\'' +
                ", saltDecoded ='" + getSalt() + '\'' +
                ", hashDecoded='" + getHash() + '\'' +
                '}';
    }
}
