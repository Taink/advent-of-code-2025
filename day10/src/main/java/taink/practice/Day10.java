package taink.practice;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day10 {
    static void main() {
//        Path path = Paths.get("src/main/resources/input.txt");
        Path path = Paths.get("src/test/resources/small_input.txt");
        List<String> input;
        try {
            input = Files.readAllLines(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        long totalPresses = 0;
        List<FactoryMachine> machines = List.copyOf(input.stream().map(FactoryMachine::new).toList());
        for (int i = 0; i < machines.size(); i++) {
            int buttonPressesToSolve = solveMachine(machines.get(i));
            IO.println(buttonPressesToSolve + " buttons pressed to solve machine #" + (i + 1));
            totalPresses += buttonPressesToSolve;
        }
        IO.println("Sum of presses: "+totalPresses);
    }

    private static int solveMachine(FactoryMachine machine) {
        List<Set<Integer>> buttonsLeft = new ArrayList<>(machine.buttons);
        while (!buttonsLeft.isEmpty() && !machine.isSolved()) {
            Set<Integer> idealButton = getIdealButton(machine);

            int closestMatchIndex = indexOfClosestMatch(idealButton, buttonsLeft);
            buttonsLeft.remove(closestMatchIndex);
            machine.pressButtonAt(closestMatchIndex);
        }
        if (!machine.isSolved()) {
            return -1;
        }
        return machine.getTotalButtonPresses();
    }

    private static int indexOfClosestMatch(Set<Integer> target, List<Set<Integer>> candidates) {
        assert !candidates.isEmpty();

        int candidateIndex = 0;
        double bestJaccard = jaccard(candidates.getFirst(), target);

        for (int i = 1; i < candidates.size(); i++) {
            double jaccard = jaccard(candidates.get(i), target);
            if (jaccard > bestJaccard) {
                bestJaccard = jaccard;
                candidateIndex = i;
            }
        }

        return candidateIndex;
    }

    private static double jaccard(Set<Integer> a, Set<Integer> b) {
        assert !a.isEmpty() && !b.isEmpty();

        Set<Integer> union = new HashSet<>(a);
        union.addAll(b);

        long intersect = a.stream().filter(b::contains).count();
        return (double) intersect / (double) union.size();
    }

    private static Set<Integer> getIdealButton(FactoryMachine machine) {
        Set<Integer> targetActivatedLights = new HashSet<>();
        Set<Integer> currentActivatedLights = new HashSet<>();
        char[] targetLights = machine.targetIndicatorLights.toCharArray();
        char[] currentLights = machine.getCurrentLightState().toCharArray();
        for (int i = 0; i < targetLights.length; i++) {
            if (targetLights[i] == FactoryMachine.LIGHT_ON) {
                targetActivatedLights.add(i);
            }
        }
        for (int i = 0; i < currentLights.length; i++) {
            if (currentLights[i] == FactoryMachine.LIGHT_ON) {
                currentActivatedLights.add(i);
            }
        }
        targetActivatedLights.removeAll(currentActivatedLights);

        return targetActivatedLights;
    }
}
