package agh.ics.oop;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.Vector2d;

import java.util.List;
import java.util.ArrayList;

public class Simulation {
    private List<Animal> animals;
    private List<MoveDirection> directions;

    public Simulation(List<Vector2d> positions, List<MoveDirection> directions) {
        this.animals = new ArrayList<>();
        for (Vector2d position : positions) {
            this.animals.add(new Animal(position));
        }
        this.directions = directions;
    }

    public List<Animal> getAnimals() {
        return this.animals;
    }

    public void run() {
        int numberOfAnimals = animals.size();
        int animalIndex = 0;

        for (MoveDirection direction : directions) {
            Animal animal = animals.get(animalIndex);
            animal.move(direction);

            System.out.println("Zwierzę " + animalIndex + " : " + animal);

            animalIndex = (animalIndex + 1) % numberOfAnimals;
        }
    }
}


