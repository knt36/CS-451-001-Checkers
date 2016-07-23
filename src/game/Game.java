package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import static game.Color.RED;
import static game.Color.WHITE;

public class Game extends Observable {
    public Player turn;
    public Player p1;
    public Player p2;
    public List<Disk> board;
    public String name;

    public Game(String name, String p1) {
        this.name = name;
        this.p1 = new Player(p1, RED);
        this.p2 = new NullPlayer(WHITE);
        this.board = new ArrayList<>(32);
        this.turn = this.p1;
    }

    public Game(String name, String p1, String p2) {
        this.name = name;
        this.p1 = new Player(p1, RED);
        this.p2 = new Player(p2, WHITE);
        this.board = new ArrayList<>(32);
        this.turn = this.p1;
    }

    public Boolean move(int src, int dst) {
        return false;
    }

    public Boolean move(Game newState) {
        return false;
    }

    public Disk getSquare(int coord) {
        return board.get(coord);
    }
}
