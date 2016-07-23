package game;

public class NullPlayer extends Player {
    public NullPlayer(String name, Color color) {
        super(name, color);
    }

    public NullPlayer(Color color) {
        super("", color);
    }
}
