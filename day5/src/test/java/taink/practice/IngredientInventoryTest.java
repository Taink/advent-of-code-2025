package taink.practice;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IngredientInventoryTest {
    @Test
    void simpleRangesAndIds() {
        List<String> input = List.of("1-5", "7-10", "", "4", "5", "6", "7", "8");
        IngredientInventory inventory = new IngredientInventory(input);
        assertFalse(inventory.idIsFresh(6));
        assertTrue(inventory.idIsFresh(4));
        assertTrue(inventory.idIsFresh(5));
        assertTrue(inventory.idIsFresh(7));
        assertTrue(inventory.idIsFresh(8));
    }

    @Test
    void rangesAreInclusive() {
        List<String> input = List.of("1-2", "", "1", "2");
        IngredientInventory inventory = new IngredientInventory(input);
        assertTrue(inventory.idIsFresh(1));
        assertTrue(inventory.idIsFresh(2));

        assertFalse(inventory.idIsFresh(42069));
    }

    @Test
    void testWithSmallInput() {
        Path path = Paths.get("src/test/resources/small_input.txt");
        List<String> input;
        try {
            input = Files.readAllLines(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        IngredientInventory inventory =  new IngredientInventory(input);
        assertFalse(inventory.idIsFresh(1));
        assertTrue(inventory.idIsFresh(5));
        assertFalse(inventory.idIsFresh(8));
        assertTrue(inventory.idIsFresh(11));
        assertTrue(inventory.idIsFresh(17));
        assertFalse(inventory.idIsFresh(32));

        assertEquals(3, inventory.getFreshIngredientCount());
    }
}