package agh.ics.oop.model;

import agh.ics.oop.model.util.Boundary;
import agh.ics.oop.model.util.IncorrectPositionException;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class GrassFieldTest {

    @Test
    public void testIfMapWorks() {
        WorldMap map = new GrassField(10);
        Animal animal = new Animal();
        try {
            map.place(animal);
        } catch (IncorrectPositionException e) {
            fail("Unexpected exception: " + e.getMessage());
        }
        map.move(animal, MoveDirection.FORWARD);
        assertEquals(new Vector2d(2, 3), animal.getPosition());
    }

    @Test
    public void testCanMoveTo1() {
        WorldMap map = new GrassField(10);
        Animal animal1 = new Animal();
        try {
            map.place(animal1);
        } catch (IncorrectPositionException e) {
            fail("Unexpected exception: " + e.getMessage());
        }
        assertTrue(map.canMoveTo(new Vector2d(2, 3))); // Empty position
        assertFalse(map.canMoveTo(new Vector2d(2, 2))); // Occupied position
    }

    @Test
    public void testCanMoveTo2() {
        WorldMap map = new GrassField(10);
        Animal animal1 = new Animal();
        Animal animal2 = new Animal(MapDirection.SOUTH, new Vector2d(2, 3));
        try {
            map.place(animal1);
            map.place(animal2);
        } catch (IncorrectPositionException e) {
            fail("Unexpected exception: " + e.getMessage());
        }
        assertFalse(map.canMoveTo(new Vector2d(2, 3))); // Occupied by animal2
        assertTrue(map.canMoveTo(new Vector2d(1, 1))); // Empty position
    }

    @Test
    public void testPlace() {
        WorldMap map = new GrassField(10);
        Animal animal1 = new Animal();
        Animal animal2 = new Animal(MapDirection.SOUTH, new Vector2d(2, 2));

        try {
            map.place(animal1); // Should succeed
        } catch (IncorrectPositionException e) {
            fail("Unexpected exception: " + e.getMessage());
        }

        // Placing another animal at the same position should throw an exception
        assertThrows(IncorrectPositionException.class, () -> map.place(animal2));
    }

    @Test
    public void testMove() {
        WorldMap map = new GrassField(10);
        Animal animal1 = new Animal();
        Animal animal2 = new Animal(MapDirection.SOUTH, new Vector2d(3, 2));
        try {
            map.place(animal1);
            map.place(animal2);
        } catch (IncorrectPositionException e) {
            fail("Unexpected exception: " + e.getMessage());
        }
        map.move(animal1, MoveDirection.FORWARD); // Moves to (2,3)
        map.move(animal2, MoveDirection.FORWARD); // Moves to (3,1)
        assertEquals(new Vector2d(2, 3), animal1.getPosition());
        assertEquals(new Vector2d(3, 1), animal2.getPosition());
    }

    @Test
    public void testIsOccupied() {
        WorldMap map = new GrassField(10);
        Animal animal1 = new Animal();
        try {
            map.place(animal1);
        } catch (IncorrectPositionException e) {
            fail("Unexpected exception: " + e.getMessage());
        }

        assertTrue(map.isOccupied(new Vector2d(2, 2))); // Occupied by animal1
        assertFalse(map.isOccupied(new Vector2d(3, 3))); // Empty position
    }

    @Test
    public void testObjectAt() {
        WorldMap map = new GrassField(10);
        Animal animal1 = new Animal();
        try {
            map.place(animal1);
        } catch (IncorrectPositionException e) {
            fail("Unexpected exception: " + e.getMessage());
        }
        assertEquals(animal1, map.objectAt(new Vector2d(2, 2))); // Animal at (2,2)
        assertNull(map.objectAt(new Vector2d(3, 3))); // Nothing at (3,3)
    }

    @Test
    public void testGetElements() {
        WorldMap map = new GrassField(10);
        Animal animal1 = new Animal();
        Animal animal2 = new Animal(MapDirection.SOUTH, new Vector2d(2, 3));
        try {
            map.place(animal1);
            map.place(animal2);
        } catch (IncorrectPositionException e) {
            fail("Unexpected exception: " + e.getMessage());
        }

        List<WorldElement> elements = map.getElements();
        assertEquals(12, elements.size()); // 10 grasses + 2 animals
    }

    @Test
    public void testAddGrass() {
        GrassField map = new GrassField(0);
        Grass grass = new Grass(new Vector2d(5, 5));
        map.addGrass(grass);

        assertTrue(map.isOccupied(new Vector2d(5, 5))); // Grass is added
        assertEquals(grass, map.objectAt(new Vector2d(5, 5))); // Grass object is present
    }

    @Test
    public void testNoGrassOverlap() {
        int grassCount = 10;
        GrassField grassField = new GrassField(grassCount);

        Set<Vector2d> grassPositions = new HashSet<>();
        for (Grass grass : grassField.getGrasses()) {
            assertTrue(grassPositions.add(grass.getPosition()));
        }

        assertEquals(grassCount, grassPositions.size());
    }

    @Test
    public void testVisualization() {
        GrassField grassField = new GrassField(3);
        Animal animal = new Animal(MapDirection.NORTH, new Vector2d(0, 0));
        try {
            grassField.place(animal);
        } catch (IncorrectPositionException e) {
            fail("Unexpected exception: " + e.getMessage());
        }

        String mapString = grassField.toString();

        assertTrue(mapString.contains("N"));
        assertTrue(mapString.contains("*"));
    }

    @Test
    public void testCurrentBounds() {
        GrassField grassField = new GrassField(0);
        Animal animal = new Animal(MapDirection.NORTH, new Vector2d(0, 0));
        try {
            grassField.place(animal);
        } catch (IncorrectPositionException e) {
            fail("Unexpected exception: " + e.getMessage());
        }

        Boundary bounds = grassField.getCurrentBounds();
        assertEquals(new Vector2d(0, 0), bounds.lowerLeft());
        assertEquals(new Vector2d(0, 0), bounds.upperRight());
    }
}
