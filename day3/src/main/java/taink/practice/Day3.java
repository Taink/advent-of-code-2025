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
        List<BatteryBank> batteryBanks = batteries.stream().map(BatteryBank::new).toList();
        int maxJoltage = batteryBanks.stream().mapToInt(BatteryBank::getMaxJoltage).sum();
        IO.println("Maximum Joltage: " + maxJoltage);
    }
}
