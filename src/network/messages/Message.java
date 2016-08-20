package network.messages;

import com.google.gson.JsonElement;

/**
 *
 */
public interface Message {
    MessageTypes type();

    JsonElement toJson();
}
