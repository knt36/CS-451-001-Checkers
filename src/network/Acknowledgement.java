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
    private Boolean failed;
    private String message;

    public Acknowledgement(String message, Boolean failed) {
        this.message = message;
        this.failed = failed;
    }

    public static Acknowledgement fromJson(JsonElement json) {
        try {
            JsonObject root = json.getAsJsonObject();
            String message = root.get("message").getAsString();
            Boolean failed = root.get("failed").getAsBoolean();
            return new Acknowledgement(message, failed);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String toString() {
        return "Acknowledgement{" +
                "failed=" + failed.toString() +
                ", message='" + message + '\'' +
                '}';
    }

    public JsonElement toJson() {
        JsonObject root = new JsonObject();
        root.addProperty("message", message);
        root.addProperty("failed", failed);
        return root;
    }
}
