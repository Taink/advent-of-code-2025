package taink.practice;

import taink.practice.common.Grid;

import java.util.List;

public class TachyonManifold {
    public static final char EMPTY_SPACE = '.';
    public static final char BEAM = '|';
    public static final char SPLITTER = '^';
    public static final char START = 'S';

    private final Grid<Character> state;
    private int curRowIndex;
    private int splitCounter;

    public TachyonManifold(List<String> initialState) {
        this.state = new Grid<>();
        this.curRowIndex = 0;
        this.splitCounter = 0;

        for (int y = 0; y < initialState.size(); y++) {
            String line = initialState.get(y);

            char[] split = line.toCharArray();
            for (int x = 0; x < split.length; x++) {
                this.state.setElementAtCoords(x, y, split[x]);
            }
        }
    }

    public boolean isFullyPropagated() {
        return this.curRowIndex >= this.state.getMaxY();
    }

    public void propagateBeam() {
        int prevRowIndex = this.curRowIndex;
        this.curRowIndex++;

        List<Character> prevRow = this.state.getRow(prevRowIndex);
        for (int x = 0; x < prevRow.size(); x++) {
            char cell =  prevRow.get(x);

            if (cell != BEAM && cell != START) {
                continue;
            }

            switch (this.state.getElementAtCoords(x, curRowIndex)) {
                case EMPTY_SPACE:
                    this.state.setElementAtCoords(x, curRowIndex, BEAM);
                    break;
                case SPLITTER:
                    this.splitCounter++;

                    int leftX = x - 1;
                    int rightX = x + 1;
                    if (leftX >= 0 && this.state.getElementAtCoords(leftX, curRowIndex) == EMPTY_SPACE) {
                        this.state.setElementAtCoords(leftX, curRowIndex, BEAM);
                    }
                    if (rightX <= this.state.getMaxX() && this.state.getElementAtCoords(rightX, curRowIndex) == EMPTY_SPACE) {
                        this.state.setElementAtCoords(rightX, curRowIndex, BEAM);
                    }
                    break;
            }
        }
    }

    public int getSplitCount() {
        return this.splitCounter;
    }

    @Override
    public String toString() {
        return this.state.toString();
    }
}
