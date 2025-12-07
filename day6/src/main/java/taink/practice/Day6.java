package taink.practice;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day6 {
    static void main() {
        Path path = Paths.get("src/main/resources/input.txt");
        List<String> input;
        List<List<Integer>> cols = new ArrayList<>();
        List<Long> colResults = new ArrayList<>();
        try {
            input = Files.readAllLines(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (int i = 0; i < input.size() - 1; i++) {
            String[] inputRow = input.get(i).split("\\s+");
            for (int j = 0; j < inputRow.length; j++) {
                if (i == 0) {
                    cols.add(new ArrayList<>());
                }
                cols.get(j).add(Integer.parseInt(inputRow[j]));
            }
        }

        String[] operations = input.getLast().split("\\s+");
        for (int i = 0; i < operations.length; i++) {
            List<Long> col = cols.get(i).stream().mapToLong(Integer::longValue).boxed().toList();
            switch (operations[i]) {
                case "+":
                    colResults.add(col.stream().reduce(0L, Long::sum));
                    break;
                case "*":
                    colResults.add(col.stream().reduce(1L, (x, y) -> x * y));
                    break;
            }
        }

        IO.println("Worksheet: " + cols);
        IO.println("Results: " + colResults);
        IO.println("Sum of results: " + colResults.stream().reduce(0L, Long::sum));
    }
}
