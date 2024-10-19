package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;

public class World {
    public static void main(String[] args) {
        System.out.println("Start");

        MoveDirection[] directions = OptionsParser.parse(args);
        run(directions);

        System.out.println("Stop");
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





