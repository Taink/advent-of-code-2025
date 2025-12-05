package taink.practice;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class PaperRollGridTest {
    @Test
    void fillingGivesSameString() throws IOException {
        Path path = Paths.get("src/test/resources/small_input.txt");
        String input = Files.readString(path);
        PaperRollGrid papers = new PaperRollGrid();
        papers.fillFromString(input);

        assertEquals(input, papers.toString());
    }

    @Test
    void parseSmallInput() throws IOException {
        Path path = Paths.get("src/test/resources/small_input.txt");
        String input = Files.readString(path);
        PaperRollGrid papers = new PaperRollGrid();
        papers.fillFromString(input);
        papers.markAccessibleRollsWithX();
        String expected = """
                        ..xx.xx@x.
                        x@@.@.@.@@
                        @@@@@.x.@@
                        @.@@@@..@.
                        x@.@@@@.@x
                        .@@@@@@@.@
                        .@.@.@.@@@
                        x.@@@.@@@@
                        .@@@@@@@@.
                        x.x.@@@.x.
                        """;
        assertEquals(expected, papers.toString());
        assertEquals(13, papers.countAccessibleRolls());
    }
}