package network.messages;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 */
public final class JsonHelper {
    private JsonHelper() {
    }

    public static JsonArray stringListToArray(List<String> list) {
        return listToArray(list, JsonPrimitive::new);
    }

    public static <T extends Message> JsonArray listToArray(List<T> list) {
        return listToArray(list, Message::toJson);
    }

    public static <T> JsonArray listToArray(List<T> list, Function<T, JsonElement> toJson) {
        JsonArray array = new JsonArray();
        list.forEach(i -> array.add(toJson.apply(i)));
        return array;
    }

    public static <T> List<T> arrayToList(JsonArray array, Function<JsonElement, T> fromJson) {
        List<T> list = new ArrayList<>();
        for (JsonElement json : array) {
            list.add(fromJson.apply(json));
        }
        return list;
    }
}
