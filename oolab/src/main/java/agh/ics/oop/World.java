package agh.ics.oop;

import agh.ics.oop.model.*;

import java.util.ArrayList;
import java.util.List;

public class World {
    public static void main(String[] args) {
        System.out.println("start");

        try {
            String[] argsDirections = {"f", "b", "r", "l", "f", "f", "r", "r", "f", "f"};
            List<MoveDirection> directions = OptionsParser.parse(argsDirections);

            List<Simulation> simulations = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                List<Vector2d> positions = List.of(
                        new Vector2d(i % 10, i % 10)
                );
                WorldMap map = new GrassField("Map" + i, 10);
                map.addObserver(new ConsoleMapDisplay());
                Simulation simulation = new Simulation(directions, positions, map);
                simulations.add(simulation);
            }

            SimulationEngine engine = new SimulationEngine(simulations);
            engine.runAsyncInThreadPool();
            engine.awaitSimulationsEnd();



        } catch (IllegalArgumentException e) {
            System.out.println("Error while parsing " + e.getMessage());
            return;
        }

        System.out.println("System zakończył działanie");


    }
}






