package agh.ics.oop.model;

public class Animal {
    private MapDirection orientation;
    private Vector2d position;
    private static final Vector2d LOWER_LEFT = new Vector2d(0, 0);
    private static final Vector2d UPPER_RIGHT = new Vector2d(4, 4);

    public Animal() {
        this.position = new Vector2d(2, 2);
        this.orientation = MapDirection.NORTH;
    }

    public Animal(Vector2d initialPosition) {
        this.position = initialPosition;
        this.orientation = MapDirection.NORTH;
    }

    @Override
    public String toString() {
        return this.position + ", " + this.orientation;
    }

    public Vector2d getCoordinates() {
        return this.position;
    }

    public MapDirection getDirection() {
        return this.orientation;
    }

    public boolean isAt(Vector2d position) {
        return this.position.equals(position);
    }

    public void move(MoveDirection direction) {
        switch (direction) {
            case RIGHT -> this.orientation = this.orientation.next();
            case LEFT -> this.orientation = this.orientation.previous();
            case FORWARD, BACKWARD -> {
                Vector2d moveVector = this.orientation.toUnitVector();
                if (direction == MoveDirection.BACKWARD) {
                    moveVector = moveVector.opposite();
                }
                Vector2d newPosition = this.position.add(moveVector);
                if (newPosition.follows(LOWER_LEFT) && newPosition.precedes(UPPER_RIGHT)) {
                    this.position = newPosition;
                }
            }
            default -> {

            }
        }
    }
}


