package network.messages;

import database.UserList;
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
    GAME_LIST(GameList.class),
    GAME_DELETE(GameDelete.class),
    USER_LIST_REQUEST(UserListRequest.class),
    USER_LIST(UserList.class);

    public final Class<? extends Message> klass;

    MessageTypes(Class<? extends Message> klass) {
        this.klass = klass;
    }
}
