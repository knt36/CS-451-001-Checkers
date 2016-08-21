package game;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import network.Client;
import network.messages.Message;
import network.messages.MessageTypes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static game.Color.RED;
import static game.Disk.*;
import static game.MoveStatus.*;

public class Game implements Message {
    protected static Integer boardSize = 32;
    protected static Integer columns = (int) Math.sqrt(boardSize / 2);
    protected static Integer width = columns * 2;
    protected static Integer startRows = 3;
    public Player turn;
    public Player p1;
    public Player p2;
    public List<Disk> board;
    public String name;
    public List<Integer> lastMove;

    /**
     * Creates a new public game with the initiating player. Colors will be randomized.
     *
     * @param name Unique game name
     * @param u1   Username of the player who created the game
     */
    public Game(String name, String u1) {
        Player p1 = new Player(u1);     // Random color
        Player p2 = p1.opponent(""); // Opposite color of p1
        construct(name, p1, p2);
    }

    /**
     * Creates a new private game between two players. Player user order does not matter as colors will be randomized.
     *
     * @param name Unique game name
     * @param u1   Username of the first player.
     * @param u2   Username of the second player.
     */
    public Game(String name, String u1, String u2) {
        Player p1 = new Player(u1);     // Random color
        Player p2 = p1.opponent(u2); // Opposite color of p1
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
    public Game(String name, Player p1, Player p2, List<Disk> board, Player turn, List<Integer> lastMove) {
        construct(name, p1, p2, board, turn, lastMove);
    }

    /**
     * Copy Constructor. Does not run data validation, but this may change in the future.
     * @param other Game data to copy.
     */
    public Game(Game other) {
        this.name = other.name;
        this.p1 = new Player(other.p1);
        this.p2 = new Player(other.p2);
        this.board = new ArrayList<>(other.board);
        if (other.turn.getColor().equals(p1.getColor())) {
            this.turn = this.p1;
        } else {
            this.turn = this.p2;
        }
        this.lastMove = new ArrayList<>(other.lastMove);
    }

    /**
     * Creates a new board in the initial state.
     *
     * @return Board state, with 12 white and 12 red disks in the starting position, and null disks in all others.
     */
    public static List<Disk> newBoard() {
        List<Disk> board = new ArrayList<>(boardSize);
        // The board is oriented top to bottom. Squares 0-4 should be on the top row of the screen.
        for (int i = 0; i < boardSize; i++) {
            if (i < startRows * columns) {
                board.add(RED_DISK);
            } else if (i < (width - startRows) * columns) {
                board.add(EMPTY);
            } else {
                board.add(WHITE_DISK);
            }
        }
        return board;
    }

    /**
     * Determines if the move is a legal single-square diagonal move. Only checks movement numbers and not disk
     * validity.
     *
     * @param src Source square coordinate
     * @param dst Destination square coordinate
     * @return True if the move is valid.
     */
    public static Boolean legalAdj(Integer src, Integer dst) {
        Integer move = dst - src;
        return Math.abs(move) == columns
                || Math.abs(move + 1) == columns
                && src % (columns * 2) > columns
                || Math.abs(move - 1) == columns
                && src % (columns * 2) < columns - 1;
    }

    /**
     * Determines if the move is a legal jump move. Only checks movement numbers and not disk validity.
     *
     * @param src Source square coordinate
     * @param dst Destination square coordinate
     * @return True if the move is valid.
     */
    public static Boolean legalJmp(Integer src, Integer dst) {
        Integer move = dst - src;
        return (Math.abs(move + 1) == 2 * columns && src % columns > 0)
                || (Math.abs(move - 1) == 2 * columns && src % columns < columns - 1);
    }

    /**
     * Returns the position being jumped over. Assumes that the jump is valid, otherwise returns are undefined
     *
     * @param src Source square coordinate
     * @param dst Destination square coordinate
     * @return Jumped square coordinate.
     */
    public static Integer jumpedSquare(Integer src, Integer dst) {
        return src + Math.floorDiv(dst - src, 2) - Math.floorDiv(src % 8, 4) + 1;
    }

    /**
     * Turns disk into king if applicable, otherwise returns it unchanged
     *
     * @param dst  Destination coordinate
     * @param disk Disk being moved
     * @return Updated disk
     */
    private static Disk kingify(Integer dst, Disk disk) {
        if (disk.red() && dst >= boardSize - columns) {
            return RED_KING;
        } else if (disk.white() && dst < columns) {
            return WHITE_KING;
        } else {
            return disk;
        }
    }

    public static List<Disk> deserializeBoard(String stateBlob) {
        return Arrays.stream(stateBlob.split(",")).map(Disk::fromString).collect(Collectors.toList());
    }

    public static Game fromJson(JsonElement json) {
        try {
            JsonObject root = json.getAsJsonObject();
            Player red = new Player(root.get("red").getAsString(), RED);
            String turnName = root.get("turn").getAsString();
            String u1 = root.get("p1").getAsString();
            Player p1;
            Player p2;
            if (red.getName().equals(u1)) {
                p1 = new Player(red);
                p2 = p1.opponent(root.get("p2").getAsString());
            } else {
                p2 = new Player(red);
                p1 = p2.opponent(u1);
            }
            Player turn;
            if (turnName.equals(p1.getName())) {
                turn = p1;
            } else {
                turn = p2;
            }
            List<Disk> board = deserializeBoard(root.get("board").getAsString());
            String name = root.get("name").getAsString();
            String moveString = root.get("moves").getAsString();
            List<Integer> moves = new ArrayList<>();
            if (!moveString.isEmpty()) {
                moves = Arrays.stream(moveString.split(",")).map(Integer::valueOf).collect(Collectors.toList());
            }
            return new Game(name, p1, p2, board, turn, moves);
        } catch (IllegalStateException | UnsupportedOperationException e) {
            return null;
        }
    }

    /**
     * Constructs a new game with two players
     * @param name Unique game name
     * @param p1 First player
     * @param p2 Second player. Must be the opposite color of the first player
     */
    private void construct(String name, Player p1, Player p2) {
        Player turn;
        if (p1.getColor().equals(RED)) {
            turn = p1;
        } else {
            turn = p2;
        }
        construct(name, p1, p2, newBoard(), turn, new ArrayList<>());
    }

    /**
     * Constructs a game from components if state is valid, otherwise throws IllegalArgumentException.
     * @param name Unique game name
     * @param p1 First player
     * @param p2 Second player. Must have opposite color of first player.
     * @param board Board state. Must have 32 squares, <= 12 white disks, and <= 12 red disks
     * @param turn Current player's turn. Must be either p1 or p2.
     */
    private void construct(String name, Player p1, Player p2, List<Disk> board, Player turn, List<Integer> lastMove) {
        this.name = name;
        this.p1 = p1;
        if (p2.getColor().equals(p1.getColor())) {
            throw new IllegalArgumentException("Both players cannot have the same color");
        }
        this.p2 = p2;
        if (board.size() != boardSize) {
            throw new IllegalArgumentException("Board must contain exactly 32 spaces");
        } else if (board.stream().filter(Disk::red).count() > columns * startRows) {
            throw new IllegalArgumentException("Board must contain <= 12 red disks");
        } else if (board.stream().filter(Disk::white).count() > columns * startRows) {
            throw new IllegalArgumentException("Board must contain <= 12 white disks");
        } else {
            this.board = board;
        }

        if (!turn.getName().equals(p1.getName()) && !turn.getName().equals(p2.getName())) {
            throw new IllegalArgumentException("Turn must be either p1 or p2");
        }
        this.turn = turn;
        this.lastMove = lastMove;
    }

    /**
     * Detects whether the game is public or private.
     * @return True if the game is public and waiting for another player.
     */
    public Boolean isPublicGame() {
        return this.p2.nobody();
    }

    /**
     * Basic filtering of moves for adjMoves and jmpMoves
     *
     * @param move Distance being moved
     * @param src  Source location
     * @return False if the move is invalid, True otherwise (that is, there will be false positives).
     */
    private Boolean moveFilter(Integer move, Integer src) {
        Disk disk = this.getDisk(src);
        return
                // Move is in the right direction
                ((move < 0 && disk.canMoveDown()) || (move >= 0 && disk.canMoveUp()))
                        // Double check board bounds
                        && (src + move) >= 0 && (src + move) < boardSize
                        // Destination is empty
                        && this.getDisk(src + move).empty();
    }

    /**
     * Returns the list of valid adjacent moves for the given disk. Checks the source turn color, if dest is empty, and
     * direction (whether the source is a king or not).
     *
     * @param src Disk location
     * @return Destination squares that would produce valid moves
     */
    public Set<Integer> adjMoves(Integer src) {
        Set<Integer> result = Stream
                // All valid adjacent moves
                .of(-columns - 1, -columns, -columns + 1, columns - 1, columns, columns + 1)
                // Basic filtering for moves
                .filter(move -> moveFilter(move, src))
                .map(move -> src + move)
                // Prevent wrapping around the edge of the board
                .filter(dst -> legalAdj(src, dst))
                .collect(Collectors.toSet());
        return result;
    }

    public Set<Integer> jmpMoves(Integer src) {
        return Stream
                // All valid adjacent moves
                .of(-(2 * columns) - 1, -(2 * columns) + 1, (2 * columns) - 1, (2 * columns) + 1)
                // Basic filtering for moves
                .filter(move -> moveFilter(move, src))
                .map(move -> src + move)
                // Prevent wrapping around the edge of the board
                .filter(dst -> legalJmp(src, dst))
                .filter(dst -> this.getDisk(jumpedSquare(src, dst)).getColor().equals(turn.oppositeColor()))
                .collect(Collectors.toSet());
    }

    private Player nextTurn() {
        if (turn.getName().equals(p1.getName())) {
            return p2;
        } else {
            return p1;
        }
    }

    /**
     * Moves the piece from one space on the board to another.
     *
     * @param src Location of the disk being moved. Must be a the same color as the current 'turn' player.
     * @param dst Location where the disk is being moved to. Must be an empty space (color = NONE).
     * @return True if the move was valid and state was updated, else False
     */
    public MoveStatus move(Integer src, Integer dst) {
        if (!this.lastMove.isEmpty()) {
            if (!this.getDisk(this.lastMove.get(this.lastMove.size() - 1)).getColor().equals(this.turn.getColor())) {
                // If starting a new player's turn, clear the last moves
                this.lastMove = new ArrayList<>();
            } else if (!this.lastMove.get(this.lastMove.size() - 1).equals(src)) {
                // If it's the same player's turn, make sure they're moving the same piece
                return JMP_WRONG_DISK;
            }
        }
        if (src < 0 || dst < 0 || src >= this.board.size() || dst >= this.board.size()) {
            return OUT_OF_BOARD; // Not a square on the board
        }
        if (this.getDisk(src).empty()) {
            return EMPTY_SRC;
        }
        if (!this.getDisk(src).getColor().equals(turn.getColor())) {
            return WRONG_TURN;
        }
        String user = Client.client.getUsername();
        // If user is null, we're the server do don't check
        if (user != null && !user.equals(turn.getName())) {
            return WRONG_TURN;
        }
        if (adjMoves(src).contains(dst)) {
            this.board.set(dst, kingify(dst, this.getDisk(src)));
            this.board.set(src, EMPTY);
            this.lastMove.add(src);
            this.lastMove.add(dst);
            this.turn = this.nextTurn();
            return ADJ;
        } else if (jmpMoves(src).contains(dst)) {
            this.board.set(dst, kingify(dst, this.getDisk(src)));
            this.board.set(src, EMPTY);
            this.board.set(jumpedSquare(src, dst), EMPTY); // Removing jumped square
            if (this.lastMove.isEmpty()) {
                this.lastMove.add(src);
            }
            this.lastMove.add(dst);
            if (jmpMoves(dst).isEmpty()) {
                this.turn = this.nextTurn();
                return JMP;
            } else {
                return JMP_INCOMPLETE;
            }
        }
        return INVALID_MOVE;
    }

    /**
     * Updates the board to the new state, if the new state represents a legal move. If the move is not legal,
     * throws an IllegalArgumentException (since this state should be coming from the server which should always be
     * accurate and errors are a very very bad sign).
     * @param newState New board state
     * @return True if the state was updated.
     */
    public MoveStatus move(Game newState) {
        MoveStatus status = INVALID_STATE;
        Integer src = null;
        for (Integer dst : newState.lastMove) {
            if (src != null) {
                status = move(src, dst);
                if (status.failure()) {
                    return status;
                }
            }
            src = dst;
        }
        if (newState.board.equals(board)) {
            return status;
        } else {
            return INVALID_STATE;
        }
    }

    public Disk getDisk(Integer coord) {
        return board.get(coord);
    }

    public Player red() {
        if (this.p1.getColor().equals(RED)) {
            return p1;
        } else {
            return p2;
        }
    }

    private Boolean moves(Player player) {
        if (!board.stream().anyMatch(d -> d.getColor().equals(player.getColor()))) {
            return false;
        }
        for (int i = 0; i < 32; i++) {
            if (!adjMoves(i).isEmpty() || !jmpMoves(i).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public Player winner() {
        if (!moves(p1)) {
            return p2;
        } else if (!moves(p2)) {
            return p1;
        } else {
            return null;
        }
    }


    public String serializeBoard() {
        return this.board.stream().map(Disk::toString).collect(Collectors.joining(","));
    }

    public JsonElement toJson() {
        JsonObject root = new JsonObject();
        root.addProperty("turn", this.turn.getName());
        root.addProperty("p1", this.p1.getName());
        root.addProperty("p2", this.p2.getName());
        root.addProperty("board", serializeBoard());
        root.addProperty("name", this.name);
        root.addProperty("moves", this.lastMove.stream().map(Object::toString).collect(Collectors.joining(",")));
        root.addProperty("red", this.red().getName());
        return root;
    }

    @Override
    public MessageTypes type() {
        return MessageTypes.GAME;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Game game = (Game) o;

        // Intellij simplified this
        return turn != null ? turn.equals(game.turn) : game.turn == null
                && (p1 != null ? p1.equals(game.p1) : game.p1 == null
                && (p2 != null ? p2.equals(game.p2) : game.p2 == null
                && (board != null ? board.equals(game.board) : game.board == null
                && (name != null ? name.equals(game.name) : game.name == null
                && (lastMove != null ? lastMove.equals(game.lastMove) : game.lastMove == null)))));

    }

    @Override
    public int hashCode() {
        int result = turn.hashCode();
        result = 31 * result + p1.hashCode();
        result = 31 * result + p2.hashCode();
        result = 31 * result + board.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + lastMove.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Game{" +
                "turn=" + turn +
                ", p1=" + p1 +
                ", p2=" + p2 +
                ", board=" + board +
                ", name='" + name + '\'' +
                ", lastMove=" + lastMove +
                '}';
    }
}
