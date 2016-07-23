package game;

import java.util.List;

/**
 * Created by maxm on 7/21/2016.
 */
public class Game {
    public Player turn;
    public Player p1;
    public Player p2;
    public List<Disk> board;

    public Boolean move(int src, int dst) {
        return false;
    }

    public Disk getSquare(int coord) {
        return board.get(coord);
    }
}
