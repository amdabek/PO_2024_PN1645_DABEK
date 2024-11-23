package agh.ics.oop.model;

import agh.ics.oop.model.util.Boundary;

import java.util.*;

public class GrassField extends AbstractWorldMap {
    private final Map<Vector2d, Grass> grasses = new HashMap<>();

    public GrassField(int grassNumber) {
        int range = (int) Math.sqrt(grassNumber * 10);
        Random rand = new Random();

        for (int i = 0; i < grassNumber; ) {
            int x = rand.nextInt(range + 1);
            int y = rand.nextInt(range + 1);
            Vector2d position = new Vector2d(x, y);
            if (!grasses.containsKey(position)) {
                grasses.put(position, new Grass(position));
                i++;
            }
        }
    }


    @Override
    public WorldElement objectAt(Vector2d position) {
        WorldElement element = super.objectAt(position);
        if (element != null) {
            return element;
        } else {
            return grasses.get(position);
        }
    }

    @Override
    public List<WorldElement> getElements() {
        List<WorldElement> elements = super.getElements();
        elements.addAll(grasses.values());
        return elements;
    }

    @Override
    public String toString() {
        Vector2d bottom = new Vector2d(upperRight.x, upperRight.y);
        Vector2d top = new Vector2d(lowerLeft.x, lowerLeft.y);
        List<WorldElement> elements = getElements();
        for (WorldElement element: elements) {
            bottom = bottom.lowerLeft(element.getPosition());
            top = top.upperRight(element.getPosition());
        }
        return visualizer.draw(bottom, top);
    }
    @Override
    public Boundary getCurrentBounds() {
        Vector2d min = new Vector2d(upperRight.x, upperRight.y);
        Vector2d max = new Vector2d(lowerLeft.x, lowerLeft.y);

        for (WorldElement element : getElements()) {
            Vector2d pos = element.getPosition();
            min = min.lowerLeft(pos);
            max = max.upperRight(pos);
        }

        return new Boundary(min, max);
    }

    public Collection<Grass> getGrasses() {
        return grasses.values();
    }

    public void addGrass(Grass grass) {
        grasses.put(grass.getPosition(), grass);
    }
}

