package agh.ics.oop;

import agh.ics.oop.model.*;

import java.util.List;
import java.util.ArrayList;

public class Simulation {
    private final List<Animal> animals;
    private final List<MoveDirection> directions;
    private final WorldMap map;

    public Simulation(WorldMap map, List<Vector2d> positions, List<MoveDirection> directions) {
        this.map = map;
        this.animals = new ArrayList<>();
        this.directions = directions;
        for (Vector2d position : positions) {
            Animal animal = new Animal(MapDirection.NORTH, position);

            if (map.place(animal)) {
                animals.add(animal);
            }
        }

    }

    public List<Animal> getAnimals() {
        return this.animals;
    }
    public List<MoveDirection> getDirections() {
        return directions;
    }

    public void run() {
        int numberOfAnimals = animals.size();
        int animalIndex = 0;

        for (MoveDirection direction : directions) {
            Animal animal = animals.getFirst();
            animals.removeFirst();
            map.move(animal, direction);

            System.out.println("Zwierze " + ((animalIndex %numberOfAnimals) +1)+" "+animal.toString());
            System.out.println(map);
            animalIndex++;
            animals.add(animal);
        }
    }
}



