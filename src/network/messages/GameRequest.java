package network.messages;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

/**
 *
 */
public class GameRequest implements Message {
    public String user;

    public GameRequest(String user) {
        this.user = user;
    }

    public static GameRequest fromJson(JsonElement json) {
        return new GameRequest(json.getAsString());
    }

    public JsonElement toJson() {
        return new JsonPrimitive(user);
    }

    @Override
    public MessageTypes type() {
        return MessageTypes.GAME_REQUEST;
    }
}
