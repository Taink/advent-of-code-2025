package taink.practice;

import taink.practice.common.Grid;

import java.util.List;
import java.util.StringTokenizer;

public class PaperRollGrid {
    public static final char PAPER_ROLL = '@';
    public static final char ACCESSIBLE_PAPER_ROLL = 'x';
    public static final char EMPTY_CELL = '.';
    public static final int UNACCESSIBLE_ROLLS_THRESHOLD = 4;
    private final Grid<Character> grid;
    public PaperRollGrid() {
        this.grid = new Grid<>();
    }

    public void fillFromString(String input) {
        StringTokenizer tokenizer = new StringTokenizer(input, "\n");
        int y = 0;
        while (tokenizer.hasMoreTokens()) {
            String row = tokenizer.nextToken();
            for (int x = 0; x < row.length(); x++) {
                this.grid.setElementAtCoords(x, y, row.charAt(x));
            }

            y++;
        }
    }

    public List<Character> getAllCells() {
        return this.grid.toList();
    }

    public void markAccessibleRollsWithX() {
        for (int y = 0; y <= this.grid.getMaxY(); y++) {
            for (int x = 0; x <= this.grid.getMaxX(); x++) {
                try {
                    if (this.grid.getElementAtCoords(x, y) != PAPER_ROLL) {
                        continue;
                    }

                    Grid<Character> neighbors = this.grid.getNeighbors3x3(x, y);
                    neighbors.setElementAtCoords(1, 1, null); // exclude self
                    List<Character> nonEmptyNeighbors = neighbors.toList().stream().filter(c -> c != null && c != EMPTY_CELL).toList();
                    if (nonEmptyNeighbors.size() < UNACCESSIBLE_ROLLS_THRESHOLD) {
                        this.grid.setElementAtCoords(x, y, ACCESSIBLE_PAPER_ROLL);
                    }
                } catch(IndexOutOfBoundsException e) {
                    System.exit(1);
                }
            }
        }
    }

    public int countAccessibleRolls() {
        return this.grid.toList().stream().filter(c -> c == ACCESSIBLE_PAPER_ROLL).toList().size();
    }

    @Override
    public String toString() {
        return grid.toString();
    }
}
