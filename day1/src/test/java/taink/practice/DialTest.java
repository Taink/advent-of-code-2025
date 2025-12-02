package taink.practice;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DialTest {

    @Test
    void turnLeft() {
        Dial d = new Dial();
        assertEquals(50, d.getPosition());
        d.turnLeft(1);
        assertEquals(49, d.getPosition());

        Dial d2 = new Dial(0);
        assertEquals(0, d2.getPosition());
        d2.turnLeft(1);
        assertEquals(99, d2.getPosition());

        Dial d3 = new Dial(50);
        assertEquals(50, d3.getPosition());
        d3.turnLeft(100);
        assertEquals(50, d3.getPosition());
        d3.turnLeft(150);
        assertEquals(0,  d3.getPosition());

        Dial d4 = new Dial(42);
        assertEquals(42, d4.getPosition());
        d4.turnLeft(100);
        assertEquals(42, d4.getPosition());
        d4.turnLeft(150);
        assertEquals(92,  d4.getPosition());

        Dial d5 = new Dial(42);
        assertEquals(42, d5.getPosition());
        d5.turnLeft(102);
        assertEquals(40, d5.getPosition());
        d5.turnLeft(783);
        assertEquals(57,  d5.getPosition());
    }

    @Test
    void turnRight() {
        Dial d = new Dial();
        assertEquals(50, d.getPosition());
        d.turnRight(1);
        assertEquals(51, d.getPosition());

        Dial d2 = new Dial(99);
        assertEquals(99, d2.getPosition());
        d2.turnRight(1);
        assertEquals(0, d2.getPosition());

        Dial d3 = new Dial(50);
        assertEquals(50, d3.getPosition());
        d3.turnRight(100);
        assertEquals(50, d3.getPosition());
        d3.turnRight(150);
        assertEquals(0,  d3.getPosition());

        Dial d4 = new Dial(42);
        assertEquals(42, d4.getPosition());
        d4.turnRight(100);
        assertEquals(42, d4.getPosition());
        d4.turnRight(150);
        assertEquals(92,  d4.getPosition());

        Dial d5 = new Dial(42);
        assertEquals(42, d5.getPosition());
        d5.turnRight(98);
        assertEquals(40, d5.getPosition());
        d5.turnRight(787);
        assertEquals(27,  d5.getPosition());
    }

    @Test
    void basicParse() {
        Dial d = new Dial();
        assertEquals(50, d.getPosition());
        d.parseTextRotation("L1");
        assertEquals(49, d.getPosition());
        d.parseTextRotation("R2");
        assertEquals(51, d.getPosition());
    }

    @Test
    void smallFileParse() throws IOException {
        int[] expected_positions = {82, 52, 0, 95, 55, 0, 99, 0, 14, 32};
        Dial d = new Dial();

        Path path = Paths.get("src/test/resources/small_input.txt");
        List<String> lines = Files.readAllLines(path);

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            int expected_position = expected_positions[i];
            d.parseTextRotation(line);
            assertEquals(expected_position, d.getPosition());
        }

        assertEquals(3, d.getPassword());
        assertEquals(6, d.getCompletePassword());
    }
}