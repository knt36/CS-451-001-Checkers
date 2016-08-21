package game;

import org.junit.Test;

import static game.Color.*;
import static game.Disk.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class DiskTest {
    @Test
    public void EmptyTest() {
        Disk disk = EMPTY;
        assertEquals(EMPTY, disk.inverse());
        assertEquals(true, disk.empty());
        assertEquals(false, disk.canMoveUp());
        assertEquals(false, disk.canMoveDown());
        assertEquals(NONE, disk.getColor());
        assertEquals(false, disk.red());
        assertEquals(false, disk.white());
        assertEquals(false, disk.king());
        assertEquals("__", disk.toString());
    }

    @Test
    public void RedManTest() {
        Disk disk = RED_DISK;
        assertEquals(WHITE_DISK, disk.inverse());
        assertEquals(false, disk.empty());
        assertEquals(true, disk.canMoveUp());
        assertEquals(false, disk.canMoveDown());
        assertEquals(RED, disk.getColor());
        assertEquals(true, disk.red());
        assertEquals(false, disk.white());
        assertEquals(false, disk.king());
        assertEquals("r_", disk.toString());
    }

    @Test
    public void RedKingTest() {
        Disk disk = RED_KING;
        assertEquals(RED_KING, Disk.fromString("rk"));
        assertEquals(WHITE_DISK, disk.inverse());
        assertEquals(false, disk.empty());
        assertEquals(true, disk.canMoveUp());
        assertEquals(true, disk.canMoveDown());
        assertEquals(RED, disk.getColor());
        assertEquals(true, disk.red());
        assertEquals(false, disk.white());
        assertEquals(true, disk.king());
        assertEquals("rk", disk.toString());
    }

    @Test
    public void WhiteManTest() {
        Disk disk = WHITE_DISK;
        assertEquals(RED_DISK, disk.inverse());
        assertEquals(false, disk.empty());
        assertEquals(false, disk.canMoveUp());
        assertEquals(true, disk.canMoveDown());
        assertEquals(WHITE, disk.getColor());
        assertEquals(false, disk.red());
        assertEquals(true, disk.white());
        assertEquals(false, disk.king());
        assertEquals("w_", disk.toString());
    }

    @Test
    public void WhiteKingTest() {
        Disk disk = WHITE_KING;
        assertEquals(RED_DISK, disk.inverse());
        assertEquals(false, disk.empty());
        assertEquals(true, disk.canMoveUp());
        assertEquals(true, disk.canMoveDown());
        assertEquals(WHITE, disk.getColor());
        assertEquals(false, disk.red());
        assertEquals(true, disk.white());
        assertEquals(true, disk.king());
        assertEquals("wk", disk.toString());
        assertEquals(WHITE_KING, Disk.fromString("wk"));
    }

    @Test
    public void EqualsTest() {
        Disk first = WHITE_KING;
        Disk third = WHITE_KING;
        Disk second = WHITE_DISK;

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