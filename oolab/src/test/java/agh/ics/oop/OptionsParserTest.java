package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class OptionsParserTest {

    @Test
    void parseForward() {
        String[] args = {"f"};
        List<MoveDirection> expected = List.of(MoveDirection.FORWARD);
        assertEquals(expected, OptionsParser.parse(args));
    }

    @Test
    void parseBackward() {
        String[] args = {"b"};
        List<MoveDirection> expected = List.of(MoveDirection.BACKWARD);
        assertEquals(expected, OptionsParser.parse(args));
    }

    @Test
    void parseRight() {
        String[] args = {"r"};
        List<MoveDirection> expected = List.of(MoveDirection.RIGHT);
        assertEquals(expected, OptionsParser.parse(args));
    }

    @Test
    void parseLeft() {
        String[] args = {"l"};
        List<MoveDirection> expected = List.of(MoveDirection.LEFT);
        assertEquals(expected, OptionsParser.parse(args));
    }

    @Test
    void parseUnknown() {
        String[] args = {"x"};
        List<MoveDirection> expected = List.of(); // Niepoprawne opcje są ignorowane
        assertEquals(expected, OptionsParser.parse(args));
    }

    @Test
    void parseMultiple() {
        String[] args = {"f", "b", "r", "l"};
        List<MoveDirection> expected = List.of(
                MoveDirection.FORWARD,
                MoveDirection.BACKWARD,
                MoveDirection.RIGHT,
                MoveDirection.LEFT
        );
        assertEquals(expected, OptionsParser.parse(args));
    }

    @Test
    void parseEmpty() {
        String[] args = {};
        List<MoveDirection> expected = List.of();
        assertEquals(expected, OptionsParser.parse(args));
    }
}
