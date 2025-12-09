package taink.practice;

import taink.practice.common.Pair;

public record Rect(Pair<Integer, Integer> start, Pair<Integer, Integer> end) {
    public int minX() {
        return Math.min(start.x(), end.x());
    }
    public int maxX() {
        return Math.max(start.x(), end.x());
    }
    public int minY() {
        return Math.min(start.y(), end.y());
    }
    public int maxY() {
        return Math.max(start.y(), end.y());
    }
    public boolean intersects(Rect rect) {
        return (rect.minX() < this.maxX() && rect.maxX() > this.minX() && rect.minY() < this.maxY() && rect.maxY() > this.minY());
    }
    public long area() {
        long rectangleLength = Math.abs(end.x() - start.x()) + 1;
        long rectangleWidth = Math.abs(end.y() - start.y()) + 1;
        assert rectangleWidth > 0;
        assert rectangleLength > 0;
        return rectangleLength * rectangleWidth;
    }
}
