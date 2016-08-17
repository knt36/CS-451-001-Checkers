package network;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * Created by mblaho on 8/13/16.
 *
 * POJO for login requests.
 */
public class LoginRequest {
    private String username;
    private String password;

    public LoginRequest() {
    }

    public LoginRequest(String suppliedUsername, String suppliedPassword) {
        this.password = suppliedPassword;
        this.username = suppliedUsername;
    }

    public static LoginRequest fromJson(JsonElement json) {
        try {
            JsonObject root = json.getAsJsonObject();
            String username = root.get("username").getAsString();
            String password = root.get("password").getAsString();
            return new LoginRequest(username, password);
        } catch (Exception e) {
            return null;
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginRequest{" +
                "TYPE='Login'" +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public JsonElement toJson() {
        JsonObject root = new JsonObject();
        root.addProperty("username", username);
        root.addProperty("password", password);
        return root;
    }
}
