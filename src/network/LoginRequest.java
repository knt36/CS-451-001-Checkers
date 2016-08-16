package network;

/**
 * Created by mblaho on 8/13/16.
 *
 * POJO for login requests.
 */
public class LoginRequest {
    public final String TYPE = "login";
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String username;
    private String password;

    public LoginRequest(){};

    public LoginRequest(String suppliedUsername, String suppliedPassword){
        this.password = suppliedPassword;
        this.username = suppliedUsername;
    }

    @Override
    public String toString() {
        return "LoginRequest{" +
                "TYPE='" + TYPE + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
