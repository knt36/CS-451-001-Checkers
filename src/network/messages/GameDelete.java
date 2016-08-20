package network.messages;

/**
 */
public class GameDelete extends GameRequest {
    public GameDelete(String name) {
        super(name);
    }

    @Override
    public MessageTypes type() {
        return MessageTypes.GAME_DELETE;
    }
}
