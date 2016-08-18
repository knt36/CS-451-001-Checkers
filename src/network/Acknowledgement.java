package network;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * Created by mblaho on 8/16/16.
 *
 * POJO for Server acknowledgement.
 *
 * State is true or false,
 */
public class Acknowledgement {
    private Boolean success;
    private String message;

    public Acknowledgement(String message, Boolean success) {
        this.message = message;
        this.success = success;
    }

    public static Acknowledgement fromJson(JsonElement json) {
        try {
            JsonObject root = json.getAsJsonObject();
            String message = root.get("message").getAsString();
            Boolean success = root.get("success").getAsBoolean();
            return new Acknowledgement(message, success);
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
