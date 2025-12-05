package taink.practice;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Day4 {
    static void main() {
        Path path = Paths.get("src/main/resources/input.txt");
        String input;
        try {
            input = Files.readString(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        PaperRollGrid papers = new PaperRollGrid();
        papers.fillFromString(input);
        int accessibleRolls = 0;
        int totalAccessibleRolls = accessibleRolls;
        do {
            papers.markAccessibleRollsWithX();
            IO.println("Paper grid, marked with Xs:");
            IO.print(papers.toString());
            accessibleRolls = papers.countAccessibleRolls();
            IO.println("Accessible paper rolls: " + accessibleRolls);
            totalAccessibleRolls += accessibleRolls;
            papers.emptyAccessibleRolls();
        } while (accessibleRolls > 0);
        IO.println("Total accessible rolls: " + totalAccessibleRolls);
    }
}
