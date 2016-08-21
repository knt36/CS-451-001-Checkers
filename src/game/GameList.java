package game;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import network.messages.Message;
import network.messages.MessageTypes;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static network.messages.JsonHelper.arrayToList;
import static network.messages.JsonHelper.listToArray;

/**
 *
 */
public class GameList implements Message {
    public List<Game> current;
    public List<Game> pub;

    public GameList(List<Game> pub, List<Game> current) {
        this.pub = pub;
        this.current = current;
    }

    public static GameList fromJson(JsonElement json) {
        JsonObject root = json.getAsJsonObject();
        List<Game> pub = arrayToList(root.get("public").getAsJsonArray(), Game::fromJson);
        List<Game> current = arrayToList(root.get("current").getAsJsonArray(), Game::fromJson);
        return new GameList(pub, current);
    }

    public List<Game> allGames() {
        return Stream.concat(current.stream(), pub.stream()).collect(Collectors.toList());
    }

    public JsonElement toJson() {
        JsonObject root = new JsonObject();
        root.add("public", listToArray(pub));
        root.add("current", listToArray(current));
        return root;
    }

    @Override
    // Is covered in JUnit testing, GameListTest:EqualsTest but it isn't registering.
    public MessageTypes type() {
        return MessageTypes.GAME_LIST;
    }
}
