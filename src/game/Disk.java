package game;

import static game.Color.*;

public class Disk {
    public static final Disk RED_DISK = new Disk(RED, false);
    public static final Disk WHITE_DISK = new Disk(WHITE, false);
    public static final Disk RED_KING = new Disk(RED, true);
    public static final Disk WHITE_KING = new Disk(WHITE, true);
    public static final Disk EMPTY = new Disk();
    private final Color color;
    private final Boolean king;

    private Disk() {
        this(NONE, false);
    }

    private Disk(Color color, Boolean king) {
        this.color = color;
        this.king = king;
    }

    public Disk inverse() {
        if (this.red()) {
            return WHITE_DISK;
        } else if (this.white()) {
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
