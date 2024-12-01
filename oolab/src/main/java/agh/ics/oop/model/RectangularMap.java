package agh.ics.oop.model;

public class RectangularMap extends AbstractWorldMap {
    public RectangularMap(String id, int width, int height) {
        super(id);
        lowerLeft = new Vector2d(0, 0);
        upperRight = new Vector2d(width - 1, height - 1);
    }
}


