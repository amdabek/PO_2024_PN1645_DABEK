package agh.ics.oop;

import agh.ics.oop.model.*;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class SimulationTest {

    @Test
    public void simMultipleAnimals() {
        List<MoveDirection> directions = OptionsParser.parse("r l f f 5 g s f f b r b r".split(" "));
        List<Vector2d> positions = List.of(
                new Vector2d(2, 2),
                new Vector2d(3, 3)
        );
        WorldMap map = new RectangularMap(8, 8);

        Simulation simulation = new Simulation(directions, positions, map);
        simulation.run();

        List<Animal> animals = simulation.getAnimals();

        assertEquals(2, animals.size());

        Animal animal1 = animals.get(0);
        Animal animal2 = animals.get(1);

        assertEquals(new Vector2d(2, 2), animal1.getPosition());
        assertEquals(new Vector2d(1, 3), animal2.getPosition());

        assertEquals(MapDirection.EAST, animal1.getDirection());
        assertEquals(MapDirection.EAST, animal2.getDirection());
    }

    @Test
    public void simSingleAnimal() {
        List<MoveDirection> directions = OptionsParser.parse("f s f 5 l f".split(" "));
        List<Vector2d> positions = List.of(new Vector2d(2, 2));
        WorldMap map = new RectangularMap(8, 8);
        Simulation simulation = new Simulation(directions, positions, map);
        simulation.run();

        List<Animal> animals = simulation.getAnimals();
        assertEquals(1, animals.size());
        Animal animal = animals.get(0);

        assertEquals(new Vector2d(1, 4), animal.getPosition());
        assertEquals(MapDirection.WEST, animal.getDirection());
        assertTrue(animal.getPosition().follows(new Vector2d(0, 0)));
        assertTrue(animal.getPosition().precedes(new Vector2d(7, 7)));
    }

    @Test
    public void animalStaysInBounds() {
        List<MoveDirection> directions = OptionsParser.parse("b f".split(" "));
        List<Vector2d> positions = List.of(new Vector2d(0, 0), new Vector2d(4, 4));
        WorldMap map = new RectangularMap(5, 5);
        Simulation simulation = new Simulation(directions, positions, map);
        simulation.run();
        List<Animal> animals = simulation.getAnimals();

        for (Animal animal : animals) {
            assertTrue(animal.getPosition().follows(new Vector2d(0, 0)));
            assertTrue(animal.getPosition().precedes(new Vector2d(4, 4)));
        }
    }

    @Test
    public void simEmptyInput() {
        List<MoveDirection> directions = new ArrayList<>();
        List<Vector2d> positions = new ArrayList<>();
        WorldMap map = new RectangularMap(5,5);

        Simulation simulation = new Simulation(directions, positions, map);
        simulation.run();

        List<Animal> animals = simulation.getAnimals();

        assertTrue(animals.isEmpty());
    }

    @Test
    public void simOnGrassField() {
        List<MoveDirection> directions = OptionsParser.parse("f f r f l b".split(" "));
        List<Vector2d> positions = List.of(new Vector2d(0, 0));
        WorldMap map = new GrassField(10);
        Simulation simulation = new Simulation(directions, positions, map);
        simulation.run();

        List<Animal> animals = simulation.getAnimals();
        assertEquals(1, animals.size());
        Animal animal = animals.get(0);

        assertEquals(MapDirection.NORTH, animal.getDirection());
        assertEquals(new Vector2d(1, 1), animal.getPosition());
    }
}



