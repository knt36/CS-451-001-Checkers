package network.messages;

import com.google.gson.JsonElement;

/**
 */
public class Signup extends Login {
    public Signup(String username, String password) {
        super(username, password);
    }

    public static Signup signupFromJson(JsonElement json) {
        Login login = Login.fromJson(json);
        return new Signup(login.getUsername(), login.getPassword());
    }

    @Override
    public MessageTypes type() {
        return MessageTypes.SIGNUP;
    }
}
