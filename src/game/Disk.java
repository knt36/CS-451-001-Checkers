package game;

import static game.Color.NONE;

public class Disk {
    public Color color;
    public Boolean king;

    public Disk() {
        this(NONE);
    }

    public Disk(Color color) {
        this(color, false);
    }

    public Disk(Color color, Boolean king) {
        this.color = color;
        this.king = king;
    }
}
