package agh.ics.oop.model;

public class Animal {
    private MapDirection direction;
    private Vector2d position;

    public Animal() {
        direction = MapDirection.NORTH;
        position = new Vector2d(2, 2);
    }

    public Animal(MapDirection direction, Vector2d position) {
        this.direction = direction;
        this.position = position;
    }

    public Vector2d getCoordinates() {
        return this.position;
    }

    public MapDirection getDirection() {
        return this.direction;
    }

    public String toString() {
        return switch (this.direction) {
            case NORTH -> "N";
            case EAST -> "E";
            case SOUTH -> "S";
            case WEST -> "W";
        };
    }

    public String toString(int numberOfAnimal) {
        return "Zwierze " + numberOfAnimal + " jest na pozycji: " + this.position.toString() + " i orientacji: " + this.direction.toString();
    }

    public boolean isAt(Vector2d position) {
        return this.position.equals(position);
    }

    public void move(MoveDirection direction, MoveValidator validator) {
        switch (direction) {
            case RIGHT -> this.direction = this.direction.next();
            case LEFT -> this.direction = this.direction.previous();
            case FORWARD -> {
                Vector2d newPositionForward = this.position.add(this.direction.toUnitVector());
                if (validator.canMoveTo(newPositionForward)) {
                    this.position = newPositionForward;
                }
            }
            case BACKWARD -> {
                Vector2d newPositionBackwards = this.position.subtract(this.direction.toUnitVector());
                if (validator.canMoveTo(newPositionBackwards)) {
                    this.position = newPositionBackwards;
                }
            }
            default -> {}

        }
    }
}


