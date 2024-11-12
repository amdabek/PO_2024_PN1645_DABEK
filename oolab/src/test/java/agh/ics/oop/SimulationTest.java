package agh.ics.oop;

import agh.ics.oop.model.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SimulationTest {
    @Test
    public void simulationOfMultipleAnimals() {
        List<MoveDirection> directions = OptionsParser.parse("r l f f 5 g s f f b r b r".split(" "));
        List<Vector2d> positions = new ArrayList<>();
        RectangularMap map = new RectangularMap(8,8);
        positions.add(new Vector2d(2, 2));
        positions.add(new Vector2d(3, 3));

        Simulation simulation = new Simulation(map, positions, directions);
        simulation.run();

        List<Animal> animals = simulation.getAnimals();

        assertEquals(2, animals.size());
        assertTrue(animals.get(0).isAt(new Vector2d(2, 2)));
        assertTrue(animals.get(1).isAt(new Vector2d(1, 3)));

        for(Animal animal : animals) {
            assertEquals(MapDirection.EAST, animal.getDirection());
            assertTrue(animal.getCoordinates().precedes(new Vector2d(4, 4)));
            assertTrue(animal.getCoordinates().follows(new Vector2d(0,0)));
        }
    }

    @Test
    public void simulationOfSingleAnimal() {
        List<MoveDirection> directions = OptionsParser.parse("f s f 5 l f".split(" "));
        List<Vector2d> positions = List.of(new Vector2d(2, 2));
        RectangularMap map = new RectangularMap(8,8);
        Simulation simulation = new Simulation(map, positions, directions);
        simulation.run();

        List<Animal> animals = simulation.getAnimals();
        assertEquals(1, animals.size());
        assertTrue(animals.get(0).isAt(new Vector2d(1,4)));
        assertEquals(MapDirection.WEST, animals.get(0).getDirection());
        assertTrue(animals.get(0).getCoordinates().follows(new Vector2d(0,0)));
        assertTrue(animals.get(0).getCoordinates().precedes(new Vector2d(4,4)));
    }

    @Test
    public void animalDoesntGoOutOfBounds() {
        List<MoveDirection> directions = OptionsParser.parse("b f".split(" "));
        List<Vector2d> positions = List.of(new Vector2d(0, 0), new Vector2d(4, 4));
        RectangularMap map = new RectangularMap(5,5);
        Simulation simulation = new Simulation(map, positions, directions);
        simulation.run();
        List<Animal> animals = simulation.getAnimals();

        for (Animal animal : animals) {
            assertTrue(animal.getCoordinates().follows(new Vector2d(0,0)));
            assertTrue(animal.getCoordinates().precedes(new Vector2d(4,4)));
        }
    }

    @Test
    public void simulationWithEmptyInput() {
        List<MoveDirection> directions = new ArrayList<>();
        List<Vector2d> positions = new ArrayList<>();
        RectangularMap map = new RectangularMap(5,5);

        Simulation simulation = new Simulation(map, positions, directions);
        simulation.run();

        List<Animal> animals = simulation.getAnimals();

        assertTrue(animals.isEmpty());
    }
}



