package agh.ics.oop;
//lab2
import agh.ics.oop.model.MoveDirection;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class OptionsParserTest {

    @Test
    void parseForward() {
        String[] args = {"f"};
        MoveDirection[] expected = {MoveDirection.FORWARD};
        assertArrayEquals(expected, OptionsParser.parse(args));
    }

    @Test
    void parseBackward() {
        String[] args = {"b"};
        MoveDirection[] expected = {MoveDirection.BACKWARD};
        assertArrayEquals(expected, OptionsParser.parse(args));
    }

    @Test
    void parseRight() {
        String[] args = {"r"};
        MoveDirection[] expected = {MoveDirection.RIGHT};
        assertArrayEquals(expected, OptionsParser.parse(args));
    }

    @Test
    void parseLeft() {
        String[] args = {"l"};
        MoveDirection[] expected = {MoveDirection.LEFT};
        assertArrayEquals(expected, OptionsParser.parse(args));
    }

    @Test
    void parseUnknown() {
        String[] args = {"g"};
        MoveDirection[] expected = {MoveDirection.FAILED};
        assertArrayEquals(expected, OptionsParser.parse(args));
    }

    @Test
    void parseMultiple() {
        String[] args = {"f", "b", "r", "l"};
        MoveDirection[] expected = {
                MoveDirection.FORWARD,
                MoveDirection.BACKWARD,
                MoveDirection.RIGHT,
                MoveDirection.LEFT
        };
        assertArrayEquals(expected, OptionsParser.parse(args));
    }
}


