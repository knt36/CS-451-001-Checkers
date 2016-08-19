package game;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import network.messages.Message;
import network.messages.MessageTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        List<Game> pub = gameList(root.get("public").getAsJsonArray());
        List<Game> current = gameList(root.get("public").getAsJsonArray());
        return new GameList(pub, current);
    }

    private static JsonArray listToArray(List<Game> games) {
        JsonArray array = new JsonArray();
        games.forEach(g -> array.add(g.toJson()));
        return array;
    }

    private static List<Game> gameList(JsonArray array) {
        List<Game> games = new ArrayList<>();
        for (JsonElement json : array) {
            games.add(Game.fromJson(json));
        }
        return games;
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
    public MessageTypes type() {
        return MessageTypes.GAME_LIST;
    }
}
