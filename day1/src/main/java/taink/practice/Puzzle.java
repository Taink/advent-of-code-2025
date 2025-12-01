package taink.practice;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Puzzle {
    static void main() {

        Dial d = new Dial();

        Path path = Paths.get("src/main/resources/input.txt");
        List<String> lines;
        try {
            lines = Files.readAllLines(path);
        } catch (IOException e) {
            IO.println("Error while reading file: " + e.getMessage());
            lines = List.of();
        }

        for (String line : lines) {
            d.parseTextRotation(line);
        }

        IO.println("Password: " + d.getPassword());
    }
}
