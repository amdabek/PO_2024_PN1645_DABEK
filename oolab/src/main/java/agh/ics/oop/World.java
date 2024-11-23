package agh.ics.oop;

import agh.ics.oop.model.*;
import java.util.List;

public class World {
    public static void main(String[] args) {
        System.out.println("start");

        try {
            List<MoveDirection> directions = OptionsParser.parse(args);
            List<Vector2d> positions = List.of(
                    new Vector2d(0, 0),
                    new Vector2d(4, 4),
                    new Vector2d(4, 4)
            );
            AbstractWorldMap map = new GrassField(10);
            map.addObserver(new ConsoleMapDisplay());
            Simulation simulation = new Simulation(directions, positions, map);
            simulation.run();
        } catch (IllegalArgumentException e) {
            System.out.println("Error while parsing " + e.getMessage());
            return;
        }

        System.out.println("koniec");
    }
}






