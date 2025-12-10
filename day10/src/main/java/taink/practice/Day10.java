package taink.practice;

import taink.practice.common.Pair;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Day10 {
    static void main() {
        Path path = Paths.get("src/main/resources/input.txt");
//        Path path = Paths.get("src/test/resources/small_input.txt");
        List<String> input;
        try {
            input = Files.readAllLines(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
