package network.messages;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

/**
 */
public class UserListRequest implements Message {
    public String str;

    public UserListRequest(String str) {
        this.str = str;
    }

    public static UserListRequest fromJson(JsonElement json) {
        return new UserListRequest(json.getAsString());
    }

    public JsonElement toJson() {
        return new JsonPrimitive(str);
    }

    @Override
    public MessageTypes type() {
        return MessageTypes.USER_LIST_REQUEST;
    }
}
