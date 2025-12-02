package taink.practice;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IdAnalyzer {
    private final Pattern numberRepeatedTwice;
    private final Pattern numberRepeatedTwiceOrMore;

    public IdAnalyzer() {
        this.numberRepeatedTwice = Pattern.compile("(\\d+)\\1");
        this.numberRepeatedTwiceOrMore = Pattern.compile("(\\d+)\\1+");
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

    public boolean isSimpleInvalidId(String id) {
        Matcher m = numberRepeatedTwice.matcher(id);
        return m.matches();
    }

    public List<String> simpleInvalidIdsInRange(int start, int end) {
        List<String> numbers = numbersFromRange(start, end);
        return numbers.stream().filter(this::isSimpleInvalidId).toList();
    }

    public List<String> simpleInvalidIdsInRange(long start, long end) {
        List<String> numbers = numbersFromRange(start, end);
        return numbers.stream().filter(this::isSimpleInvalidId).toList();
    }

    public boolean isCompleteInvalidId(String id) {
        Matcher m = numberRepeatedTwiceOrMore.matcher(id);
        return m.matches();
    }

    public List<String> completeInvalidIdsInRange(int start, int end) {
        List<String> numbers = numbersFromRange(start, end);
        return numbers.stream().filter(this::isCompleteInvalidId).toList();
    }

    public List<String> completeInvalidIdsInRange(long start, long end) {
        List<String> numbers = numbersFromRange(start, end);
        return numbers.stream().filter(this::isCompleteInvalidId).toList();
    }
}
