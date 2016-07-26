package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static game.Color.*;

public class Game extends Observable {
    public static Set<Integer> validJumpMoves = Stream.of(7, 9).collect(Collectors.toSet());

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
     * Creates game object from existing game data. Will throw exceptions for invalid data.
     *
     * @param name  Unique game name
     * @param p1    First player
     * @param p2    Second player. Must have opposite color of first player
     * @param board Board state. Must have 32 spaces and <= 12 red and <= 12 white disks.
     * @param turn  Current player's turn. Must be either p1 or p2.
     *
     * @throws IllegalArgumentException when player colors are the same.
     * @throws IllegalArgumentException when board does not have exactly 32 spaces.
     * @throws IllegalArgumentException when board has more than 12 red disks.
     * @throws IllegalArgumentException when board has more than 12 white disks.
     * @throws IllegalArgumentException when turn is neither p1 nor p2.
     */
    public Game(String name, Player p1, Player p2, List<Disk> board, Player turn) {
        construct(name, p1, p2, board, turn);
    }

    /**
     * Creates a new board in the initial state.
     *
     * @return Board state, with 12 white and 12 red disks in the starting position, and null disks in all others.
     */
    private static List<Disk> newBoard() {
        List<Disk> board = new ArrayList<>(32);
        // The board is oriented top to bottom. Squares 0-4 should be on the top row of the screen.
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

    /**
     * Constructs a new game with two players
     * @param name Unique game name
     * @param p1 First player
     * @param p2 Second player. Must be the opposite color of the first player
     */
    private void construct(String name, Player p1, Player p2) {
        this.p1 = p1;
        this.p2 = p2;
        construct(name, p1, p2, newBoard(), redPlayer());
    }

    /**
     * Constructs a game from components if state is valid, otherwise throws IllegalArgumentException.
     * @param name Unique game name
     * @param p1 First player
     * @param p2 Second player. Must have opposite color of first player.
     * @param board Board state. Must have 32 squares, <= 12 white disks, and <= 12 red disks
     * @param turn Current player's turn. Must be either p1 or p2.
     */
    private void construct(String name, Player p1, Player p2, List<Disk> board, Player turn) {
        this.name = name;
        this.p1 = p1;
        if (p2.getColor().equals(p1.getColor())) {
            throw new IllegalArgumentException("Both players cannot have the same color");
        }
        this.p2 = p2;
        if (board.size() != 32) {
            throw new IllegalArgumentException("Board must contain exactly 32 spaces");
        } else if (board.stream().filter(d -> d.color == RED).count() > 12) {
            throw new IllegalArgumentException("Board must contain <= 12 red disks");
        } else if (board.stream().filter(d -> d.color == WHITE).count() > 12) {
            throw new IllegalArgumentException("Board must contain <= 12 white disks");
        } else {
            this.board = board;
        }

        if (!turn.equals(p1) && !turn.equals(p2)) {
            throw new IllegalArgumentException("Turn must be either p1 or p2");
        }
        this.turn = turn;
    }

    /**
     * Detects whether the game is public or private.
     * @return True if the game is public and waiting for another player.
     */
    public Boolean isPublicGame() {
        return this.p2 instanceof NullPlayer;
    }

    /**
     * Returns the red player
     * @return Either p1 or p2, depending on which one has color == RED.
     */
    public Player redPlayer() {
        if (this.p1.getColor().equals(RED)) {
            return p1;
        } else {
            return p2;
        }
    }

    /**
     * Moves the piece from one space on the board to another.
     *
     * @param src Location of the disk being moved. Must be a the same color as the current 'turn' player.
     * @param dst Location where the disk is being moved to. Must be an empty space (color = NONE).
     * @return True if the move was valid and state was updated, else False
     */
    public Boolean move(Integer src, Integer dst) {
        Disk srcDisk = this.board.get(src);
        Disk dstSquare = this.board.get(dst);
        if (srcDisk.color != turn.getColor()) {
            // Attempted to move wrong color or blank space
            return false;
        }
        if (!dstSquare.empty()) {
            // Attempted to move to a filled square
            return false;
        }
        if (legalSingle(src, dst)) {
            this.board.set(dst, srcDisk);
            this.board.set(src, dstSquare); // dstSquare is empty, so just swap them
            return true;
        }
        if (legalJump(src, dst) && this.board.get(jumpedSquare(src, dst)).color.equals(turn.oppositeColor())) {
            this.board.set(dst, srcDisk);
            this.board.set(src, dstSquare);
            this.board.set(jumpedSquare(src, dst), new Disk()); // Removing jumped square
            return true;
        }
        return false; // Neither legal move nor legal jump
    }

    /**
     * Determines if the move is a legal single-square diagonal move. Only checks movement numbers and not disk
     * validity.
     *
     * @param src Source square coordinate
     * @param dst Destination square coordinate
     * @return True if the move is valid.
     */
    private Boolean legalSingle(Integer src, Integer dst) {
        Integer move = Math.abs(src - dst);
        return move.equals(4)
                || (move.equals(3) && src % 8 >= 6)
                || (move.equals(5) && src % 8 <= 2);
    }

    /**
     * Determines if the move is a legal jump move. Only checks movement numbers and not disk validity.
     *
     * @param src Source square coordinate
     * @param dst Destination square coordinate
     * @return True if the move is valid.
     */
    private Boolean legalJump(Integer src, Integer dst) {
        Integer move = Math.abs(src - dst);
        return (move.equals(7) && src % 4 >= 1)
                || (move.equals(9) && src % 4 <= 2);
    }

    /**
     * Returns the position being jumped over. Assumes that the jump is valid, otherwise returns are undefined
     *
     * @param src Source square coordinate
     * @param dst Destination square coordinate
     * @return Jumped square coordinate.
     */
    private Integer jumpedSquare(Integer src, Integer dst) {
        Integer move = Math.abs(src - dst);
        Integer shift = -Math.floorDiv(move, 4);
        if (move.equals(9)) {
            return Math.max(src, dst) - 4 + shift;
        } else {
            return Math.max(src, dst) - 3 + shift;
        }
    }

    /**
     * Updates the board to the new state, if the new state represents a legal move. If the move is not legal,
     * throws an IllegalArgumentException (since this state should be coming from the server which should always be
     * accurate and errors are a very very bad sign).
     * @param newState New board state
     * @return True if the state was updated.
     */
    public Boolean move(Game newState) {
        return false;
    }

    public Disk getSquare(Integer coord) {
        return board.get(coord);
    }
}
