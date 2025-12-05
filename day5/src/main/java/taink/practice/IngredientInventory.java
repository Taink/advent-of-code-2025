package taink.practice;

import taink.practice.common.Pair;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class IngredientInventory {
    private final List<Pair<Long, Long>> originalFreshRanges;
    private List<Pair<Long, Long>> freshRanges;
    private final List<Long> ingredients;

    public IngredientInventory(List<String> inputLines) {
        this.originalFreshRanges = new ArrayList<>();
        this.freshRanges = originalFreshRanges;
        this.ingredients = new ArrayList<>();

        boolean parsingFreshIdRange = true;
        for(String s : inputLines) {
            if (s.isEmpty()) {
                parsingFreshIdRange = false;
                continue;
            }

            if (parsingFreshIdRange) {
                String[] split = s.split("-");
                long min = Long.parseLong(split[0]);
                long max = Long.parseLong(split[1]);
                this.freshRanges.add(new Pair<>(min, max));
            } else {
                this.ingredients.add(Long.parseLong(s));
            }
        }

        this.freshRanges = deduplicateRanges(originalFreshRanges);
    }

    private static List<Pair<Long, Long>> deduplicateRanges(List<Pair<Long, Long>> originalRanges) {
        List<Pair<Long, Long>> result = new ArrayList<>();
        List<Pair<Long, Long>> sortedRangesByStartAsc = originalRanges.stream().sorted(Comparator.comparingLong(Pair::x)).toList();

        for (Pair<Long, Long> range : sortedRangesByStartAsc) {
            if (result.isEmpty() || range.x() > result.getLast().y() + 1) {
                result.add(range); // disjoint interval
            } else {
                Pair<Long, Long> last = result.getLast();
                long newMax = Math.max(last.y(), range.y());
                result.set(result.size() - 1, new Pair<>(last.x(), newMax));
            }
        }

        return result;
    }

    /**
     * Ranges are inclusive
     */
    public static boolean idIsInRange(long id, Pair<Long, Long> range) {
        return idIsInRange(id, range.x(), range.y());
    }

    /**
     * Ranges are inclusive
     */
    public static boolean idIsInRange(long id, long rangeMin, long rangeMax) {
        return rangeMin <= id && id <= rangeMax;
    }

    public boolean idIsFresh(long id) {
        return this.freshRanges.stream().anyMatch(range -> idIsInRange(id, range));
    }

    public long getFreshIngredientCount() {
        return this.ingredients.stream().filter(this::idIsFresh).count();
    }

    public long getFreshIdCandidateCount() {
        long total = 0;
        for (Pair<Long, Long> range : this.freshRanges) {
            long freshCount = range.y() - range.x() + 1;
            total += freshCount;
        }
        return total;
    }
}
