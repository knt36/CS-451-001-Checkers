package game;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;

import static game.Color.RED;
import static game.Color.WHITE;
import static game.Disk.*;
import static game.MoveStatus.*;
import static java.util.Arrays.asList;
import static org.junit.Assert.*;

/**
 *
 */
public class GameTest {
    private static Integer sampleSrc = 0;
    private static Integer sampleAdjDst = 4;
    private static Integer sampleJmpDst = 9;
    private static String sampleName = "test";
    private static Player redPlayer = new Player("red player", RED);
    private static Player whitePlayer = new Player("white player", WHITE);
    private static List<Disk> sampleBoard = Game.newBoard(); // Just going to trust this function, maybe not a good idea
    private static Game sampleGame = new Game(sampleName, redPlayer, whitePlayer, sampleBoard, redPlayer, new ArrayList<>());

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    /**
     * Sets up the board to move disk at src to dst
     *
     * @param src  Source coordinate
     * @param dst  Destination coordinate
     * @param disk Disk at source
     * @return Setup game board with all spaces empty except src
     */
    private static Game setupBoardAdj(Integer src, Integer dst, Disk disk) {
        Game testGame = new Game(sampleGame); // Copy the sample board
        testGame.board.replaceAll(d -> EMPTY); // Clear the board to prevent interference
        if (disk.red()) {
            testGame.turn = redPlayer;
        } else {
            testGame.turn = whitePlayer;
        }
        testGame.board.set(src, disk);
        return testGame;
    }

    /**
     * Sets up board to move disk at src to dst and places opposite colored disk to jump over
     *
     * @param src  Source coordinate
     * @param dst  Destination coordinate
     * @param disk Disk at source (opposite color placed between src and dst)
     * @return Setup game board with all spaces empty except src and jumped square
     */
    private static Game setupBoardJmp(Integer src, Integer dst, Disk disk) {
        Game testGame = setupBoardAdj(src, dst, disk);
        try {
            testGame.board.set(Game.jumpedSquare(src, dst), disk.inverse());
        } catch (IndexOutOfBoundsException e) {
            // Do nothing since we're probably testing for failure and got undefined behavior. If we return a board
            // with no jumpable piece, it will return "false" for the move anyway.
        }
        return testGame;
    }

    /**
     * Asserts that an adjacent move can be made with all disk configurations
     *
     * @param src Source coordinate
     * @param dst Destination coordinate
     */
    private static void assertMoveAdj(Integer src, Integer dst) {
        Game testGame;
        if (src < dst) {
            testGame = setupBoardAdj(src, dst, RED_DISK);
            assertEquals(ADJ, testGame.move(src, dst));
            if (dst >= 28) {
                assertEquals(RED_KING, testGame.getDisk(dst));
            } else {
                assertEquals(RED_DISK, testGame.getDisk(dst));
            }
            assertTrue(setupBoardAdj(src, dst, WHITE_DISK).move(src, dst).failure());
        } else {
            testGame = setupBoardAdj(src, dst, WHITE_DISK);
            assertEquals(ADJ, testGame.move(src, dst));
            if (dst <= 3) {
                assertEquals(WHITE_KING, testGame.getDisk(dst));
            } else {
                assertEquals(WHITE_DISK, testGame.getDisk(dst));
            }
            assertTrue(setupBoardAdj(src, dst, RED_DISK).move(src, dst).failure());
        }
        assertEquals(ADJ, setupBoardAdj(src, dst, RED_KING).move(src, dst));
        assertEquals(ADJ, setupBoardAdj(src, dst, WHITE_KING).move(src, dst));
    }

    /**
     * Asserts that a jump move can be made with all disk configurations
     *
     * @param src Source coordinate
     * @param dst Destination coordinate
     */
    private static void assertMoveJmp(Integer src, Integer dst) {
        Game testGame;
        if (src < dst) {
            testGame = setupBoardJmp(src, dst, RED_DISK);
            assertEquals(JMP, testGame.move(src, dst));
            if (dst >= 28) {
                assertEquals(RED_KING, testGame.getDisk(dst));
            } else {
                assertEquals(RED_DISK, testGame.getDisk(dst));
            }
            assertTrue(setupBoardJmp(src, dst, WHITE_DISK).move(src, dst).failure());
        } else {
            testGame = setupBoardJmp(src, dst, WHITE_DISK);
            assertEquals(JMP, testGame.move(src, dst));
            if (dst <= 3) {
                assertEquals(WHITE_KING, testGame.getDisk(dst));
            } else {
                assertEquals(WHITE_DISK, testGame.getDisk(dst));
            }
            assertTrue(setupBoardJmp(src, dst, RED_DISK).move(src, dst).failure());
        }
        assertEquals(JMP, setupBoardJmp(src, dst, RED_KING).move(src, dst));
        assertEquals(JMP, setupBoardJmp(src, dst, WHITE_KING).move(src, dst));
    }

    /**
     * Asserts that a move cannot be made with any disk configuration
     *
     * @param src Source coordinate
     * @param dst Destination coordinate
     */
    private static void assertMoveFalse(Integer src, Integer dst) {
        assertFalse(setupBoardAdj(src, dst, RED_DISK).move(src, dst).success());
        assertFalse(setupBoardAdj(src, dst, WHITE_DISK).move(src, dst).success());
        assertFalse(setupBoardAdj(src, dst, RED_KING).move(src, dst).success());
        assertFalse(setupBoardAdj(src, dst, WHITE_KING).move(src, dst).success());
        assertFalse(setupBoardJmp(src, dst, RED_DISK).move(src, dst).success());
        assertFalse(setupBoardJmp(src, dst, WHITE_DISK).move(src, dst).success());
        assertFalse(setupBoardJmp(src, dst, RED_KING).move(src, dst).success());
        assertFalse(setupBoardJmp(src, dst, WHITE_KING).move(src, dst).success());
    }

    /**
     * Tests the 'isPublicGame' function
     *
     * @throws Exception
     */
    @Test
    public void isPublicGame() throws Exception {
        Game testPublic = new Game("test", "p1");
        assertTrue(testPublic.isPublicGame());
        Game testPrivate = new Game("test", "p1", "p2");
        assertFalse(testPrivate.isPublicGame());
    }

    /**
     * Tests that a jump cannot be made over an empty space
     *
     * @throws Exception
     */
    @Test
    public void jumpEmpty() throws Exception {
        assertFalse(setupBoardAdj(sampleSrc, sampleJmpDst, RED_DISK).move(sampleSrc, sampleJmpDst).success());
    }

    /**
     * Tests that a jump cannot be made over a piece of the same color
     *
     * @throws Exception
     */
    @Test
    public void jumpSameColor() throws Exception {
        Disk testDisk = RED_DISK;
        Game testGame = setupBoardAdj(sampleSrc, sampleJmpDst, testDisk);
        testGame.board.set(Game.jumpedSquare(sampleSrc, sampleJmpDst), testDisk);
        assertTrue(testGame.move(sampleSrc, sampleJmpDst).failure());
    }

    /**
     * Tests a move outside of the board space. Probably shouldn't happen in production, but can't hurt to check
     *
     * @throws Exception
     */
    @Test
    public void moveOutsideBoard() throws Exception {
        MoveStatus status = setupBoardAdj(sampleSrc, sampleSrc - 4, RED_DISK).move(sampleSrc, sampleSrc - 4);
        assertEquals(OUT_OF_BOARD, status);
        assertTrue(status.serious());
    }

    @Test
    public void jumpTwo() throws Exception {
        Disk testDisk = RED_DISK;
        Game testGame = setupBoardJmp(sampleSrc, sampleJmpDst, testDisk);
        for (Integer space : asList(3, 4, 5)) {
            testGame.board.set(sampleJmpDst + space, testDisk.inverse());
        }
        assertEquals(JMP_INCOMPLETE, testGame.move(sampleSrc, sampleJmpDst));
        assertEquals(JMP, testGame.move(sampleJmpDst, testGame.jmpMoves(sampleJmpDst).iterator().next()));
    }

    @Test
    public void jumpWrongDisk() throws Exception {
        Disk testDisk = RED_DISK;
        Game testGame = setupBoardJmp(sampleSrc, sampleJmpDst, testDisk);
        for (Integer space : asList(3, 4, 5)) {
            testGame.board.set(sampleJmpDst + space, testDisk.inverse());
        }
        assertEquals(JMP_INCOMPLETE, testGame.move(sampleSrc, sampleJmpDst));
        assertEquals(JMP_WRONG_DISK, testGame.move(sampleSrc, sampleJmpDst));
    }

    /**
     * Tests all moves across all board coordinates exhaustively and checks that all valid moves fit into the exact set
     * of 170 possible cases.
     *
     * @throws Exception
     */
    @Test
    public void moveExhaustiveLocations() throws Exception {
        List<Integer> adjMoves;
        List<Integer> jmpMoves;
        int totalMoves = 0;
        for (Integer src = 0; src < 32; src++) {
            adjMoves = new ArrayList<>();
            jmpMoves = new ArrayList<>();
            for (Integer dst = 0; dst < 32; dst++) {
                if (Game.legalAdj(src, dst)) {
                    assertMoveAdj(src, dst);
                    totalMoves++;
                    adjMoves.add(dst);
                } else if (Game.legalJmp(src, dst)) {
                    assertMoveJmp(src, dst);
                    totalMoves++;
                    jmpMoves.add(dst);
                } else {
                    assertMoveFalse(src, dst);
                }
            }
            assertTrue(adjMoves.size() <= 4); // Only 4 diagonal directions
            assertTrue(jmpMoves.size() <= 4); // Same here
            // There's only 170 moves and the only other way to test is to duplicate the same algorithm, so we'll test
            // it the hard way
            switch (src) {
                case 0:
                    assertEquals(asList(4, 5), adjMoves);
                    assertEquals(asList(9), jmpMoves);
                    break;
                case 1:
                    assertEquals(asList(5, 6), adjMoves);
                    assertEquals(asList(8, 10), jmpMoves);
                    break;
                case 2:
                    assertEquals(asList(6, 7), adjMoves);
                    assertEquals(asList(9, 11), jmpMoves);
                    break;
                case 3:
                    assertEquals(asList(7), adjMoves);
                    assertEquals(asList(10), jmpMoves);
                    break;
                case 4:
                    assertEquals(asList(0, 8), adjMoves);
                    assertEquals(asList(13), jmpMoves);
                    break;
                case 5:
                    assertEquals(asList(0, 1, 8, 9), adjMoves);
                    assertEquals(asList(12, 14), jmpMoves);
                    break;
                case 6:
                    assertEquals(asList(1, 2, 9, 10), adjMoves);
                    assertEquals(asList(13, 15), jmpMoves);
                    break;
                case 7:
                    assertEquals(asList(2, 3, 10, 11), adjMoves);
                    assertEquals(asList(14), jmpMoves);
                    break;
                case 8:
                    assertEquals(asList(4, 5, 12, 13), adjMoves);
                    assertEquals(asList(1, 17), jmpMoves);
                    break;
                case 9:
                    assertEquals(asList(5, 6, 13, 14), adjMoves);
                    assertEquals(asList(0, 2, 16, 18), jmpMoves);
                    break;
                case 10:
                    assertEquals(asList(6, 7, 14, 15), adjMoves);
                    assertEquals(asList(1, 3, 17, 19), jmpMoves);
                    break;
                case 11:
                    assertEquals(asList(7, 15), adjMoves);
                    assertEquals(asList(2, 18), jmpMoves);
                    break;
                case 12:
                    assertEquals(asList(8, 16), adjMoves);
                    assertEquals(asList(5, 21), jmpMoves);
                    break;
                case 13:
                    assertEquals(asList(8, 9, 16, 17), adjMoves);
                    assertEquals(asList(4, 6, 20, 22), jmpMoves);
                    break;
                case 14:
                    assertEquals(asList(9, 10, 17, 18), adjMoves);
                    assertEquals(asList(5, 7, 21, 23), jmpMoves);
                    break;
                case 15:
                    assertEquals(asList(10, 11, 18, 19), adjMoves);
                    assertEquals(asList(6, 22), jmpMoves);
                    break;
                case 16:
                    assertEquals(asList(12, 13, 20, 21), adjMoves);
                    assertEquals(asList(9, 25), jmpMoves);
                    break;
                case 17:
                    assertEquals(asList(13, 14, 21, 22), adjMoves);
                    assertEquals(asList(8, 10, 24, 26), jmpMoves);
                    break;
                case 18:
                    assertEquals(asList(14, 15, 22, 23), adjMoves);
                    assertEquals(asList(9, 11, 25, 27), jmpMoves);
                    break;
                case 19:
                    assertEquals(asList(15, 23), adjMoves);
                    assertEquals(asList(10, 26), jmpMoves);
                    break;
                case 20:
                    assertEquals(asList(16, 24), adjMoves);
                    assertEquals(asList(13, 29), jmpMoves);
                    break;
                case 21:
                    assertEquals(asList(16, 17, 24, 25), adjMoves);
                    assertEquals(asList(12, 14, 28, 30), jmpMoves);
                    break;
                case 22:
                    assertEquals(asList(17, 18, 25, 26), adjMoves);
                    assertEquals(asList(13, 15, 29, 31), jmpMoves);
                    break;
                case 23:
                    assertEquals(asList(18, 19, 26, 27), adjMoves);
                    assertEquals(asList(14, 30), jmpMoves);
                    break;
                case 24:
                    assertEquals(asList(20, 21, 28, 29), adjMoves);
                    assertEquals(asList(17), jmpMoves);
                    break;
                case 25:
                    assertEquals(asList(21, 22, 29, 30), adjMoves);
                    assertEquals(asList(16, 18), jmpMoves);
                    break;
                case 26:
                    assertEquals(asList(22, 23, 30, 31), adjMoves);
                    assertEquals(asList(17, 19), jmpMoves);
                    break;
                case 27:
                    assertEquals(asList(23, 31), adjMoves);
                    assertEquals(asList(18), jmpMoves);
                    break;
                case 28:
                    assertEquals(asList(24), adjMoves);
                    assertEquals(asList(21), jmpMoves);
                    break;
                case 29:
                    assertEquals(asList(24, 25), adjMoves);
                    assertEquals(asList(20, 22), jmpMoves);
                    break;
                case 30:
                    assertEquals(asList(25, 26), adjMoves);
                    assertEquals(asList(21, 23), jmpMoves);
                    break;
                case 31:
                    assertEquals(asList(26, 27), adjMoves);
                    assertEquals(asList(22), jmpMoves);
                    break;
            }
        }
        assertEquals(170, totalMoves);
    }

    /**
     * Tests that sending a new state to the server produces a valid adjacent move
     *
     * @throws Exception
     */
    @Test
    public void moveStateAdj() throws Exception {
        Disk testDisk = RED_DISK;
        Game testGame = setupBoardAdj(sampleSrc, sampleAdjDst, testDisk);
        Game newState = new Game(testGame);
        assertEquals(ADJ, newState.move(sampleSrc, sampleAdjDst));
        assertEquals(ADJ, testGame.move(newState));
    }

    /**
     * Tests that sending a new state to the server produces a multiple-step jump move
     *
     * @throws Exception
     */
    @Test
    public void moveStateJmp() throws Exception {
        Disk testDisk = RED_DISK;
        Game testGame = setupBoardJmp(sampleSrc, sampleJmpDst, testDisk);
        for (Integer space : asList(3, 4, 5)) {
            testGame.board.set(sampleJmpDst + space, testDisk.inverse());
        }
        Game newState = new Game(testGame);
        assertEquals(JMP_INCOMPLETE, newState.move(sampleSrc, sampleJmpDst));
        assertEquals(JMP, newState.move(sampleJmpDst, newState.jmpMoves(sampleJmpDst).iterator().next()));
        assertEquals(JMP, testGame.move(newState));
    }

    /**
     * Tests that sending an invalid board state produces an error
     *
     * @throws Exception
     */
    @Test
    public void moveStateFailed() throws Exception {
        Disk testDisk = RED_DISK;
        Game testGame = setupBoardAdj(sampleSrc, sampleAdjDst, testDisk);
        Game newState = new Game(testGame);
        newState.lastMove = asList(sampleSrc, sampleAdjDst);
        assertTrue(testGame.move(newState).serious());
    }

    /**
     * Tests that the board returns the correct disk for a square
     *
     * @throws Exception
     */
    @Test
    public void getSquare() throws Exception {
        for (Integer coordinate = 0; coordinate < 32; coordinate++) {
            assertEquals(sampleBoard.get(coordinate), sampleGame.getDisk(coordinate));
        }
    }

    @Test
    public void constructInvalidPlayers() {
        thrown.expect(IllegalArgumentException.class);
        Game testGame = new Game(sampleName, redPlayer, redPlayer, sampleBoard, redPlayer, new ArrayList<>());
    }

    @Test
    public void constructInvalidTurn() {
        thrown.expect(IllegalArgumentException.class);
        Game testGame = new Game(sampleName, redPlayer, whitePlayer, sampleBoard, new Player("test", RED), new ArrayList<>());
    }

    @Test
    public void constructInvalidBoardSize() {
        thrown.expect(IllegalArgumentException.class);
        Game testGame = new Game(sampleName, redPlayer, whitePlayer, new ArrayList<>(10), redPlayer, new ArrayList<>());
    }

    @Test
    public void constructTooManyWhiteDisks() {
        List<Disk> testBoard = new ArrayList<>(sampleBoard);
        testBoard.replaceAll(d -> WHITE_DISK);
        thrown.expect(IllegalArgumentException.class);
        Game testGame = new Game(sampleName, redPlayer, whitePlayer, testBoard, redPlayer, new ArrayList<>());
    }

    @Test
    public void constructTooManyRedDisks() {
        List<Disk> testBoard = new ArrayList<>(sampleBoard);
        testBoard.replaceAll(d -> RED_DISK);
        thrown.expect(IllegalArgumentException.class);
        Game testGame = new Game(sampleName, redPlayer, whitePlayer, testBoard, redPlayer, new ArrayList<>());
    }

    @Test
    public void equalsTest() {
        Game first = new Game("blah", "blargh");
        Game third = new Game("blah", "blargh");
        Game second = new Game("bler", "blee");

        // check identities
        assertEquals(first, third);
        assertNotEquals(second, first);
        assertNotEquals(second, third);

        // Now that identity is correct, check hashcode function
        // this must be deterministic, we repeat it
        assertEquals(first.hashCode(), third.hashCode());
        assertEquals(first.hashCode(), third.hashCode());

        assertNotEquals(first.hashCode(), second.hashCode());
        assertNotEquals(first.hashCode(), second.hashCode());

        assertNotEquals(third.hashCode(), second.hashCode());
        assertNotEquals(third.hashCode(), second.hashCode());

    }
}