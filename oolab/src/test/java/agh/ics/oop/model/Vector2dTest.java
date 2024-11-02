package agh.ics.oop.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Vector2dTest {
    Vector2d v = new Vector2d(2, 3);
    Vector2d u = new Vector2d(1, 2);
    Vector2d w = new Vector2d(3, 3);

    @Test
    void testToString() {
        assertEquals("(2,3)", v.toString());
    }

    @Test
    void precedes() {
        assertTrue(v.precedes(w));
        assertFalse(v.precedes(u));
    }

    @Test
    void follows() {
        assertTrue(w.follows(v));
        assertFalse(u.follows(v));
    }

    @Test
    void upperRight() {
        assertEquals(new Vector2d(2, 3), v.upperRight(u));
        assertEquals(new Vector2d(3, 3), v.upperRight(w));
    }

    @Test
    void lowerLeft() {
        assertEquals(new Vector2d(1, 2), v.lowerLeft(u));
        assertEquals(new Vector2d(2, 3), v.lowerLeft(w));
    }

    @Test
    void add() {
        assertEquals(new Vector2d(3, 5), v.add(u));
    }

    @Test
    void subtract() {
        assertEquals(new Vector2d(1, 1), v.subtract(u));
    }

    @Test
    void testEquals() {
        assertTrue(v.equals(new Vector2d(2, 3)));
        assertFalse(v.equals(w));
    }

    @Test
    void opposite() {
        assertEquals(new Vector2d(-2, -3), v.opposite());
    }
}
