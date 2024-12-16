package agh.ics.oop.presenter;

import agh.ics.oop.Simulation;
import agh.ics.oop.SimulationEngine;
import agh.ics.oop.model.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.util.List;

import static agh.ics.oop.OptionsParser.parse;

public class SimulationPresenter implements MapChangeListener {
    private WorldMap currentMap;
    @FXML
    private GridPane mapGrid;
    @FXML
    private TextField moveListTextField;
    @FXML
    private Label moveDescriptionLabel;

    private int minX;
    private int minY;
    private int maxX;
    private int maxY;
    private int currentMapWidth;
    private int currentMapHeight;

    private int cellWidth = 50;
    private int cellHeight = 50;

    private final int maxGridHeight = 300;
    private final int maxGridWidth = 300;

    public void setWorldMap(WorldMap map) {
        this.currentMap = map;
    }

    private void updateBounds() {
        var currentBounds = currentMap.getCurrentBounds();
        minX = currentBounds.lowerLeft().x;
        minY = currentBounds.lowerLeft().y;
        maxX = currentBounds.upperRight().x;
        maxY = currentBounds.upperRight().y;
        currentMapWidth = maxX - minX + 1;
        currentMapHeight = maxY - minY + 1;

        cellWidth = Math.max(1, Math.min(maxGridWidth / currentMapWidth, maxGridHeight / currentMapHeight));
        cellHeight = cellWidth;
    }

    private void clearGrid() {
        if (!mapGrid.getChildren().isEmpty()) {
            mapGrid.getChildren().retainAll(mapGrid.getChildren().get(0));
        }
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }

    private void drawMap() {
        updateBounds();
        clearGrid();

        mapGrid.getColumnConstraints().add(new ColumnConstraints(cellWidth));
        mapGrid.getRowConstraints().add(new RowConstraints(cellHeight));
        Label axisLabel = new Label("y/x");
        mapGrid.add(axisLabel, 0, 0);
        GridPane.setHalignment(axisLabel, HPos.CENTER);

        for (int i = 0; i < currentMapWidth; i++) {
            Label coordinateLabel = new Label(Integer.toString(minX + i));
            GridPane.setHalignment(coordinateLabel, HPos.CENTER);
            mapGrid.getColumnConstraints().add(new ColumnConstraints(cellWidth));
            mapGrid.add(coordinateLabel, i + 1, 0);
        }

        for (int i = 0; i < currentMapHeight; i++) {
            Label coordinateLabel = new Label(Integer.toString(maxY - i));
            GridPane.setHalignment(coordinateLabel, HPos.CENTER);
            mapGrid.getRowConstraints().add(new RowConstraints(cellHeight));
            mapGrid.add(coordinateLabel, 0, i + 1);
        }

        for (int x = minX; x <= maxX; x++) {
            for (int y = maxY; y >= minY; y--) {
                Vector2d currentPosition = new Vector2d(x, y);
                Label cellContentLabel;
                if (currentMap.isOccupied(currentPosition)) {
                    cellContentLabel = new Label(currentMap.objectAt(currentPosition).toString());
                } else {
                    cellContentLabel = new Label(" ");
                }
                GridPane.setHalignment(cellContentLabel, HPos.CENTER);
                mapGrid.add(cellContentLabel, x - minX + 1, maxY - y + 1);
            }
        }

        mapGrid.setGridLinesVisible(true);
    }

    @Override
    public void mapChanged(WorldMap updatedMap, String updateMessage) {
        setWorldMap(updatedMap);
        Platform.runLater(() -> {
            drawMap();
            moveDescriptionLabel.setText(updateMessage);
        });
    }

    @FXML
    private void startSimulation() {
        String userMoveList = moveListTextField.getText();
        List<MoveDirection> parsedDirections = parse(userMoveList.split(" "));
        List<Vector2d> initialPositions = List.of(new Vector2d(1,2), new Vector2d(5,6));
        AbstractWorldMap simulationMap = new GrassField("GrassMap",10);
        simulationMap.addObserver(this);

        Simulation simulation = new Simulation(parsedDirections, initialPositions, simulationMap);
        setWorldMap(simulationMap);
        drawMap();

        new Thread(() -> {
            SimulationEngine simulationEngine = new SimulationEngine(List.of(simulation));
            simulationEngine.runAsync();
        }).start();

    }

}
