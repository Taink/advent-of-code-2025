package taink.practice;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class BatteryBankTest {

    @Test
    void ascendingBattery() {
        BatteryBank batteryBank = new BatteryBank("234234234234278");
        assertEquals(13, batteryBank.getFirstBatteryPos());
        assertEquals(14, batteryBank.getSecondBatteryPos());
        assertEquals(78, batteryBank.getMaxJoltage());
    }

    @Test
    void descendingBattery() {
        BatteryBank batteryBank = new BatteryBank("987654321111111");
        assertEquals(0, batteryBank.getFirstBatteryPos());
        assertEquals(1, batteryBank.getSecondBatteryPos());
        assertEquals(98, batteryBank.getMaxJoltage());
    }

    @Test
    void spacedBattery() {
        BatteryBank batteryBank = new BatteryBank("811111111111119");
        assertEquals(0, batteryBank.getFirstBatteryPos());
        assertEquals(14, batteryBank.getSecondBatteryPos());
        assertEquals(89, batteryBank.getMaxJoltage());
    }

    @Test
    void randomBattery() {
        BatteryBank batteryBank = new BatteryBank("818181911112111");
        assertEquals(6, batteryBank.getFirstBatteryPos());
        assertEquals(11, batteryBank.getSecondBatteryPos());
        assertEquals(92, batteryBank.getMaxJoltage());
    }

    @Test
    void parseSmallInput() throws IOException {
        Path path = Paths.get("src/test/resources/small_input.txt");
        List<String> batteries = Files.readAllLines(path);
        List<BatteryBank> batteryBanks = batteries.stream().map(BatteryBank::new).toList();
        List<Integer> maxJoltages = batteryBanks.stream().map(BatteryBank::getMaxJoltage).toList();
        assertArrayEquals(new Integer[] {98, 89, 78, 92}, maxJoltages.toArray());
        assertEquals(357, maxJoltages.stream().reduce(0, Integer::sum));
    }
}