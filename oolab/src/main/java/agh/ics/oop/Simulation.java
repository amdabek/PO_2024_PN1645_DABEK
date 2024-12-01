package agh.ics.oop;

import agh.ics.oop.model.*;
import java.util.ArrayList;
import java.util.List;
import agh.ics.oop.model.util.IncorrectPositionException;

public class Simulation implements Runnable {
    private final List<Animal> animals;
    private final List<MoveDirection> directions;
    private final WorldMap map;


    public Simulation(List<MoveDirection> directions, List<Vector2d> positions, WorldMap map) {
        this.animals = new ArrayList<>();
        this.directions = directions;
        this.map = map;
        for (Vector2d position : positions) {
            Animal animal = new Animal(MapDirection.NORTH, position);
            try {
                map.place(animal);
                this.animals.add(animal);

            } catch (IncorrectPositionException e) {
                System.out.println(e.getMessage());
            }
        }

    }

    public void run() {
        for(int i = 0; i < directions.size(); i++){
            map.move(animals.get(i % animals.size()), directions.get(i));
        }
    }
    public List<Animal> getAnimals() {
        return this.animals;
    }


}


