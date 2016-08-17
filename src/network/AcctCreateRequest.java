package network;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 */
public class AcctCreateRequest {
    private String username;
    private String password;

    public AcctCreateRequest() {
    }

    public AcctCreateRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public static AcctCreateRequest fromJson(JsonElement json) {
        try {
            JsonObject root = json.getAsJsonObject();
            String username = root.get("username").getAsString();
            String password = root.get("password").getAsString();
            return new AcctCreateRequest(username, password);
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "AcctCreateRequest{" +
                "TYPE=Create" +
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
