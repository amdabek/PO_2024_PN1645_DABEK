package agh.ics.oop;

import agh.ics.oop.Simulation;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.MapDirection;
import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.Vector2d;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SimulationTest {

    @Test
    public void testSimulationRun() {
        List<MoveDirection> directions = new ArrayList<>();
        directions.add(MoveDirection.FORWARD);
        directions.add(MoveDirection.FORWARD);
        directions.add(MoveDirection.RIGHT);
        directions.add(MoveDirection.RIGHT);
        directions.add(MoveDirection.FORWARD);
        directions.add(MoveDirection.FORWARD);

        List<Vector2d> positions = new ArrayList<>();
        positions.add(new Vector2d(2, 2));
        positions.add(new Vector2d(2, 2));

        Simulation simulation = new Simulation(positions, directions);
        simulation.run();

        List<Animal> animals = simulation.getAnimals();

        assertEquals(2, animals.size());

        for (Animal animal : animals) {
            assertEquals(MapDirection.EAST, animal.getDirection());
            assertTrue(animal.getCoordinates().follows(new Vector2d(0, 0)));
            assertTrue(animal.getCoordinates().precedes(new Vector2d(4, 4)));
        }
    }

    @Test
    public void testSimulationSingleAnimal() {
        List<MoveDirection> directions = new ArrayList<>();
        directions.add(MoveDirection.FORWARD);
        directions.add(MoveDirection.RIGHT);
        directions.add(MoveDirection.BACKWARD);

        List<Vector2d> positions = new ArrayList<>();
        positions.add(new Vector2d(0, 0));

        Simulation simulation = new Simulation(positions, directions);
        simulation.run();

        List<Animal> animals = simulation.getAnimals();

        assertEquals(1, animals.size());

        Animal animal = animals.get(0);

        assertEquals(MapDirection.EAST, animal.getDirection());
        assertEquals(new Vector2d(0, 1), animal.getCoordinates());
    }

    @Test
    public void testAnimalMovement() {
        Animal animal = new Animal(new Vector2d(2, 2));

        animal.move(MoveDirection.FORWARD);
        assertEquals(MapDirection.NORTH, animal.getDirection());
        assertEquals(new Vector2d(2, 3), animal.getCoordinates());

        animal.move(MoveDirection.RIGHT);
        assertEquals(MapDirection.EAST, animal.getDirection());
        assertEquals(new Vector2d(2, 3), animal.getCoordinates());

        animal.move(MoveDirection.BACKWARD);
        assertEquals(MapDirection.EAST, animal.getDirection());
        assertEquals(new Vector2d(1, 3), animal.getCoordinates());
    }

    @Test
    public void testAnimalEdgeCases() {
        Animal animal = new Animal(new Vector2d(0, 0));

        animal.move(MoveDirection.LEFT);
        assertEquals(MapDirection.WEST, animal.getDirection());
        assertEquals(new Vector2d(0, 0), animal.getCoordinates());

        animal.move(MoveDirection.BACKWARD);
        assertEquals(MapDirection.WEST, animal.getDirection());
        assertEquals(new Vector2d(1, 0), animal.getCoordinates());

        animal.move(MoveDirection.RIGHT);
        assertEquals(MapDirection.NORTH, animal.getDirection());
        assertEquals(new Vector2d(1, 0), animal.getCoordinates());

        animal.move(MoveDirection.FORWARD);
        assertEquals(MapDirection.NORTH, animal.getDirection());
        assertEquals(new Vector2d(1, 1), animal.getCoordinates());
    }

    @Test
    public void testAnimalBoundaries() {
        Animal animal = new Animal(new Vector2d(0, 0));

        animal.move(MoveDirection.LEFT);

        animal.move(MoveDirection.FORWARD);
        assertEquals(new Vector2d(0, 0), animal.getCoordinates());

        animal.move(MoveDirection.LEFT);
        animal.move(MoveDirection.FORWARD);
        assertEquals(new Vector2d(0, 0), animal.getCoordinates());

        animal.move(MoveDirection.LEFT);
        for (int i = 0; i < 7; i++) {
            animal.move(MoveDirection.FORWARD);
        }
        assertEquals(new Vector2d(4, 0), animal.getCoordinates());

        animal.move(MoveDirection.LEFT);
        for (int i = 0; i < 9; i++) {
            animal.move(MoveDirection.FORWARD);
        }
        assertEquals(new Vector2d(4, 4), animal.getCoordinates());
    }

    @Test
    public void testAnimalIsAt() {
        Animal animal = new Animal(new Vector2d(2, 2));

        assertTrue(animal.isAt(new Vector2d(2, 2)));

        animal.move(MoveDirection.FORWARD);
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

}