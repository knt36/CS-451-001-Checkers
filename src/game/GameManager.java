package game;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 */
public class GameManager {
    public List<Game> current;
    public List<Game> pub;

    public List<Game> allGames() {
        return Stream.concat(current.stream(), pub.stream()).collect(Collectors.toList());
    }
}
