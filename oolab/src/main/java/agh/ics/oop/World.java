package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.model.MapDirection;

public class World {
    public static void main(String[] args) {
        //lab1
        /*System.out.println("Start");

        MoveDirection[] directions = OptionsParser.parse(args);
        run(directions);

        System.out.println("Stop");*/
        Vector2d startPoint = new Vector2d(3, 4);
        System.out.println("Start Point: " + startPoint);

        Vector2d endPoint = new Vector2d(-1, 3);
        System.out.println("End Point: " + endPoint);

        Vector2d resultVector = startPoint.add(endPoint);
        System.out.println("Result of addition: " + resultVector);

        MapDirection currentDirection = MapDirection.WEST;
        System.out.println("Current Direction: " + currentDirection);

        MapDirection nextDirection = currentDirection.next();
        System.out.println("Next Direction: " + nextDirection);

        MapDirection previousDirection = currentDirection.previous();
        System.out.println("Previous Direction: " + previousDirection);

        Vector2d unitVector = currentDirection.toUnitVector();
        System.out.println("Unit Vector of Current Direction: " + unitVector);

    }

    public static void run(MoveDirection[] directions) {
        for (MoveDirection direction : directions) {
            if (direction != null) {
            switch (direction) {

                case FORWARD -> System.out.println("Zwierzak idzie do przodu");
                case BACKWARD -> System.out.println("Zwierzak idzie do tyłu");
                case RIGHT -> System.out.println("Zwierzak skręca w prawo");
                case LEFT -> System.out.println("Zwierzak skręca w lewo");
            }

            }
        }
    }
}





