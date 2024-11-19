package agh.ics.oop;

import agh.ics.oop.model.*;
import java.util.List;

public class World {
    public static void main(String[] args) {
        System.out.println("start");

        List<MoveDirection> directions = OptionsParser.parse(args);
        List<Vector2d> positions = List.of(
                new Vector2d(0, 0),
                new Vector2d(4, 4)
        );
        WorldMap map = new GrassField(10);
        Simulation simulation = new Simulation(directions, positions, map);
        simulation.run();

        System.out.println("koniec");
    }
}






