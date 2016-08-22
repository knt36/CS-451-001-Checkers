package game;

import java.util.concurrent.ThreadLocalRandom;

import static game.Color.*;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;

        return name != null ? name.equals(player.name) : player.name == null && color == player.color;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (color != null ? color.hashCode() : 0);
        return result;
    }

    public Boolean nobody() {
        return this.name == null || this.name.equals("");
    }
}
