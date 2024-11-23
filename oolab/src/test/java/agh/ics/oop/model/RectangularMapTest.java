package agh.ics.oop.model;

import agh.ics.oop.model.util.IncorrectPositionException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RectangularMapTest {

    @Test
    public void placeAnimal() {
        RectangularMap map = new RectangularMap(4, 4);
        Animal animal1 = new Animal(MapDirection.NORTH, new Vector2d(2, 3));
        Animal animal2 = new Animal(MapDirection.EAST, new Vector2d(3, 4)); // Poza mapą

        try {
            map.place(animal1);
        } catch (IncorrectPositionException e) {
            fail("First animal should be placed successfully.");
        }

        assertThrows(IncorrectPositionException.class, () -> map.place(animal2));
    }

    @Test
    public void canMoveToTest() {
        RectangularMap map = new RectangularMap(5, 5);
        Animal animal1 = new Animal(MapDirection.NORTH, new Vector2d(2, 3));

        try {
            map.place(animal1);
        } catch (IncorrectPositionException e) {
            fail("Animal should be placed successfully at (2,3).");
        }

        assertFalse(map.canMoveTo(new Vector2d(2, 3)));
        assertTrue(map.canMoveTo(new Vector2d(2, 4)));
        assertTrue(map.canMoveTo(new Vector2d(1, 3)));

        Animal animal2 = new Animal(MapDirection.EAST, new Vector2d(3, 4));

        try {
            map.place(animal2);
        } catch (IncorrectPositionException e) {
            fail("Animal should be placed successfully at (3,4).");
        }

        assertFalse(map.canMoveTo(new Vector2d(3, 4)));

        assertFalse(map.canMoveTo(new Vector2d(-1, 0))); // Poza mapą
        assertFalse(map.canMoveTo(new Vector2d(0, -1))); // Poza mapą
        assertFalse(map.canMoveTo(new Vector2d(5, 0)));  // Poza mapą
        assertFalse(map.canMoveTo(new Vector2d(0, 5)));  // Poza mapą
    }

    @Test
    public void isOccupiedTest() {
        RectangularMap map = new RectangularMap(5, 5);
        Animal animal = new Animal(MapDirection.NORTH, new Vector2d(2, 3));
        try {
            map.place(animal);
        } catch (IncorrectPositionException e) {
            fail("Animal should be placed successfully.");
        }

        assertTrue(map.isOccupied(new Vector2d(2, 3)));
        assertFalse(map.isOccupied(new Vector2d(0, 4)));
    }

    @Test
    public void objectAtTest() {
        RectangularMap map = new RectangularMap(5, 5);
        Animal animal1 = new Animal(MapDirection.NORTH, new Vector2d(2, 3));
        Animal animal2 = new Animal(MapDirection.EAST, new Vector2d(3, 4));
        try {
            map.place(animal1);
            map.place(animal2);
        } catch (IncorrectPositionException e) {
            fail("Animals should be placed successfully.");
        }

        assertEquals(animal1, map.objectAt(new Vector2d(2, 3)));
        assertEquals(animal2, map.objectAt(new Vector2d(3, 4)));
    }

    @Test
    public void moveAnimalTest() {
        RectangularMap map = new RectangularMap(5, 5);
        Animal animal1 = new Animal(MapDirection.NORTH, new Vector2d(2, 3));
        Animal animal2 = new Animal(MapDirection.EAST, new Vector2d(3, 3));
        try {
            map.place(animal1);
            map.place(animal2);
        } catch (IncorrectPositionException e) {
            fail("Animals should be placed successfully.");
        }

        map.move(animal1, MoveDirection.FORWARD);
        map.move(animal2, MoveDirection.FORWARD);

        assertEquals(new Vector2d(2, 4), animal1.getPosition());
        assertEquals(new Vector2d(4, 3), animal2.getPosition());
    }

    @Test
    public void mapBoundariesTest() {
        RectangularMap map = new RectangularMap(5, 5);
        Animal animal = new Animal(MapDirection.NORTH, new Vector2d(2, 4));
        try {
            map.place(animal);
        } catch (IncorrectPositionException e) {
            fail("Animal should be placed successfully.");
        }

        map.move(animal, MoveDirection.FORWARD);

        assertEquals(new Vector2d(2, 4), animal.getPosition());
        assertTrue(map.isOccupied(new Vector2d(2, 4)));
    }

    @Test
    public void toStringTest() {
        String ls = System.lineSeparator();
        String expectedMap =
                " y\\x  0 1 2 3 4" + ls +
                        "  5: -----------" + ls +
                        "  4: | | | | | |" + ls +
                        "  3: | | |N| | |" + ls +
                        "  2: | | | | | |" + ls +
                        "  1: | | | | | |" + ls +
                        "  0: | | | | | |" + ls +
                        " -1: -----------" + ls;
        RectangularMap map = new RectangularMap(5, 5);
        Animal animal = new Animal(MapDirection.NORTH, new Vector2d(2, 3));
        try {
            map.place(animal);
        } catch (IncorrectPositionException e) {
            fail("Animal should be placed successfully.");
        }

        assertEquals(expectedMap, map.toString());
    }


    @Test
    public void emptyMapTest() {
        RectangularMap map = new RectangularMap(5, 5);

        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                Vector2d position = new Vector2d(x, y);
                assertFalse(map.isOccupied(position));
                assertNull(map.objectAt(position));
            }
        }

        assertTrue(map.canMoveTo(new Vector2d(2, 2)));
        assertFalse(map.canMoveTo(new Vector2d(-1, 0)));
        assertFalse(map.canMoveTo(new Vector2d(0, -1)));
        assertFalse(map.canMoveTo(new Vector2d(5, 0)));
        assertFalse(map.canMoveTo(new Vector2d(0, 5)));
    }
}
