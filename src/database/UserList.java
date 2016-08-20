package database;

import com.google.gson.JsonElement;
import network.messages.Message;
import network.messages.MessageTypes;

import java.util.List;

import static network.messages.JsonHelper.arrayToList;
import static network.messages.JsonHelper.stringListToArray;

/**
 */
public class UserList implements Message {
    private List<String> users;

    public UserList(List<String> users) {
        this.users = users;
    }

    public static UserList fromJson(JsonElement json) {
        return new UserList(arrayToList(json.getAsJsonArray(), JsonElement::getAsString));
    }

    public JsonElement toJson() {
        return stringListToArray(users);
    }

    @Override
    public MessageTypes type() {
        return MessageTypes.USER_LIST;
    }

    public List<String> getUsers() {
        return users;
    }
}
