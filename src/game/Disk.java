package game;

import static game.Color.*;

public class Disk {
    public static final Disk RED_DISK = new Disk(RED, false);
    public static final Disk WHITE_DISK = new Disk(WHITE, false);
    public static final Disk RED_KING = new Disk(RED, true);
    public static final Disk WHITE_KING = new Disk(WHITE, true);
    public static final Disk EMPTY = new Disk(NONE, false);
    private final Color color;
    private final Boolean king;

    private Disk(Color color, Boolean king) {
        this.color = color;
        this.king = king;
    }

    public static Disk fromString(String s) {
        if (s.startsWith("r")) {
            if (s.endsWith("k")) {
                return RED_KING;
            } else {
                return RED_DISK;
            }
        } else if (s.startsWith("w")) {
            if (s.endsWith("k")) {
                return WHITE_KING;
            } else {
                return WHITE_DISK;
            }
        } else {
            return EMPTY;
        }
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

    public boolean king() {
        return this.king;
    }

    @Override
    public String toString() {
        String result = "";
        if (this.red()) {
            result += "r";
        } else if (this.white()) {
            result += "w";
        } else {
            result += "_";
        }
        if (this.king()) {
            result += "k";
        } else {
            result += "_";
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Disk disk = (Disk) o;

        return color == disk.color && (king != null ? king.equals(disk.king) : disk.king == null);

    }

    @Override
    public int hashCode() {
        int result = color.hashCode();
        result = 31 * result + king.hashCode();
        return result;
    }
}
