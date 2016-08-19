package network.messages;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 */
public class Signup implements Message {
    private String username;
    private String password;

    public Signup() {
    }

    public Signup(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public static Signup fromJson(JsonElement json) {
        try {
            JsonObject root = json.getAsJsonObject();
            String username = root.get("username").getAsString();
            String password = root.get("password").getAsString();
            return new Signup(username, password);
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

    public JsonElement toJson() {
        JsonObject root = new JsonObject();
        root.addProperty("username", username);
        root.addProperty("password", password);
        return root;
    }

    @Override
    public MessageTypes type() {
        return MessageTypes.SIGNUP;
    }
}
