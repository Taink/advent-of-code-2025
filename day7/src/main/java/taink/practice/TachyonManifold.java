package taink.practice;

import taink.practice.common.Grid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    public long computeDistinctBeams() {
        // beamCol, distinctBeams
        Map<Integer, Long> beamCountByCol = new HashMap<>();
        int startX = 0;
        while (this.state.getElementAtCoords(startX, 0) != START) {
            startX++;
        }
        beamCountByCol.put(startX, 1L);
        for (int y = 1; y < this.state.getMaxY(); y++) {
            Set<Integer> colsThatHaveBeams = beamCountByCol.keySet();
            for (int colToInvestigate : colsThatHaveBeams.stream().toList()) {
                if (this.state.getElementAtCoords(colToInvestigate, y) == SPLITTER) {
                    int leftX = colToInvestigate - 1;
                    if (leftX >= 0 && this.state.getElementAtCoords(leftX, y) == BEAM) {
                        long leftBeamCount = beamCountByCol.get(leftX) != null ? beamCountByCol.get(leftX) : 0L;
                        beamCountByCol.put(leftX, leftBeamCount + beamCountByCol.get(colToInvestigate));
                    }
                    int rightX = colToInvestigate + 1;
                    if (rightX <= this.state.getMaxX() && this.state.getElementAtCoords(rightX, y) == BEAM) {
                        long rightBeamCount = beamCountByCol.get(rightX) != null ? beamCountByCol.get(rightX) : 0L;
                        beamCountByCol.put(rightX, rightBeamCount + beamCountByCol.get(colToInvestigate));
                    }
                    beamCountByCol.remove(colToInvestigate); // the beam stops when we encounter a splitter
                }
            }
        }

        return beamCountByCol.values().stream().reduce(0L, Long::sum);
    }

    public int getSplitCount() {
        return this.splitCounter;
    }

    @Override
    public String toString() {
        return this.state.toString();
    }
}
