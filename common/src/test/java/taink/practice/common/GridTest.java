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
    void getColumnThrowsIffOutOfBoundsColumn() {
        Grid<Integer> grid = new Grid<>();
        assertThrows(IndexOutOfBoundsException.class, () -> grid.getColumn(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> grid.getColumn(0));
        assertThrows(IndexOutOfBoundsException.class, () -> grid.getColumn(1));

        grid.setElementAtCoords(0,0, 1);
        assertThrows(IndexOutOfBoundsException.class, () -> grid.getColumn(-1));
        assertDoesNotThrow(() -> grid.getColumn(0));
        assertThrows(IndexOutOfBoundsException.class, () -> grid.getColumn(1));

        grid.setElementAtCoords(42,0, 1);
        assertThrows(IndexOutOfBoundsException.class, () -> grid.getColumn(-1));
        assertDoesNotThrow(() -> grid.getColumn(0));
        assertDoesNotThrow(() -> grid.getColumn(1));
        assertThrows(IndexOutOfBoundsException.class, () -> grid.getColumn(69));
    }

    @Test
    void getRowThrowsIffOutOfBoundsRow() {
        Grid<Integer> grid = new Grid<>();
        assertThrows(IndexOutOfBoundsException.class, () -> grid.getRow(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> grid.getRow(0));
        assertThrows(IndexOutOfBoundsException.class, () -> grid.getRow(1));

        grid.setElementAtCoords(0,0, 1);
        assertThrows(IndexOutOfBoundsException.class, () -> grid.getRow(-1));
        assertDoesNotThrow(() -> grid.getRow(0));
        assertThrows(IndexOutOfBoundsException.class, () -> grid.getRow(1));

        grid.setElementAtCoords(0,42, 1);
        assertThrows(IndexOutOfBoundsException.class, () -> grid.getRow(-1));
        assertDoesNotThrow(() -> grid.getRow(0));
        assertDoesNotThrow(() -> grid.getRow(1));
        assertThrows(IndexOutOfBoundsException.class, () -> grid.getRow(69));
    }

    @Test
    void getColumnReturnsNullForEmptyRow() {
        Grid<Integer> grid = new Grid<>();
        for (int i = 0; i < 10; i++) {
            if (i == 5) continue;
            grid.setElementAtCoords(0, i, i);
        }
        assertDoesNotThrow(() -> grid.getColumn(0));
        assertNull(grid.getColumn(0).get(5));
        assertNotNull(grid.getColumn(0).get(0));
        assertNotNull(grid.getColumn(0).get(3));
        assertNotNull(grid.getColumn(0).get(7));
        assertNotNull(grid.getColumn(0).get(8));
    }

    @Test
    void getRowReturnsNullForEmptyColumn() {
        Grid<Integer> grid = new Grid<>();
        for (int i = 0; i < 10; i++) {
            if (i == 5) continue;
            grid.setElementAtCoords(i, 0, i);
        }
        assertDoesNotThrow(() -> grid.getRow(0));
        assertNull(grid.getRow(0).get(5));
        assertNotNull(grid.getRow(0).get(0));
        assertNotNull(grid.getRow(0).get(3));
        assertNotNull(grid.getRow(0).get(7));
        assertNotNull(grid.getRow(0).get(8));
    }

    @Test
    void getColumnReturnsListUpToMaxX() {
        Grid<Integer> grid = ascendingSquareGrid(99);
        assertDoesNotThrow(() -> grid.getColumn(42));
        assertDoesNotThrow(() -> grid.getColumn(69));
        assertEquals(99, grid.getColumn(42).size());
    }

    @Test
    void getRowReturnsListUpToMaxY() {
        Grid<Integer> grid = ascendingSquareGrid(99);
        assertDoesNotThrow(() -> grid.getRow(42));
        assertDoesNotThrow(() -> grid.getRow(69));
        assertEquals(99, grid.getRow(42).size());
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
