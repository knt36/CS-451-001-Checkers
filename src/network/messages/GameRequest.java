package network.messages;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

/**
 *
 */
public class GameRequest implements Message {
    public String name;

    public GameRequest(String name) {
        this.name = name;
    }

    public static GameRequest fromJson(JsonElement json) {
        return new GameRequest(json.getAsString());
    }

    public JsonElement toJson() {
        return new JsonPrimitive(name);
    }

    @Override
    public MessageTypes type() {
        return MessageTypes.GAME_REQUEST;
    }
}
