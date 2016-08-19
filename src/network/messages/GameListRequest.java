package network.messages;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

/**
 *
 */
public class GameListRequest implements Message {
    public String name;

    public GameListRequest(String name) {
        this.name = name;
    }

    public static GameListRequest fromJson(JsonElement json) {
        return new GameListRequest(json.getAsString());
    }

    public JsonElement toJson() {
        return new JsonPrimitive(name);
    }

    @Override
    public MessageTypes type() {
        return MessageTypes.GAME_LIST_REQUEST;
    }
}
