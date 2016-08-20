package network.messages;

import com.google.gson.JsonElement;

/**
 */
public class GameDelete extends GameRequest {
    public GameDelete(String name) {
        super(name);
    }

    public static GameDelete gameDeleteFromJson(JsonElement json) {
        GameRequest request = GameRequest.fromJson(json);
        return new GameDelete(request.name);
    }

    @Override
    public MessageTypes type() {
        return MessageTypes.GAME_DELETE;
    }
}
