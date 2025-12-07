package taink.practice;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Day6 {
    static void main() {
        Path path = Paths.get("src/main/resources/input.txt");
        List<String> input;
        try {
            input = Files.readAllLines(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        CephalopodMath math = new CephalopodMath(input);
        IO.println("Sum of results: " + math.getSumOfResults());
    }
}
