package game;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static game.Color.RED;
import static game.Color.WHITE;
import static game.Disk.*;
import static java.util.Arrays.asList;
import static org.junit.Assert.*;

/**
 *
 */
public class GameTest {
    private static String sampleName = "test";
    private static Player redPlayer = new Player("red player", RED);
    private static Player whitePlayer = new Player("white player", WHITE);
    private static List<Disk> sampleBoard = Game.newBoard(); // Just going to trust this function, maybe not the best idea
    private static Game sampleGame = new Game(sampleName, redPlayer, whitePlayer, sampleBoard, redPlayer);

    private static Game setupBoardAdj(Integer src, Integer dst, Disk disk) {
        Game testGame = new Game(sampleGame); // Copy the sample board
        testGame.board.replaceAll(d -> EMPTY); // Clear the board to prevent interference
        testGame.turn.setColor(disk.getColor());
        testGame.board.set(src, disk);
        return testGame;
    }

    private static Game setupBoardJmp(Integer src, Integer dst, Disk disk) {
        Game testGame = setupBoardAdj(src, dst, disk);
        try {
            testGame.board.set(Game.jumpedSquare(src, dst), Disk.inverse(disk));
        } catch (IndexOutOfBoundsException e) {
            // Do nothing since we're probably testing for failure and got undefined behavior. If we return a board
            // with no jumpable piece, it will return "false" for the move anyway.
        }
        return testGame;
    }

    private static void assertMoveAdj(Integer src, Integer dst) {
        if (src < dst) {
            assertTrue(setupBoardAdj(src, dst, RED_DISK).move(src, dst));
            assertFalse(setupBoardAdj(src, dst, WHITE_DISK).move(src, dst));
        } else {
            assertTrue(setupBoardAdj(src, dst, WHITE_DISK).move(src, dst));
            assertFalse(setupBoardAdj(src, dst, RED_DISK).move(src, dst));
        }
        assertTrue(setupBoardAdj(src, dst, RED_KING).move(src, dst));
        assertTrue(setupBoardAdj(src, dst, WHITE_KING).move(src, dst));
    }

    private static void assertMoveJmp(Integer src, Integer dst) {
        if (src < dst) {
            assertTrue(setupBoardJmp(src, dst, RED_DISK).move(src, dst));
            assertFalse(setupBoardJmp(src, dst, WHITE_DISK).move(src, dst));
        } else {
            assertTrue(setupBoardJmp(src, dst, WHITE_DISK).move(src, dst));
            assertFalse(setupBoardJmp(src, dst, RED_DISK).move(src, dst));
        }
        assertTrue(setupBoardJmp(src, dst, RED_KING).move(src, dst));
        assertTrue(setupBoardJmp(src, dst, WHITE_KING).move(src, dst));
    }

    private static void assertMoveFalse(Integer src, Integer dst) {
        assertFalse(setupBoardAdj(src, dst, RED_DISK).move(src, dst));
        assertFalse(setupBoardAdj(src, dst, WHITE_DISK).move(src, dst));
        assertFalse(setupBoardAdj(src, dst, RED_KING).move(src, dst));
        assertFalse(setupBoardAdj(src, dst, WHITE_KING).move(src, dst));
        assertFalse(setupBoardJmp(src, dst, RED_DISK).move(src, dst));
        assertFalse(setupBoardJmp(src, dst, WHITE_DISK).move(src, dst));
        assertFalse(setupBoardJmp(src, dst, RED_KING).move(src, dst));
        assertFalse(setupBoardJmp(src, dst, WHITE_KING).move(src, dst));
    }

    @Test
    public void isPublicGame() throws Exception {
        Game testPublic = new Game("test", "p1");
        assertTrue(testPublic.isPublicGame());
        Game testPrivate = new Game("test", "p1", "p2");
        assertFalse(testPrivate.isPublicGame());
    }

    @Test
    public void redPlayer() throws Exception {
        assertEquals(sampleGame.redPlayer(), redPlayer);
    }

    @Test
    public void move() throws Exception {
        List<Integer> adjMoves;
        List<Integer> jmpMoves;
        int totalMoves = 0;
        for (Integer src = 0; src < 32; src++) {
            adjMoves = new ArrayList<>();
            jmpMoves = new ArrayList<>();
            for (Integer dst = 0; dst < 32; dst++) {
                if (Game.legalAdj(src, dst)) {
                    // System.out.println("sing: " + src.toString() + " " + dst.toString());
                    assertMoveAdj(src, dst);
                    totalMoves++;
                    adjMoves.add(dst);
                } else if (Game.legalJmp(src, dst)) {
                    // System.out.println("jump: " + src.toString() + " " + dst.toString());
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
                    assertEquals(adjMoves, asList(4, 5));
                    assertEquals(jmpMoves, asList(9));
                    break;
                case 1:
                    assertEquals(adjMoves, asList(5, 6));
                    assertEquals(jmpMoves, asList(8, 10));
                    break;
                case 2:
                    assertEquals(adjMoves, asList(6, 7));
                    assertEquals(jmpMoves, asList(9, 11));
                    break;
                case 3:
                    assertEquals(adjMoves, asList(7));
                    assertEquals(jmpMoves, asList(10));
                    break;
                case 4:
                    assertEquals(adjMoves, asList(0, 8));
                    assertEquals(jmpMoves, asList(13));
                    break;
                case 5:
                    assertEquals(adjMoves, asList(0, 1, 8, 9));
                    assertEquals(jmpMoves, asList(12, 14));
                    break;
                case 6:
                    assertEquals(adjMoves, asList(1, 2, 9, 10));
                    assertEquals(jmpMoves, asList(13, 15));
                    break;
                case 7:
                    assertEquals(adjMoves, asList(2, 3, 10, 11));
                    assertEquals(jmpMoves, asList(14));
                    break;
                case 8:
                    assertEquals(adjMoves, asList(4, 5, 12, 13));
                    assertEquals(jmpMoves, asList(1, 17));
                    break;
                case 9:
                    assertEquals(adjMoves, asList(5, 6, 13, 14));
                    assertEquals(jmpMoves, asList(0, 2, 16, 18));
                    break;
                case 10:
                    assertEquals(adjMoves, asList(6, 7, 14, 15));
                    assertEquals(jmpMoves, asList(1, 3, 17, 19));
                    break;
                case 11:
                    assertEquals(adjMoves, asList(7, 15));
                    assertEquals(jmpMoves, asList(2, 18));
                    break;
                case 12:
                    assertEquals(adjMoves, asList(8, 16));
                    assertEquals(jmpMoves, asList(5, 21));
                    break;
                case 13:
                    assertEquals(adjMoves, asList(8, 9, 16, 17));
                    assertEquals(jmpMoves, asList(4, 6, 20, 22));
                    break;
                case 14:
                    assertEquals(adjMoves, asList(9, 10, 17, 18));
                    assertEquals(jmpMoves, asList(5, 7, 21, 23));
                    break;
                case 15:
                    assertEquals(adjMoves, asList(10, 11, 18, 19));
                    assertEquals(jmpMoves, asList(6, 22));
                    break;
                case 16:
                    assertEquals(adjMoves, asList(12, 13, 20, 21));
                    assertEquals(jmpMoves, asList(9, 25));
                    break;
                case 17:
                    assertEquals(adjMoves, asList(13, 14, 21, 22));
                    assertEquals(jmpMoves, asList(8, 10, 24, 26));
                    break;
                case 18:
                    assertEquals(adjMoves, asList(14, 15, 22, 23));
                    assertEquals(jmpMoves, asList(9, 11, 25, 27));
                    break;
                case 19:
                    assertEquals(adjMoves, asList(15, 23));
                    assertEquals(jmpMoves, asList(10, 26));
                    break;
                case 20:
                    assertEquals(adjMoves, asList(16, 24));
                    assertEquals(jmpMoves, asList(13, 29));
                    break;
                case 21:
                    assertEquals(adjMoves, asList(16, 17, 24, 25));
                    assertEquals(jmpMoves, asList(12, 14, 28, 30));
                    break;
                case 22:
                    assertEquals(adjMoves, asList(17, 18, 25, 26));
                    assertEquals(jmpMoves, asList(13, 15, 29, 31));
                    break;
                case 23:
                    assertEquals(adjMoves, asList(18, 19, 26, 27));
                    assertEquals(jmpMoves, asList(14, 30));
                    break;
                case 24:
                    assertEquals(adjMoves, asList(20, 21, 28, 29));
                    assertEquals(jmpMoves, asList(17));
                    break;
                case 25:
                    assertEquals(adjMoves, asList(21, 22, 29, 30));
                    assertEquals(jmpMoves, asList(16, 18));
                    break;
                case 26:
                    assertEquals(adjMoves, asList(22, 23, 30, 31));
                    assertEquals(jmpMoves, asList(17, 19));
                    break;
                case 27:
                    assertEquals(adjMoves, asList(23, 31));
                    assertEquals(jmpMoves, asList(18));
                    break;
                case 28:
                    assertEquals(adjMoves, asList(24));
                    assertEquals(jmpMoves, asList(21));
                    break;
                case 29:
                    assertEquals(adjMoves, asList(24, 25));
                    assertEquals(jmpMoves, asList(20, 22));
                    break;
                case 30:
                    assertEquals(adjMoves, asList(25, 26));
                    assertEquals(jmpMoves, asList(21, 23));
                    break;
                case 31:
                    assertEquals(adjMoves, asList(26, 27));
                    assertEquals(jmpMoves, asList(22));
                    break;
            }
        }
        assertEquals(totalMoves, 170);
    }

    @Test
    public void move1() throws Exception {

    }

    @Test
    public void getSquare() throws Exception {
        Integer coordinate = ThreadLocalRandom.current().nextInt(32);
        assertEquals(sampleBoard.get(coordinate), sampleGame.getSquare(coordinate));
    }

}