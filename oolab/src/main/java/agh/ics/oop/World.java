package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.model.WorldMap;
import agh.ics.oop.model.RectangularMap;

import java.util.List;

public class World {
    public static void main(String[] args) {
        List<MoveDirection> directions = OptionsParser.parse(args);
        List<Vector2d> positions = List.of(
                new Vector2d(2, 2),
                new Vector2d(3, 4),
                new Vector2d(0,0)
        );

        WorldMap map = new RectangularMap(10, 10);
        Simulation simulation = new Simulation(map, positions, directions);
        simulation.run();


    }
}


/*
/*
Skorzystałam z ArrayList  do implementacji listy ze względu na możliwość dynamicznego zmieniania rozmiaru, szybki, stały czas dostępu do indeksowanych danych.
Wszystkie te cechy ułatwiły wykonanie zadań, wpłynęły pozytywnie na wydajność oraz czytelność kodu.
 *//*



    public static void run(MoveDirection[] directions) {
        for (MoveDirection direction : directions) {

            switch (direction) {

                case FORWARD -> System.out.println("Zwierzak idzie do przodu");
                case BACKWARD -> System.out.println("Zwierzak idzie do tyłu");
                case RIGHT -> System.out.println("Zwierzak skręca w prawo");
                case LEFT -> System.out.println("Zwierzak skręca w lewo");
            }


        }
    }
}

*/
