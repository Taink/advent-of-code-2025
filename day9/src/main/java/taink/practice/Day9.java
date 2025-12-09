package taink.practice;

import taink.practice.common.Pair;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Day9 {
    static void main() {
        Path path = Paths.get("src/main/resources/input.txt");
//        Path path = Paths.get("src/test/resources/small_input.txt");
        List<String> input;
        try {
            input = Files.readAllLines(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<Pair<Integer, Integer>> redTileCoords = new ArrayList<>();
        for (String line : input) {
            String[] coordinates = line.split(",");
            redTileCoords.add(new Pair<>(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1])));
        }

        Map<Set<Pair<Integer, Integer>>, Long> rectangleAreaMatrix = new HashMap<>();
        for (int i = 0; i < redTileCoords.size() - 1; i++) {
            var baseRedTile = redTileCoords.get(i);
            for (int j = i + 1; j < redTileCoords.size(); j++) {
                var otherRedTile = redTileCoords.get(j);
                var pair = Set.of(baseRedTile, otherRedTile);
                rectangleAreaMatrix.computeIfAbsent(pair, _ -> rectangleArea(baseRedTile, otherRedTile));
            }
        }

        long highestArea = rectangleAreaMatrix.values().stream().max(Comparator.naturalOrder()).orElse(0L);
        IO.println("Highest area: "+highestArea);
    }

    private static long rectangleArea(Pair<Integer, Integer> a, Pair<Integer, Integer> b) {
        long rectangleLength = Math.abs(b.x() - a.x()) + 1;
        long rectangleWidth = Math.abs(b.y() - a.y()) + 1;
        assert rectangleWidth > 0;
        assert rectangleLength > 0;
        return rectangleLength * rectangleWidth;
    }
}
