package taink.practice;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Day3 {
    static void main() {
        Path path = Paths.get("src/main/resources/input.txt");
        List<String> batteries;
        try {
            batteries = Files.readAllLines(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        List<BatteryBank> batteryBanks2 = batteries.stream().map(s -> new BatteryBank(s, 2)).toList();
        int maxJoltage2 = batteryBanks2.stream().mapToInt(BatteryBank::getMaxJoltage).sum();
        IO.println("Maximum Joltage with 2 batteries: " + maxJoltage2);
    }
}
