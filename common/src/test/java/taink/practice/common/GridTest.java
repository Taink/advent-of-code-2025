package taink.practice.common;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GridTest {

    private static Grid<Integer> ascendingSquareGrid(int dim) {
        Grid<Integer> result = new Grid<>();
        for (int y = 0; y < dim; y++) {
            for (int x = 0; x < dim; x++) {
                result.setElementAtCoords(x, y, y*dim+x);
            }
        }
        return result;
    }
    private static List<Integer> range(int start, int end) {
        List<Integer> result = new ArrayList<>();
        for (int i = start; i < end; i++) {
            result.add(i);
        }
        return List.copyOf(result);
    }

    @Test
    void getElementAtCoordsThrowsIffOutOfBoundsCoords() {
        Grid<String> grid = new Grid<>();
        assertThrows(IndexOutOfBoundsException.class, () -> grid.getElementAtCoords(-1, -1));
        // on empty, even 0,0 is out of bounds
        assertThrows(IndexOutOfBoundsException.class, () -> grid.getElementAtCoords(0, 0));

        grid.setElementAtCoords(0,0, "");
        assertThrows(IndexOutOfBoundsException.class, () -> grid.getElementAtCoords(0, -1));
        assertThrows(IndexOutOfBoundsException.class, () -> grid.getElementAtCoords(-1, 0));
        assertThrows(IndexOutOfBoundsException.class, () -> grid.getElementAtCoords(0, 1));
        assertThrows(IndexOutOfBoundsException.class, () -> grid.getElementAtCoords(1, 0));
        assertThrows(IndexOutOfBoundsException.class, () -> grid.getElementAtCoords(1, 1));
        assertDoesNotThrow(() -> grid.getElementAtCoords(0, 0));
    }

    @Test
    void setElementAtCoordsThrowsIffOutOfBoundsCoords() {
        Grid<String> grid = new Grid<>();
        assertThrows(IndexOutOfBoundsException.class, () -> grid.setElementAtCoords(-1, -1, ""));
        assertThrows(IndexOutOfBoundsException.class, () -> grid.setElementAtCoords(0, -1, ""));
        assertThrows(IndexOutOfBoundsException.class, () -> grid.setElementAtCoords(-1, 0, ""));

        assertDoesNotThrow(() -> grid.setElementAtCoords(0, 0, ""));
        assertDoesNotThrow(() -> grid.setElementAtCoords(10000, 10000, ""));
    }

    @Test
    void getNeighbors3x3Returns3x3GridFromEmptyGrid() {
        Grid<String> grid = new Grid<>();
        Grid<String> neighbors3x3 = grid.getNeighbors3x3(0, 0);

        assertEquals(2, neighbors3x3.getMaxX());
        assertEquals(2, neighbors3x3.getMaxY());
        for (int y = 0; y < 2; y++) {
            for (int x = 0; x < 2; x++) {
                final int test_x = x;
                final int test_y = y;

                assertDoesNotThrow(() -> neighbors3x3.getElementAtCoords(test_x, test_y));
            }
        }
    }

    @Test
    void getNeighbors3x3Returns3x3GridFromBigGrid() {
        Grid<Integer> grid = ascendingSquareGrid(99);
        Grid<Integer> neighbors3x3 = grid.getNeighbors3x3(0, 0);

        assertEquals(2, neighbors3x3.getMaxX());
        assertEquals(2, neighbors3x3.getMaxY());
        for (int y = 0; y < 2; y++) {
            for (int x = 0; x < 2; x++) {
                final int test_x = x;
                final int test_y = y;

                assertDoesNotThrow(() -> neighbors3x3.getElementAtCoords(test_x, test_y));
            }
        }
    }

    @Test
    void getNeighbors3x3HasOriginalValueAtCenter() {
        Grid<String> grid = new Grid<>();
        Grid<String> neighbors3x3 = grid.getNeighbors3x3(0, 0);

        assertEquals(2, neighbors3x3.getMaxX());
        assertEquals(2, neighbors3x3.getMaxY());
    }

    @Test
    void toListReturnsEmptyListFromEmptyGrid() {
        Grid<Integer> grid = new Grid<>();
        List<Integer> result = grid.toList();
        assertEquals(0, result.size());
    }

    @Test
    void toListReturnsListFromBigGrid() {
        Grid<Integer> grid = ascendingSquareGrid(99);
        List<Integer> result = grid.toList();
        List<Integer> expected = range(0, 99*99);

        assertArrayEquals(expected.toArray(), result.toArray());
    }
}
