package game;

import java.util.concurrent.ThreadLocalRandom;

import static game.Color.*;

public class Player {
    public String name;
    private Color color;

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

    public Player(String name, Player other) {
        this.name = name;
        this.color = other.oppositeColor();
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        if (color == NONE) {
            throw new IllegalArgumentException("Player cannot have color 'None'");
        }
        this.color = color;
    }

    public Color oppositeColor() {
        if (color.equals(RED)) {
            return WHITE;
        } else {
            return RED;
        }
    }
}
