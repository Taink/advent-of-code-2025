package taink.practice;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import static org.junit.jupiter.api.Assertions.*;

class IdAnalyzerTest {

    @Test
    void numbersFromRange() {
        List<String> numbers = IdAnalyzer.numbersFromRange(1188511880, 1188511890);
        List<String> expected_numbers = List.of("1188511880", "1188511881", "1188511882", "1188511883", "1188511884", "1188511885", "1188511886", "1188511887", "1188511888", "1188511889", "1188511890");
        assertLinesMatch(numbers, expected_numbers);

        List<String> numbers2 = IdAnalyzer.numbersFromRange(3, 3);
        List<String> expected_numbers2 = List.of("3");
        assertLinesMatch(numbers2, expected_numbers2);

        List<String> numbers3 = IdAnalyzer.numbersFromRange(0, 3);
        List<String> expected_numbers3 = List.of("0", "1", "2", "3");
        assertLinesMatch(numbers3, expected_numbers3);
    }

    @Test
    void isSimpleInvalidId() {
        IdAnalyzer idAnalyzer = new IdAnalyzer();
        assertTrue(idAnalyzer.isSimpleInvalidId("55"));
        assertTrue(idAnalyzer.isSimpleInvalidId("6464"));
        assertTrue(idAnalyzer.isSimpleInvalidId("123123"));
        assertFalse(idAnalyzer.isSimpleInvalidId("101"));
        assertFalse(idAnalyzer.isSimpleInvalidId("65465489489"));
        assertFalse(idAnalyzer.isSimpleInvalidId("189891896"));
        assertFalse(idAnalyzer.isSimpleInvalidId("235234"));
        assertFalse(idAnalyzer.isSimpleInvalidId("5123123"));
        assertFalse(idAnalyzer.isSimpleInvalidId("5123123"));
        assertFalse(idAnalyzer.isSimpleInvalidId("1122334455"));
    }

    @Test
    void isCompleteInvalidId() {
        IdAnalyzer idAnalyzer = new IdAnalyzer();
        assertTrue(idAnalyzer.isCompleteInvalidId("55"));
        assertTrue(idAnalyzer.isCompleteInvalidId("6464"));
        assertTrue(idAnalyzer.isCompleteInvalidId("123123"));
        assertFalse(idAnalyzer.isCompleteInvalidId("101"));
        assertFalse(idAnalyzer.isCompleteInvalidId("65465489489"));
        assertFalse(idAnalyzer.isCompleteInvalidId("189891896"));
        assertFalse(idAnalyzer.isCompleteInvalidId("235234"));
        assertFalse(idAnalyzer.isCompleteInvalidId("5123123"));
        assertFalse(idAnalyzer.isCompleteInvalidId("5123123"));
        assertFalse(idAnalyzer.isCompleteInvalidId("1122334455"));

        assertTrue(idAnalyzer.isCompleteInvalidId("12341234"));
        assertTrue(idAnalyzer.isCompleteInvalidId("123123123"));
        assertTrue(idAnalyzer.isCompleteInvalidId("1212121212"));
        assertTrue(idAnalyzer.isCompleteInvalidId("1111111"));
    }

    @Test
    void parseSmallInput() throws IOException {
        Path path = Paths.get("src/test/resources/small_input.txt");
        StringTokenizer ranges = new StringTokenizer(Files.readString(path), " ,\t\r\n\f");
        IdAnalyzer idAnalyzer = new IdAnalyzer();
        List<String> expectedSimpleInvalidIds = List.of("11", "22", "99", "1010", "1188511885", "222222", "446446", "38593859");
        List<String> expectedCompleteInvalidIds = List.of("11", "22", "99", "111", "999", "1010", "1188511885", "222222", "446446", "38593859", "565656", "824824824", "2121212121");
        List<String> simpleInvalidIds = new ArrayList<>();
        List<String> completeInvalidIds = new ArrayList<>();

        while (ranges.hasMoreTokens()) {
            String rangeString = ranges.nextToken();
            String[] extremes = rangeString.split("-");
            int start = Integer.parseInt(extremes[0]);
            int end = Integer.parseInt(extremes[1]);

            simpleInvalidIds.addAll(idAnalyzer.simpleInvalidIdsInRange(start, end));
            completeInvalidIds.addAll(idAnalyzer.completeInvalidIdsInRange(start, end));
        }
        assertLinesMatch(expectedSimpleInvalidIds, simpleInvalidIds);
        assertLinesMatch(expectedCompleteInvalidIds, completeInvalidIds);

        int sumOfSimpleInvalidIds = simpleInvalidIds.stream().mapToInt(Integer::parseInt).sum();
        assertEquals(1227775554, sumOfSimpleInvalidIds);
        long sumOfCompleteInvalidIds = completeInvalidIds.stream().mapToLong(Long::parseLong).sum();
        assertEquals(4174379265L, sumOfCompleteInvalidIds);
    }
}