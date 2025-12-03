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
        BatteryBank batteryBank = new BatteryBank("234234234234278", 2);
        assertEquals(78L, batteryBank.getMaxJoltage());
    }

    @Test
    void descendingBattery() {
        BatteryBank batteryBank = new BatteryBank("987654321111111", 2);
        assertEquals(98L, batteryBank.getMaxJoltage());
    }

    @Test
    void spacedBattery() {
        BatteryBank batteryBank = new BatteryBank("811111111111119", 2);
        assertEquals(89L, batteryBank.getMaxJoltage());
    }

    @Test
    void randomBattery() {
        BatteryBank batteryBank = new BatteryBank("818181911112111", 2);
        assertEquals(92L, batteryBank.getMaxJoltage());
    }

    @Test
    void parseSmallInput() throws IOException {
        Path path = Paths.get("src/test/resources/small_input.txt");
        List<String> batteries = Files.readAllLines(path);
        List<BatteryBank> batteryBanks2 = batteries.stream().map(s -> new BatteryBank(s, 2)).toList();
        List<Long> maxJoltages2 = batteryBanks2.stream().map(BatteryBank::getMaxJoltage).toList();
        assertArrayEquals(new Long[] {98L, 89L, 78L, 92L}, maxJoltages2.toArray());
        assertEquals(357, maxJoltages2.stream().reduce(0L, Long::sum));
    }
}