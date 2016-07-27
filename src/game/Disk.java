package game;

import static game.Color.*;

public class Disk {
    public static Disk RED_DISK = new Disk(RED, false);
    public static Disk WHITE_DISK = new Disk(WHITE, false);
    public static Disk RED_KING = new Disk(RED, true);
    public static Disk WHITE_KING = new Disk(WHITE, true);
    public static Disk EMPTY = new Disk();
    private Color color;
    private Boolean king;

    private Disk() {
        this(NONE, false);
    }

    private Disk(Color color, Boolean king) {
        this.color = color;
        this.king = king;
    }

    public static Disk inverse(Disk other) {
        if (other.red()) {
            return WHITE_DISK;
        } else if (other.white()) {
            return RED_DISK;
        } else {
            return EMPTY;
        }
    }

    /**
     * Checks if the square is empty
     *
     * @return color == None
     */
    public Boolean empty() {
        return this.color.equals(NONE);
    }

    public Boolean canMoveUp() {
        return this.color.equals(RED) || this.king;
    }

    public Boolean canMoveDown() {
        return this.color.equals(WHITE) || this.king;
    }

    public Boolean king() {
        return king;
    }

    public Color getColor() {
        return color;
    }

    public Boolean red() {
        return color == RED;
    }

    public Boolean white() {
        return color == WHITE;
    }
}
