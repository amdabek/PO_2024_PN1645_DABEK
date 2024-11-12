package agh.ics.oop.model;

import agh.ics.oop.model.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AnimalTest {

    class AlwaysTrueMoveValidator implements MoveValidator {
        @Override
        public boolean canMoveTo(Vector2d position) {
            return true;
        }
    }

    class BoundedMoveValidator implements MoveValidator {
        private final Vector2d lowerLeft;
        private final Vector2d upperRight;

        public BoundedMoveValidator(Vector2d lowerLeft, Vector2d upperRight) {
            this.lowerLeft = lowerLeft;
            this.upperRight = upperRight;
        }

        @Override
        public boolean canMoveTo(Vector2d position) {
            return position.follows(lowerLeft) && position.precedes(upperRight);
        }
    }

    @Test
    public void testAnimalMovement() {
        Animal animal = new Animal(MapDirection.NORTH, new Vector2d(2, 2));
        MoveValidator validator = new AlwaysTrueMoveValidator();

        animal.move(MoveDirection.FORWARD, validator);
        assertEquals(MapDirection.NORTH, animal.getDirection());
        assertEquals(new Vector2d(2, 3), animal.getCoordinates());

        animal.move(MoveDirection.RIGHT, validator);
        assertEquals(MapDirection.EAST, animal.getDirection());
        assertEquals(new Vector2d(2, 3), animal.getCoordinates());

        animal.move(MoveDirection.BACKWARD, validator);
        assertEquals(MapDirection.EAST, animal.getDirection());
        assertEquals(new Vector2d(1, 3), animal.getCoordinates());
    }

    @Test
    public void testAnimalEdgeCases() {
        Animal animal = new Animal(MapDirection.NORTH, new Vector2d(0, 0));
        MoveValidator validator = new BoundedMoveValidator(new Vector2d(0, 0), new Vector2d(4, 4));

        animal.move(MoveDirection.LEFT, validator);
        assertEquals(MapDirection.WEST, animal.getDirection());
        assertEquals(new Vector2d(0, 0), animal.getCoordinates());

        animal.move(MoveDirection.BACKWARD, validator);
        assertEquals(new Vector2d(1, 0), animal.getCoordinates());

        animal.move(MoveDirection.RIGHT, validator);
        assertEquals(MapDirection.NORTH, animal.getDirection());

        animal.move(MoveDirection.FORWARD, validator);
        assertEquals(new Vector2d(1, 1), animal.getCoordinates());
    }

    @Test
    public void testAnimalBoundaries() {
        Animal animal = new Animal(MapDirection.NORTH, new Vector2d(0, 0));
        MoveValidator validator = new BoundedMoveValidator(new Vector2d(0, 0), new Vector2d(4, 4));

        animal.move(MoveDirection.LEFT, validator);

        animal.move(MoveDirection.FORWARD, validator);
        assertEquals(new Vector2d(0, 0), animal.getCoordinates());

        animal.move(MoveDirection.LEFT, validator);
        animal.move(MoveDirection.FORWARD, validator);
        assertEquals(new Vector2d(0, 0), animal.getCoordinates());

        animal.move(MoveDirection.LEFT, validator);
        for (int i = 0; i < 7; i++) {
            animal.move(MoveDirection.FORWARD, validator);
        }
        assertEquals(new Vector2d(4, 0), animal.getCoordinates());

        animal.move(MoveDirection.LEFT, validator);
        for (int i = 0; i < 9; i++) {
            animal.move(MoveDirection.FORWARD, validator);
        }
        assertEquals(new Vector2d(4, 4), animal.getCoordinates());
    }

    @Test
    public void testAnimalIsAt() {
        Animal animal = new Animal(MapDirection.NORTH, new Vector2d(2, 2));
        MoveValidator validator = new AlwaysTrueMoveValidator();

        assertTrue(animal.isAt(new Vector2d(2, 2)));

        animal.move(MoveDirection.FORWARD, validator);
        assertFalse(animal.isAt(new Vector2d(2, 2)));
        assertTrue(animal.isAt(new Vector2d(2, 3)));
    }

    @Test
    public void testAnimalDefaultConstructor() {
        Animal animal = new Animal();

        assertEquals(new Vector2d(2, 2), animal.getCoordinates());
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
    public void testAnimalMoveDeniedByValidator() {
        Animal animal = new Animal(MapDirection.NORTH, new Vector2d(0, 0));
        MoveValidator validator = position -> false;

        animal.move(MoveDirection.FORWARD, validator);
        assertEquals(new Vector2d(0, 0), animal.getCoordinates());
        assertEquals(MapDirection.NORTH, animal.getDirection());
    }

    @Test
    public void testAnimalRotationWithDeniedMovement() {
        Animal animal = new Animal(MapDirection.NORTH, new Vector2d(0, 0));
        MoveValidator validator = position -> false;

        animal.move(MoveDirection.RIGHT, validator);
        assertEquals(MapDirection.EAST, animal.getDirection());
        assertEquals(new Vector2d(0, 0), animal.getCoordinates());

        animal.move(MoveDirection.LEFT, validator);
        assertEquals(MapDirection.NORTH, animal.getDirection());
        assertEquals(new Vector2d(0, 0), animal.getCoordinates());
    }
}



