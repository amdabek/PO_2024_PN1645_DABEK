package agh.ics.oop.model;

import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

public class GrassFieldTest {

    @Test
    public void initZeroGrass() {
        GrassField grassField = new GrassField(0);
        List<WorldElement> elements = grassField.getElements();
        assertTrue(elements.isEmpty());
    }

    @Test
    public void initNegativeGrass() {
        GrassField grassField = new GrassField(-5);
        List<WorldElement> elements = grassField.getElements();
        assertTrue(elements.isEmpty());
    }

    @Test
    public void initLargeGrass() {
        int grassCount = 1000;
        GrassField grassField = new GrassField(grassCount);

        int actualGrassCount = 0;
        for (WorldElement element : grassField.getElements()) {
            if (element instanceof Grass) {
                actualGrassCount++;
            }
        }

        assertEquals(grassCount, actualGrassCount);
    }

    @Test
    public void objectAtGrassOnly() {
        GrassField grassField = new GrassField(10);

        for (Grass grass : grassField.getGrasses()) {
            WorldElement element = grassField.objectAt(grass.getPosition());
            assertTrue(element instanceof Grass);
            assertEquals(grass, element);
        }
    }

    @Test
    public void objectAtAnimalOnly() {
        GrassField grassField = new GrassField(0);
        Animal animal = new Animal(MapDirection.NORTH, new Vector2d(2, 2));
        grassField.place(animal);

        WorldElement element = grassField.objectAt(new Vector2d(2, 2));
        assertTrue(element instanceof Animal);
        assertEquals(animal, element);
    }

    @Test
    public void objectAtGrassAndAnimal() {
        GrassField grassField = new GrassField(1);
        Grass grass = grassField.getGrasses().iterator().next();
        Animal animal = new Animal(MapDirection.NORTH, grass.getPosition());
        grassField.place(animal);

        WorldElement element = grassField.objectAt(grass.getPosition());
        assertTrue(element instanceof Animal);
        assertEquals(animal, element);
    }

    @Test
    public void objectAtEmpty() {
        GrassField grassField = new GrassField(0);
        WorldElement element = grassField.objectAt(new Vector2d(10, 10));
        assertNull(element);
    }

    @Test
    public void getElementsTest() {
        GrassField grassField = new GrassField(5);
        Animal animal = new Animal(MapDirection.NORTH, new Vector2d(2, 2));
        grassField.place(animal);

        List<WorldElement> elements = grassField.getElements();

        int grassCount = 0;
        int animalCount = 0;
        for (WorldElement element : elements) {
            if (element instanceof Grass) {
                grassCount++;
            } else if (element instanceof Animal) {
                animalCount++;
            }
        }

        assertEquals(5, grassCount);
        assertEquals(1, animalCount);
    }

    @Test
    public void toStringTest() {
        GrassField grassField = new GrassField(5);
        Animal animal1 = new Animal(MapDirection.NORTH, new Vector2d(2, 2));
        Animal animal2 = new Animal(MapDirection.EAST, new Vector2d(-1, -1));
        grassField.place(animal1);
        grassField.place(animal2);

        String mapString = grassField.toString();

        assertNotNull(mapString);
        assertFalse(mapString.isEmpty());
        assertTrue(mapString.contains("N"));
        assertTrue(mapString.contains("E"));
        assertTrue(mapString.contains("*"));
    }

    @Test
    public void canMoveToTest() {
        GrassField grassField = new GrassField(0);
        Animal animal1 = new Animal(MapDirection.NORTH, new Vector2d(0, 0));
        grassField.place(animal1);

        assertFalse(grassField.canMoveTo(new Vector2d(0, 0)));
        assertTrue(grassField.canMoveTo(new Vector2d(1, 1)));
        grassField.addGrass(new Grass(new Vector2d(1, 1)));
        assertTrue(grassField.canMoveTo(new Vector2d(1, 1)));
    }

    @Test
    public void placeAnimalOnOccupied() {
        GrassField grassField = new GrassField(0);
        Animal animal1 = new Animal(MapDirection.NORTH, new Vector2d(0, 0));
        Animal animal2 = new Animal(MapDirection.EAST, new Vector2d(0, 0));

        assertTrue(grassField.place(animal1));
        assertFalse(grassField.place(animal2));
    }

    @Test
    public void boundariesAfterMove() {
        GrassField grassField = new GrassField(0);
        Animal animal = new Animal(MapDirection.NORTH, new Vector2d(0, 0));
        grassField.place(animal);

        for (int i = 0; i < 10; i++) {
            grassField.move(animal, MoveDirection.FORWARD);
        }

        String mapString = grassField.toString();
        assertTrue(mapString.contains("N"));
        assertTrue(mapString.contains(Integer.toString(animal.getPosition().y)));
    }

    @Test
    public void isOccupiedTest() {
        GrassField grassField = new GrassField(0);
        grassField.addGrass(new Grass(new Vector2d(1, 1)));
        Animal animal = new Animal(MapDirection.NORTH, new Vector2d(2, 2));
        grassField.place(animal);

        assertTrue(grassField.isOccupied(new Vector2d(1, 1)));
        assertTrue(grassField.isOccupied(new Vector2d(2, 2)));
        assertFalse(grassField.isOccupied(new Vector2d(3, 3)));
    }

    @Test
    public void moveOverGrass() {
        GrassField grassField = new GrassField(0);
        grassField.addGrass(new Grass(new Vector2d(1, 1)));
        Animal animal = new Animal(MapDirection.EAST, new Vector2d(0, 1));
        grassField.place(animal);

        grassField.move(animal, MoveDirection.FORWARD);

        assertEquals(new Vector2d(1, 1), animal.getPosition());
        assertTrue(grassField.isOccupied(new Vector2d(1, 1)));
        assertEquals(animal, grassField.objectAt(new Vector2d(1, 1)));
    }

    @Test
    public void visualizationTest() {
        GrassField grassField = new GrassField(3);
        Animal animal = new Animal(MapDirection.NORTH, new Vector2d(0, 0));
        grassField.place(animal);

        String mapString = grassField.toString();

        assertTrue(mapString.contains("N"));
        assertTrue(mapString.contains("*"));
    }

    @Test
    public void moveBeyondBoundaries() {
        GrassField grassField = new GrassField(0);
        Animal animal = new Animal(MapDirection.NORTH, new Vector2d(0, 0));
        grassField.place(animal);

        for (int i = 0; i < 100; i++) {
            grassField.move(animal, MoveDirection.FORWARD);
        }

        assertEquals(new Vector2d(0, 100), animal.getPosition());
    }

    @Test
    public void noGrassOverlap() {
        int grassCount = 10;
        GrassField grassField = new GrassField(grassCount);

        Set<Vector2d> grassPositions = new HashSet<>();
        for (Grass grass : grassField.getGrasses()) {
            assertTrue(grassPositions.add(grass.getPosition()));
        }

        assertEquals(grassCount, grassPositions.size());
    }

    @Test
    public void placeAnimalOnGrass() {
        GrassField grassField = new GrassField(5);
        Grass grass = grassField.getGrasses().iterator().next();
        Animal animal = new Animal(MapDirection.NORTH, grass.getPosition());

        assertTrue(grassField.place(animal));
        assertEquals(animal, grassField.objectAt(grass.getPosition()));
    }
}


