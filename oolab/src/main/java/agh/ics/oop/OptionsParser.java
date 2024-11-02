package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;

public class OptionsParser {

    public static MoveDirection[] parse(String[] args) {

        MoveDirection[] directions = new MoveDirection[args.length];
        int index = 0;

        for (String arg : args) {
            directions[index] = getDirection(arg);
            index++;
        }

        return directions;
    }

    private static MoveDirection getDirection(String arg) {
        return switch (arg) {
            case "f" -> MoveDirection.FORWARD;
            case "b" -> MoveDirection.BACKWARD;
            case "r" -> MoveDirection.RIGHT;
            case "l" -> MoveDirection.LEFT;
            default -> MoveDirection.FAILED;
        };
    }
}
