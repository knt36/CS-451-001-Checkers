package network;

/**
 * Created by mblaho on 8/13/16.
 *
 * POJO for Account Creation
 */
public class AcctCreateRequest {
    public final String TYPE = "create";
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

    public AcctCreateRequest() {}

    public AcctCreateRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

}
