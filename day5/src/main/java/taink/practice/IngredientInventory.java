package taink.practice;

import taink.practice.common.Pair;

import java.util.ArrayList;
import java.util.List;

public class IngredientInventory {
    private final List<Pair<Long, Long>> freshRanges;
    private final List<Long> ingredients;

    public IngredientInventory(List<String> inputLines) {
        this.freshRanges = new ArrayList<>();
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

}
