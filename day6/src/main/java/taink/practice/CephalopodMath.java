package taink.practice;

import taink.practice.common.Grid;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class CephalopodMath {
    private final List<Character> operations;
    private final List<List<Integer>> worksheet;
    private static final char OPERATOR_ADD = '+';
    private static final char OPERATOR_MULTIPLY = '*';

    public CephalopodMath(List<String> worksheetInput) {
        this.operations = new ArrayList<>();
        this.worksheet = new ArrayList<>();

        String[] inputOperations = worksheetInput.getLast().trim().split("\\s+");
        parseOperators(inputOperations);

        Grid<Character> inputNumbers;
        inputNumbers = new Grid<>();
        for (int y = 0; y < worksheetInput.size() - 1; y++) {
            char[] chars = worksheetInput.get(y).toCharArray();
            for (int x = 0; x < chars.length; x++) {
                inputNumbers.setElementAtCoords(x, y, chars[x]);
            }
        }

        List<Integer> cephalopodNumbers = new ArrayList<>();

        // Cephalopod numbers are read right-to-left in columns
        for (int colX = inputNumbers.getMaxX(); colX >= 0; colX--) {
            List<Character> col = inputNumbers.getColumn(colX);
            if (col.stream().allMatch(c -> Objects.isNull(c) || Character.isSpaceChar(c))) {
                // this is a spacing column; we should switch operation

                worksheet.addFirst(cephalopodNumbers);
                cephalopodNumbers = new ArrayList<>();
                continue;
            }

            StringBuilder cephalopodNumber = new StringBuilder();
            // Each number is in its own column, with the most significant digit at the top
            for (int colY = 0; colY <= inputNumbers.getMaxY(); colY++) {
                Character c = inputNumbers.getElementAtCoords(colX, colY);
                char digit = Objects.isNull(c) ? ' ' : c;

                if (Character.isDigit(digit)) {
                    cephalopodNumber.append(digit);
                }
            }
            cephalopodNumbers.addFirst(Integer.parseInt(cephalopodNumber.toString()));
        }
        worksheet.addFirst(cephalopodNumbers);
    }

    private void parseOperators(String[] input) {
        for (String operation : input) {
            char operator = operation.toCharArray()[0];
            if (operator == OPERATOR_ADD || operator == OPERATOR_MULTIPLY) {
                this.operations.add(operator);
            } else {
                throw new IllegalArgumentException("Invalid operation: " + operation);
            }
        }
    }

    public long getResultOfColumn(int colIndex) {
        LongStream col = this.worksheet.get(colIndex).stream().mapToLong(Integer::longValue);
        return switch (this.operations.get(colIndex)) {
            case OPERATOR_ADD -> col.sum();
            case OPERATOR_MULTIPLY -> col.reduce(1L, (a, b) -> a * b);
            default -> throw new IllegalArgumentException("Invalid operation: " + this.operations.get(colIndex));
        };
    }

    public long getSumOfResults() {
        long sum = 0;
        for (int i = 0; i < this.worksheet.size(); i++) {
            sum += this.getResultOfColumn(i);
        }
        return sum;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        int col = 0;
        for (Character operation : this.operations) {
            List<Integer> numbers = this.worksheet.get(col);
            result.append(numbers.getFirst());
            String line = numbers.stream().skip(1).map(n -> " "+operation+" "+n).collect(Collectors.joining());
            result.append(line);
            result.append("\n");
            col++;
        }
        return result.toString();
    }
}
