package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RectangularMapTest {

    @Test
    public void testPlaceAnimal() {
        RectangularMap map = new RectangularMap(4, 4);
        Animal animal1 = new Animal(MapDirection.NORTH, new Vector2d(2, 3));
        Animal animal2 = new Animal(MapDirection.EAST, new Vector2d(3, 4));

        assertTrue(map.place(animal1));
        assertFalse(map.place(animal2));
    }

    @Test
    public void testCanMoveTo() {
        RectangularMap map = new RectangularMap(5, 5);
        Animal animal1 = new Animal(MapDirection.NORTH, new Vector2d(2, 3));
        map.place(animal1);

        assertTrue(map.canMoveTo(new Vector2d(2, 4)));
        assertTrue(map.canMoveTo(new Vector2d(1, 3)));

        Animal animal2 = new Animal(MapDirection.EAST, new Vector2d(3, 4));
        map.place(animal2);

        assertFalse(map.canMoveTo(new Vector2d(3, 4)));
    }

    @Test
    public void testIsOccupied() {
        RectangularMap map = new RectangularMap(5, 5);
        Animal animal = new Animal(MapDirection.NORTH, new Vector2d(2, 3));
        map.place(animal);

        assertTrue(map.isOccupied(new Vector2d(2, 3)));
        assertFalse(map.isOccupied(new Vector2d(0, 4)));
    }

    @Test
    public void testObjectAt() {
        RectangularMap map = new RectangularMap(5, 5);
        Animal animal1 = new Animal(MapDirection.NORTH, new Vector2d(2, 3));
        Animal animal2 = new Animal(MapDirection.EAST, new Vector2d(3, 4));
        map.place(animal1);
        map.place(animal2);

        assertEquals(animal1, map.objectAt(new Vector2d(2, 3)));
        assertEquals(animal2, map.objectAt(new Vector2d(3, 4)));
    }

    @Test
    public void testMoveAnimal() {
        RectangularMap map = new RectangularMap(5, 5);
        Animal animal1 = new Animal(MapDirection.NORTH, new Vector2d(2, 3));
        Animal animal2 = new Animal(MapDirection.EAST, new Vector2d(3, 4));
        map.place(animal1);
        map.place(animal2);

        map.move(animal1, MoveDirection.FORWARD);
        map.move(animal2, MoveDirection.FORWARD);

        assertEquals(new Vector2d(2, 4), animal1.getCoordinates());
        assertEquals(new Vector2d(4, 4), animal2.getCoordinates());
    }

    @Test
    public void testMapBoundaries() {
        RectangularMap map = new RectangularMap(5, 5);
        Animal animal = new Animal(MapDirection.NORTH, new Vector2d(2, 4));
        map.place(animal);

        map.move(animal, MoveDirection.FORWARD);

        assertEquals(new Vector2d(2, 4), animal.getCoordinates());
        assertTrue(map.isOccupied(new Vector2d(2, 4)));
    }

    @Test
    public void testToString() {
        String expectedMap =
                " y\\x  0 1 2 3 4\r\n" +
                        "  5: -----------\r\n" +
                        "  4: | | | | | |\r\n" +
                        "  3: | | |N| | |\r\n" +
                        "  2: | | | | | |\r\n" +
                        "  1: | | | | | |\r\n" +
                        "  0: | | | | | |\r\n" +
                        " -1: -----------\r\n";
        RectangularMap map = new RectangularMap(5, 5);
        Animal animal = new Animal(MapDirection.NORTH, new Vector2d(2, 3));
        map.place(animal);

        assertEquals(expectedMap, map.toString());
    }

    @Test
    public void testEmptyMap() {
        RectangularMap map = new RectangularMap(5, 5);


        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                Vector2d position = new Vector2d(x, y);
                assertFalse(map.isOccupied(position), "Pozycja " + position + " nie powinna być zajęta");
                assertNull(map.objectAt(position), "Na pozycji " + position + " nie powinno być obiektu");
            }
        }
        
        assertTrue(map.canMoveTo(new Vector2d(2, 2)), "Powinno być możliwe poruszenie się na pozycję (2,2)");
        assertFalse(map.canMoveTo(new Vector2d(-1, 0)), "Nie powinno być możliwe poruszenie się na pozycję (-1,0)");
        assertFalse(map.canMoveTo(new Vector2d(0, -1)), "Nie powinno być możliwe poruszenie się na pozycję (0,-1)");
        assertFalse(map.canMoveTo(new Vector2d(5, 0)), "Nie powinno być możliwe poruszenie się na pozycję (5,0)");
        assertFalse(map.canMoveTo(new Vector2d(0, 5)), "Nie powinno być możliwe poruszenie się na pozycję (0,5)");
    }

}
