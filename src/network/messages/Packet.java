package network.messages;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import database.UserList;
import game.Game;
import game.GameList;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 *
 */
public class Packet {
    // Function should be 'public static Object fromJson(JsonElement json) {}'
    private static Map<String, Function<JsonElement, Message>> fromJson;

    static {
        fromJson = new HashMap<>();
        fromJson.put(Game.class.getSimpleName(), Game::fromJson);
        fromJson.put(Signup.class.getSimpleName(), Signup::fromJson);
        fromJson.put(Ack.class.getSimpleName(), Ack::fromJson);
        fromJson.put(Login.class.getSimpleName(), Login::fromJson);
        fromJson.put(GameList.class.getSimpleName(), GameList::fromJson);
        fromJson.put(GameRequest.class.getSimpleName(), GameRequest::fromJson);
        fromJson.put(GameListRequest.class.getSimpleName(), GameListRequest::fromJson);
        fromJson.put(GameDelete.class.getSimpleName(), GameDelete::fromJson);
        fromJson.put(UserListRequest.class.getSimpleName(), UserListRequest::fromJson);
        fromJson.put(UserList.class.getSimpleName(), UserList::fromJson);
    }

    private String token;
    private Message data;

    public Packet(String token, Message data) {
        this.token = token;
        this.data = data;
    }

    public static Packet perror(String msg) {
        return new Packet("", new Ack(msg, false));
    }

    public static String error(String msg) {
        return perror(msg).toJson();
    }

    public static Packet fromJson(String json) {
        try {
            JsonObject root = new JsonParser().parse(json).getAsJsonObject();
            String token = root.get("token").getAsString();
            String type = root.get("type").getAsString();
            Function<JsonElement, Message> func = fromJson.get(type);
            if (func == null) {
                return null;
            }
            Message data = func.apply(root.get("data"));
            return new Packet(token, data);
        } catch (IllegalStateException | UnsupportedOperationException e) {
            return null;
        }
    }

    public String toJson() {
        JsonObject root = new JsonObject();
        root.addProperty("token", token);
        root.add("data", data.toJson());
        root.addProperty("type", data.getClass().getSimpleName());
        return new Gson().toJson(root);
    }

    public Message getData() {
        return this.data;
    }

    public String getToken() {
        return token;
    }
}
