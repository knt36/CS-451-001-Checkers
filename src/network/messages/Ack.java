package network.messages;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * POJO for Server acknowledgement.
 *
 * State is true or false,
 */
public class Ack {
    private Boolean success;
    private String message;

    public Ack(String message, Boolean success) {
        this.message = message;
        this.success = success;
    }

    public static Ack fromJson(JsonElement json) {
        try {
            JsonObject root = json.getAsJsonObject();
            String message = root.get("message").getAsString();
            Boolean success = root.get("success").getAsBoolean();
            return new Ack(message, success);
        } catch (Exception e) {
            return null;
        }
    }

    public JsonElement toJson() {
        JsonObject root = new JsonObject();
        root.addProperty("message", message);
        root.addProperty("success", success);
        return root;
    }
}
