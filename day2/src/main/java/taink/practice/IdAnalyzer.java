package taink.practice;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IdAnalyzer {
    private final Pattern repeatedNumber;

    public IdAnalyzer() {
        this.repeatedNumber = Pattern.compile("(\\d+)\\1");
    }

    /**
     * Gives a list of strings representing numbers between {start} and {end}, inclusive.
     *
     * @param start minimum inclusive value in the range
     * @param end maximum inclusive value in the range
     * @return an immutable list of Strings representing each number in the range
     */
    public static List<String> numbersFromRange(int start, int end) {
        List<String> result = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            result.add(String.valueOf(i));
        }
        return List.copyOf(result);
    }

    /**
     * Gives a list of strings representing numbers between {start} and {end}, inclusive.
     *
     * @param start minimum inclusive value in the range
     * @param end maximum inclusive value in the range
     * @return an immutable list of Strings representing each number in the range
     */
    public static List<String> numbersFromRange(long start, long end) {
        List<String> result = new ArrayList<>();
        for (long i = start; i <= end; i++) {
            result.add(String.valueOf(i));
        }
        return List.copyOf(result);
    }

    public boolean isInvalidId(String id) {
        Matcher m = repeatedNumber.matcher(id);
        return m.matches();
    }

    public List<String> invalidIdsInRange(int start, int end) {
        List<String> numbers = numbersFromRange(start, end);
        return numbers.stream().filter(this::isInvalidId).toList();
    }

    public List<String> invalidIdsInRange(long start, long end) {
        List<String> numbers = numbersFromRange(start, end);
        return numbers.stream().filter(this::isInvalidId).toList();
    }
}
