package network.messages;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * POJO for login requests.
 */
public class Login implements Message {
    private String username;
    private String password;

    public Login() {
    }

    public Login(String suppliedUsername, String suppliedPassword) {
        this.password = suppliedPassword;
        this.username = suppliedUsername;
    }

    public static Login fromJson(JsonElement json) {
        try {
            JsonObject root = json.getAsJsonObject();
            String username = root.get("username").getAsString();
            String password = root.get("password").getAsString();
            return new Login(username, password);
        } catch (Exception e) {
            return null;
        }
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public JsonElement toJson() {
        JsonObject root = new JsonObject();
        root.addProperty("username", username);
        root.addProperty("password", password);
        return root;
    }

    @Override
    public MessageTypes type() {
        return MessageTypes.LOGIN;
    }
}
