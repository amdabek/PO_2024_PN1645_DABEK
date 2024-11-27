package agh.ics.oop.model;

public class ConsoleMapDisplay implements MapChangeListener {
    private int upCount = 0;

    @Override
    public void mapChanged(WorldMap worldMap, String message) {
        upCount++;
        System.out.println("Map Update #" + upCount);
        System.out.println("Operation: " + message);
        System.out.println(worldMap);
    }
}
