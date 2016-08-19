package network.messages;

import game.Game;
import game.GameList;

/**
 *
 */
public enum MessageTypes {
    ACK(Ack.class),
    GAME_LIST_REQUEST(GameListRequest.class),
    GAME_REQUEST(GameRequest.class),
    LOGIN(Login.class),
    SIGNUP(Signup.class),
    GAME(Game.class),
    GAME_LIST(GameList.class);

    public final Class<? extends Message> klass;

    private MessageTypes(Class<? extends Message> klass) {
        this.klass = klass;
    }
}
