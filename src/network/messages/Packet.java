package network.messages;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import game.Game;
import game.GameList;

/**
 *
 */
public class Packet {
    // Function should be 'public static Object fromJson(JsonElement json) {}'
    private static Map<String, Function<JsonElement, Message>> fromJson;
    // Function should be 'public JsonElement toJson() {}'
    private static Map<Class, Function<Message, JsonElement>> toJson;

    static {
        toJson = new HashMap<>();
        toJson.put(Game.class, game -> ((Game) game).toJson());
        toJson.put(Signup.class, req -> ((Signup) req).toJson());
        toJson.put(Ack.class, req -> ((Ack) req).toJson());
        toJson.put(Login.class, req -> ((Login) req).toJson());
        toJson.put(GameList.class, req -> ((GameList) req).toJson());
        toJson.put(GameRequest.class, req -> ((GameRequest) req).toJson());
        toJson.put(GameListRequest.class, req -> ((GameListRequest) req).toJson());

        fromJson = new HashMap<>();
        fromJson.put(Game.class.getSimpleName(), Game::fromJson);
        fromJson.put(Signup.class.getSimpleName(), Signup::fromJson);
        fromJson.put(Ack.class.getSimpleName(), Ack::fromJson);
        fromJson.put(Login.class.getSimpleName(), Login::fromJson);
        fromJson.put(GameList.class.getSimpleName(), GameList::fromJson);
        fromJson.put(GameRequest.class.getSimpleName(), GameRequest::fromJson);
        fromJson.put(GameListRequest.class.getSimpleName(), GameListRequest::fromJson);
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
        Function<Message, JsonElement> func = toJson.get(data.getClass());
        if (func == null) {
            return null;
        }
        root.add("data", toJson.get(data.getClass()).apply(data));
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
