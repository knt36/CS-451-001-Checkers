package game;

import static game.Color.NONE;
import static game.Color.RED;
import static game.Color.WHITE;

import java.util.concurrent.ThreadLocalRandom;

public class Player {
    private final String name;
    private final Color color;

    public Player(String name, Color color) {
        this.name = name;
        if (color == NONE) {
            throw new IllegalArgumentException("Player cannot have color 'None'");
        }
        this.color = color;
    }

    public Player(String name) {
        this.name = name;
        this.color = Color.values()[ThreadLocalRandom.current().nextInt(0, 2)];
    }

    public Player(Player other) {
        this.name = other.name;
        this.color = other.color;
    }

    public Player opponent(String name) {
        return new Player(name, this.oppositeColor());
    }

    public Color getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public Color oppositeColor() {
        if (color.equals(RED)) {
            return WHITE;
        } else {
            return RED;
        }
    }

    public Boolean nobody() {
        return this.name == null || this.name.equals("");
    }
}
