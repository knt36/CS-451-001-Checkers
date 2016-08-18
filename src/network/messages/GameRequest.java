package network.messages;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

/**
 *
 */
public class GameRequest {
    public GameRequest() {

    }

    public static GameRequest fromJson(JsonElement json) {
        return new GameRequest();
    }

    public JsonElement toJson() {
        return new JsonPrimitive("");
    }
}
