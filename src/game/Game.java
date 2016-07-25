package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import static game.Color.*;

public class Game extends Observable {
    public Player turn;
    public Player p1;
    public Player p2;
    public List<Disk> board;
    public String name;

    /**
     * Creates a new public game with the initiating player. Colors will be randomized.
     *
     * @param name Unique game name
     * @param u1   Username of the player who created the game
     */
    public Game(String name, String u1) {
        Player p1 = new Player(u1);     // Random color
        Player p2 = new NullPlayer(p1); // Opposite color of p1
        construct(name, p1, p2);
    }

    /**
     * Creates a new private game between two players. Player name order does not matter as colors will be randomized.
     *
     * @param name Unique game name
     * @param u1   Username of the first player.
     * @param u2   Username of the second player.
     */
    public Game(String name, String u1, String u2) {
        Player p1 = new Player(u1);     // Random color
        Player p2 = new Player(u2, p1); // Opposite color of p1
        construct(name, p1, p2);
    }

    /**
     * Creates game object from existing game data
     *
     * @param name  Unique game name
     * @param p1    First player
     * @param p2    Second player. Must have opposite color of first player
     * @param board Board state. Must have 32 spaces and <= 12 red and <= 12 white pieces.
     * @param turn  Current player's turn. Must be either p1 or p2.
     * @throws IllegalArgumentException if board does not have exactly 32 spaces.
     * @throws IllegalArgumentException if board has more than 12 red disks.
     * @throws IllegalArgumentException if board has more than 12 white disks.
     */
    public Game(String name, Player p1, Player p2, List<Disk> board, Player turn) {
        construct(name, p1, p2, board, turn);
    }

    private static List<Disk> newBoard() {
        List<Disk> board = new ArrayList<>(32);
        for (int i = 0; i < 32; i++) {
            if (i < 12) {
                board.set(i, new Disk(RED));
            } else if (i < 20) {
                board.set(i, new Disk(NONE));
            } else {
                board.set(i, new Disk(WHITE));
            }
        }
        return board;
    }

    private void construct(String name, Player p1, Player p2) {
        this.p1 = p1;
        this.p2 = p2;
        construct(name, p1, p2, newBoard(), redPlayer());
    }

    private void construct(String name, Player p1, Player p2, List<Disk> board, Player turn) {
        this.name = name;
        this.p1 = p1;
        this.p2 = p2;
        if (board.size() != 32) {
            throw new IllegalArgumentException("Board must contain exactly 32 spaces");
        } else if (board.stream().filter(d -> d.color == RED).count() > 12) {
            throw new IllegalArgumentException("Board must contain <= 12 red pieces");
        } else if (board.stream().filter(d -> d.color == WHITE).count() > 12) {
            throw new IllegalArgumentException("Board must contain <= 12 white pieces");
        } else {
            this.board = board;
        }
        this.turn = turn;
    }

    public Boolean isPublicGame() {
        return this.p2 instanceof NullPlayer;
    }

    public Player redPlayer() {
        if (this.p1.color == RED) {
            return p1;
        } else {
            return p2;
        }
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
