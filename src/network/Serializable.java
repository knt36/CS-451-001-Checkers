package network;

import com.google.gson.JsonElement;

/**
 *
 */
public interface Serializable {
    public String toJson();

    public JsonElement toJsonElement();
}
