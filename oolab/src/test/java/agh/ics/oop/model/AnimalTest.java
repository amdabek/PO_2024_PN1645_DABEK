package agh.ics.oop.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class AnimalTest {

    class AlwaysTrueWorldMap implements WorldMap {
        @Override
        public boolean canMoveTo(Vector2d position) {
            return true;
        }

        @Override
        public boolean place(Animal animal) {
            return true;
        }

        @Override
        public void move(Animal animal, MoveDirection direction) {
        }

        @Override
        public boolean isOccupied(Vector2d position) {
            return false;
        }

        @Override
        public WorldElement objectAt(Vector2d position) {
            return null;
        }

        @Override
        public List<WorldElement> getElements() {
            return null;
        }
    }

    class BoundedWorldMap implements WorldMap {
        private final Vector2d lowerLeft;
        private final Vector2d upperRight;

        public BoundedWorldMap(Vector2d lowerLeft, Vector2d upperRight) {
            this.lowerLeft = lowerLeft;
            this.upperRight = upperRight;
        }

        @Override
        public boolean canMoveTo(Vector2d position) {
            return position.follows(lowerLeft) && position.precedes(upperRight);
        }

        @Override
        public boolean place(Animal animal) {
            return canMoveTo(animal.getPosition());
        }

        @Override
        public void move(Animal animal, MoveDirection direction) {
            // Do nothing
        }

        @Override
        public boolean isOccupied(Vector2d position) {
            return false;
        }

        @Override
        public WorldElement objectAt(Vector2d position) {
            return null;
        }

        @Override
        public List<WorldElement> getElements() {
            return null;
        }
    }

    @Test
    public void testAnimalMovement() {
        Animal animal = new Animal(MapDirection.NORTH, new Vector2d(2, 2));
        WorldMap map = new AlwaysTrueWorldMap();

        animal.move(MoveDirection.FORWARD, map);
        assertEquals(MapDirection.NORTH, animal.getDirection());
        assertEquals(new Vector2d(2, 3), animal.getPosition());

        animal.move(MoveDirection.RIGHT, map);
        assertEquals(MapDirection.EAST, animal.getDirection());
        assertEquals(new Vector2d(2, 3), animal.getPosition());

        animal.move(MoveDirection.BACKWARD, map);
        assertEquals(MapDirection.EAST, animal.getDirection());
        assertEquals(new Vector2d(1, 3), animal.getPosition());
    }

    @Test
    public void testAnimalEdgeCases() {
        Animal animal = new Animal(MapDirection.NORTH, new Vector2d(0, 0));
        WorldMap map = new BoundedWorldMap(new Vector2d(0, 0), new Vector2d(4, 4));

        animal.move(MoveDirection.LEFT, map);
        assertEquals(MapDirection.WEST, animal.getDirection());
        assertEquals(new Vector2d(0, 0), animal.getPosition());

        animal.move(MoveDirection.BACKWARD, map);
        assertEquals(new Vector2d(1, 0), animal.getPosition());

        animal.move(MoveDirection.RIGHT, map);
        assertEquals(MapDirection.NORTH, animal.getDirection());

        animal.move(MoveDirection.FORWARD, map);
        assertEquals(new Vector2d(1, 1), animal.getPosition());
    }

    @Test
    public void testAnimalBoundaries() {
        Animal animal = new Animal(MapDirection.NORTH, new Vector2d(0, 0));
        WorldMap map = new BoundedWorldMap(new Vector2d(0, 0), new Vector2d(4, 4));

        animal.move(MoveDirection.LEFT, map);

        animal.move(MoveDirection.FORWARD, map);
        assertEquals(new Vector2d(0, 0), animal.getPosition());

        animal.move(MoveDirection.LEFT, map);
        animal.move(MoveDirection.FORWARD, map);
        assertEquals(new Vector2d(0, 0), animal.getPosition());

        animal.move(MoveDirection.LEFT, map);
        for (int i = 0; i < 7; i++) {
            animal.move(MoveDirection.FORWARD, map);
        }
        assertEquals(new Vector2d(4, 0), animal.getPosition());

        animal.move(MoveDirection.LEFT, map);
        for (int i = 0; i < 9; i++) {
            animal.move(MoveDirection.FORWARD, map);
        }
        assertEquals(new Vector2d(4, 4), animal.getPosition());
    }

    @Test
    public void testAnimalIsAt() {
        Animal animal = new Animal(MapDirection.NORTH, new Vector2d(2, 2));
        WorldMap map = new AlwaysTrueWorldMap();

        assertTrue(animal.isAt(new Vector2d(2, 2)));

        animal.move(MoveDirection.FORWARD, map);
        assertFalse(animal.isAt(new Vector2d(2, 2)));
        assertTrue(animal.isAt(new Vector2d(2, 3)));
    }

    @Test
    public void testAnimalDefaultConstructor() {
        Animal animal = new Animal();

        assertEquals(new Vector2d(2, 2), animal.getPosition());
        assertEquals(MapDirection.NORTH, animal.getDirection());

        assertTrue(animal.isAt(new Vector2d(2, 2)));
    }

    @Test
    public void testAnimalToString() {
        Animal animal = new Animal(MapDirection.NORTH, new Vector2d(2, 2));
        assertEquals("N", animal.toString());

        animal = new Animal(MapDirection.EAST, new Vector2d(2, 2));
        assertEquals("E", animal.toString());

        animal = new Animal(MapDirection.SOUTH, new Vector2d(2, 2));
        assertEquals("S", animal.toString());

        animal = new Animal(MapDirection.WEST, new Vector2d(2, 2));
        assertEquals("W", animal.toString());
    }

    @Test
    public void testAnimalMoveDenByValidator() {
        Animal animal = new Animal(MapDirection.NORTH, new Vector2d(0, 0));
        WorldMap map = new BoundedWorldMap(new Vector2d(1, 1), new Vector2d(4, 4));

        animal.move(MoveDirection.FORWARD, map);
        assertEquals(new Vector2d(0, 0), animal.getPosition());
        assertEquals(MapDirection.NORTH, animal.getDirection());
    }

    @Test
    public void testAnimalRotationWithDenMove() {
        Animal animal = new Animal(MapDirection.NORTH, new Vector2d(0, 0));
        WorldMap map = new BoundedWorldMap(new Vector2d(1, 1), new Vector2d(4, 4));

        animal.move(MoveDirection.RIGHT, map);
        assertEquals(MapDirection.EAST, animal.getDirection());
        assertEquals(new Vector2d(0, 0), animal.getPosition());

        animal.move(MoveDirection.LEFT, map);
        assertEquals(MapDirection.NORTH, animal.getDirection());
        assertEquals(new Vector2d(0, 0), animal.getPosition());
    }
}
