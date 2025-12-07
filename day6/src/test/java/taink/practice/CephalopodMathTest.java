package taink.practice;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CephalopodMathTest {

    @Test
    void parseSmallInput() {
        Path path = Paths.get("src/test/resources/small_input.txt");
        List<String> input;
        try {
            input = Files.readAllLines(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        CephalopodMath math = new CephalopodMath(input);
        assertEquals(1058, math.getResultOfColumn(3));
        assertEquals(3253600, math.getResultOfColumn(2));
        assertEquals(625, math.getResultOfColumn(1));
        assertEquals(8544, math.getResultOfColumn(0));
        assertEquals(3263827, math.getSumOfResults());
    }

}