package taink.practice.common;

import java.util.*;

public class Grid<T> {
    private final Map<Pair<Integer, Integer>, T> elements;
    private int maxX;
    private int maxY;

    public Grid() {
        this.elements = new HashMap<>();
        this.maxX = Integer.MIN_VALUE;
        this.maxY = Integer.MIN_VALUE;
    }

    /**
     * The maximum X coordinate value in this grid, as set by {@link #setElementAtCoords}.
     * Defaults to {@link Integer#MIN_VALUE} on an empty grid.
     */
    public int getMaxX() {
        return this.maxX;
    }
    /**
     * The maximum Y coordinate value in this grid, as set by {@link #setElementAtCoords}.
     * Defaults to {@link Integer#MIN_VALUE} on an empty grid.
     */
    public int getMaxY() {
        return this.maxY;
    }

    /**
     * Puts the provided element at the provided [x;y] coordinates.
     * @throws IndexOutOfBoundsException if the coordinates are below 0
     */
    public void setElementAtCoords(int x, int y, T el) throws IndexOutOfBoundsException {
        if (x < 0 || y < 0) {
            throw new IndexOutOfBoundsException("Coordinates below 0 are invalid");
        }
        this.maxX = Math.max(this.maxX, x);
        this.maxY = Math.max(this.maxY, y);
        this.elements.put(new Pair<>(x, y), el);
    }

    /**
     * Retrieves the element at [x;y]. Returns {@code null} if it doesn't exist.
     * @return the element at [x;y]
     * @throws IndexOutOfBoundsException if the coordinates are below 0 or above the current max values of X and Y
     */
    public T getElementAtCoords(int x, int y) throws IndexOutOfBoundsException {
        if (x < 0 || y < 0 || x > this.maxX || y > this.maxY) {
            throw new IndexOutOfBoundsException(String.format("Coordinates [%d;%d] ([x;y]) out of bounds",  x, y));
        }
        return this.elements.get(new Pair<>(x, y));
    }

    /**
     * @param x the X coordinate of the element
     * @param y the Y coordinate of the element
     * @return a 3x3 grid of the elements around the coords x and y
     */
    public Grid<T> getNeighbors3x3(int x, int y) {
        assert x >= 0 && y >= 0;
        Grid<T> result = new Grid<>();

        for (int neighbor_y = -1; neighbor_y <= 1; neighbor_y++) {
            for (int neighbor_x = -1; neighbor_x <= 1; neighbor_x++) {
                try {
                    result.setElementAtCoords(neighbor_x + 1, neighbor_y + 1, this.getElementAtCoords(x + neighbor_x, y + neighbor_y));
                } catch (IndexOutOfBoundsException e) {
                    result.setElementAtCoords(neighbor_x + 1, neighbor_y + 1, null);
                }
            }
        }

        return result;
    }

    /**
     * Explores the grid from top left (0,0) to bottom right,
     * and returns an immutable List of the elements in that order
     * @return an immutable {@link List} of the elements in that order
     */
    public List<T> toList() {
        int maxX = getMaxX() + 1;
        int maxY = getMaxY() + 1;

        List<T> result = new ArrayList<>();
        for (int y = 0; y < maxY; y++) {
            for (int x = 0; x < maxX; x++) {
                result.add(getElementAtCoords(x, y));
            }
        }
        return Collections.unmodifiableList(result);
    }

    /**
     * Fetch elements in the specified column
     * @param x the X coordinate of the column
     * @return an unmodifiable list of the elements in the column
     * @throws IndexOutOfBoundsException if the specified column is below 0 or above maxX
     */
    public List<T> getColumn(int x) throws IndexOutOfBoundsException {
        List<T> result = new ArrayList<>();

        if (x < 0 || x > this.maxX) {
            throw new IndexOutOfBoundsException("Column "+x+" is out of bounds");
        }

        for (int y = 0; y <= maxY; y++) {
            result.add(getElementAtCoords(x, y));
        }

        return Collections.unmodifiableList(result);
    }

    /**
     * Fetch elements in the specified row
     * @param y the Y coordinate of the row
     * @return an unmodifiable list of the elements in the row
     * @throws IndexOutOfBoundsException if the specified row is below 0 or above maxY
     */
    public List<T> getRow(int y) throws IndexOutOfBoundsException {
        List<T> result = new ArrayList<>();

        if (y < 0 || y > this.maxY) {
            throw new IndexOutOfBoundsException("Row "+y+" is out of bounds");
        }

        for (int x = 0; x <= maxX; x++) {
            result.add(getElementAtCoords(x, y));
        }

        return Collections.unmodifiableList(result);
    }

    @Override
    public String toString() {
        List<T> elList = this.toList();
        List<String> serializedElements = elList.stream().map(el -> {
            String prefix = "\t";
            if (el instanceof Character) {
                prefix = "";
            }
            return String.format("%s%s", prefix, el != null ? el.toString() : "");
        }).toList();
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y <= this.maxY; y++) {
            for (int x = 0; x <= this.maxX; x++) {
                sb.append(serializedElements.get(y * (this.maxX + 1) + x));
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
