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
        Set<Rect> edges = new HashSet<>();
        for (String line : input) {
            String[] coordinates = line.split(",");
            int x = Integer.parseInt(coordinates[0]);
            int y = Integer.parseInt(coordinates[1]);
            Pair<Integer, Integer> newTile = new Pair<>(x, y);
            if (!redTileCoords.isEmpty()) {
                Pair<Integer, Integer> prev = redTileCoords.getLast();
                edges.add(new Rect(prev, newTile));
            }
            redTileCoords.addLast(newTile);
        }
        edges.add(new Rect(redTileCoords.getFirst(), redTileCoords.getLast()));

        Map<Set<Pair<Integer, Integer>>, Long> rectangleAreaMatrix = new HashMap<>();
        for (int i = 0; i < redTileCoords.size() - 1; i++) {
            var baseRedTile = redTileCoords.get(i);
            for (int j = i + 1; j < redTileCoords.size(); j++) {
                var otherRedTile = redTileCoords.get(j);
                var pair = Set.of(baseRedTile, otherRedTile); // we store sets so that tiles are interchangeable
                var rect = new Rect(baseRedTile, otherRedTile);
                if (edges.stream().anyMatch(e -> e.intersects(rect))) {
                    // if rect intersects edge, it is not fully inside the loop, so we ignore it
                    continue;
                }
                rectangleAreaMatrix.computeIfAbsent(pair, _ -> rect.area());
            }
        }

        long highestArea = rectangleAreaMatrix.values().stream().max(Comparator.naturalOrder()).orElse(0L);
        IO.println("Highest area: "+highestArea);
    }
}
