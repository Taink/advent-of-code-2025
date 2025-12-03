package taink.practice;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BatteryBankTest {

    @Test
    void ascendingBattery() {
        BatteryBank batteryBank2 = new BatteryBank("234234234234278", 2);
        assertEquals(78L, batteryBank2.getMaxJoltage());
        BatteryBank batteryBank12 = new BatteryBank("234234234234278", 12);
        assertEquals(434234234278L, batteryBank12.getMaxJoltage());
    }

    @Test
    void descendingBattery() {
        BatteryBank batteryBank2 = new BatteryBank("987654321111111", 2);
        assertEquals(98L, batteryBank2.getMaxJoltage());
        BatteryBank batteryBank12 = new BatteryBank("987654321111111", 12);
        assertEquals(987654321111L, batteryBank12.getMaxJoltage());
    }

    @Test
    void spacedBattery() {
        BatteryBank batteryBank2 = new BatteryBank("811111111111119", 2);
        assertEquals(89L, batteryBank2.getMaxJoltage());
        BatteryBank batteryBank12 = new BatteryBank("811111111111119", 12);
        assertEquals(811111111119L, batteryBank12.getMaxJoltage());
    }

    @Test
    void randomBattery() {
        BatteryBank batteryBank2 = new BatteryBank("818181911112111", 2);
        assertEquals(92L, batteryBank2.getMaxJoltage());
        BatteryBank batteryBank12 = new BatteryBank("818181911112111", 12);
        assertEquals(888911112111L, batteryBank12.getMaxJoltage());
    }

    @Test
    void parseSmallInput() throws IOException {
        Path path = Paths.get("src/test/resources/small_input.txt");
        List<String> batteries = Files.readAllLines(path);
        List<BatteryBank> batteryBanks2 = batteries.stream().map(s -> new BatteryBank(s, 2)).toList();
        List<Long> maxJoltages2 = batteryBanks2.stream().map(BatteryBank::getMaxJoltage).toList();
        assertArrayEquals(new Long[] {98L, 89L, 78L, 92L}, maxJoltages2.toArray());
        assertEquals(357, maxJoltages2.stream().reduce(0L, Long::sum));

        List<BatteryBank> batteryBanks12 = batteries.stream().map(s -> new BatteryBank(s, 12)).toList();
        List<Long> maxJoltages12 = batteryBanks12.stream().map(BatteryBank::getMaxJoltage).toList();
        assertArrayEquals(new Long[] {987654321111L, 811111111119L, 434234234278L, 888911112111L}, maxJoltages12.toArray());
        assertEquals(3121910778619L, maxJoltages12.stream().reduce(0L, Long::sum));
    }
}