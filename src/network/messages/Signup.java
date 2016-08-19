package network.messages;

/**
 */
public class Signup extends Login {
    public Signup(String username, String password) {
        super(username, password);
    }

    @Override
    public MessageTypes type() {
        return MessageTypes.SIGNUP;
    }
}
