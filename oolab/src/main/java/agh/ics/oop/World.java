package agh.ics.oop;

import agh.ics.oop.model.*;

import java.util.ArrayList;
import java.util.List;

public class World {
    public static void main(String[] args) {
        System.out.println("start");

        try {

            String[] argsDirections = {"f", "b", "r", "l", "f", "f", "r", "r", "f", "f", "l", "l", "f", "f"};
            List<MoveDirection> directions = OptionsParser.parse(argsDirections);

            WorldMap rectangularMap = new RectangularMap("RectMap", 10, 10);
            rectangularMap.addObserver(new ConsoleMapDisplay());
            Simulation simulation1 = new Simulation(directions, List.of(new Vector2d(2,2)), rectangularMap);

            WorldMap grassField = new GrassField("GrassMap", 10);
            grassField.addObserver(new ConsoleMapDisplay());
            Simulation simulation2 = new Simulation(directions, List.of(new Vector2d(3,4)), grassField);

            List<Simulation> simulations = new ArrayList<>();
            simulations.add(simulation1);
            simulations.add(simulation2);

            SimulationEngine engine = new SimulationEngine(simulations);

            // engine.runSync();

            // engine.runAsync();

            engine.runAsyncInThreadPool();
            engine.awaitSimulationsEnd();

        } catch (IllegalArgumentException e) {
            System.out.println("Error while parsing " + e.getMessage());
            return;
        }

        System.out.println("System zakończył działanie");
    }
}
