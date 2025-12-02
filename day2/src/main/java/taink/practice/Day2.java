package taink.practice;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Day2 {
    static void main() {
        Path path = Paths.get("src/main/resources/input.txt");
        StringTokenizer ranges;
        try {
            ranges = new StringTokenizer(Files.readString(path), " ,\t\r\n\f");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        IdAnalyzer idAnalyzer = new IdAnalyzer();
        List<String> simpleInvalidIds = new ArrayList<>();

        while (ranges.hasMoreTokens()) {
            String rangeString = ranges.nextToken();
            String[] extremes = rangeString.split("-");
            long start = Long.parseUnsignedLong(extremes[0]);
            long end = Long.parseUnsignedLong(extremes[1]);

            simpleInvalidIds.addAll(idAnalyzer.simpleInvalidIdsInRange(start, end));
        }

        long sumOfSimpleInvalidIds = simpleInvalidIds.stream().mapToLong(Long::parseLong).sum();
        IO.println("Sum of simple invalid ids: " + sumOfSimpleInvalidIds);
    }
}
