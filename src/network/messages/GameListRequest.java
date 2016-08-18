package network.messages;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

/**
 *
 */
public class GameListRequest {
    public GameListRequest() {

    }

    public static GameListRequest fromJson(JsonElement json) {
        return new GameListRequest();
    }

    public JsonElement toJson() {
        return new JsonPrimitive("");
    }
}
