package taink.practice;

import java.util.*;

public class FactoryMachine {
    public static final char LIGHT_ON = '#';
    public static final char LIGHT_OFF = '.';

    final String targetIndicatorLights;
    private char[] currentLightState;
    public final List<Set<Integer>> buttons;
    private final List<Integer> joltageRequirements;
    private int totalButtonPresses = 0;

    public FactoryMachine(String input) {
        String[] config = input.split(" ");
        int i = 0;
        this.targetIndicatorLights = config[i].substring(1, config[i].length() - 1);
        this.currentLightState = targetIndicatorLights.replace(LIGHT_ON, LIGHT_OFF).toCharArray();
        i++;

        var buttonWiringList = new ArrayList<Set<Integer>>();
        for (; i < config.length - 1; i++) {
            String[] buttonWiringSchematic = config[i].substring(1, config[i].length() - 1).split(",");
            Set<Integer> wiring = Set.copyOf(Arrays.stream(buttonWiringSchematic).map(Integer::parseUnsignedInt).toList());
            buttonWiringList.add(wiring);
        }
        this.buttons = List.copyOf(buttonWiringList);

        String[] joltageRequirements = config[i].substring(1, config[i].length() - 1).split(",");
        this.joltageRequirements = Arrays.stream(joltageRequirements).map(Integer::parseUnsignedInt).toList();

        assert this.joltageRequirements.size() == this.targetIndicatorLights.length();
    }

    private void toggleLightAt(int lightIndex) {
        assert lightIndex >= 0 && lightIndex < currentLightState.length;

        char indicatorLightAtIndex = this.currentLightState[lightIndex];
        this.currentLightState[lightIndex] = indicatorLightAtIndex == LIGHT_ON ? LIGHT_OFF : LIGHT_ON;
    }

    public void pressButtonAt(int buttonIndex) {
        Set<Integer> toggledLights = this.buttons.get(buttonIndex);
        for (int toggledLight : toggledLights) {
            this.toggleLightAt(toggledLight);
        }
        this.totalButtonPresses++;
    }

    public String getCurrentLightState() {
        return String.copyValueOf(currentLightState);
    }

    public String simulatePressingButtonAt(int buttonIndex) {
        Set<Integer> toggledLights = this.buttons.get(buttonIndex);
        for (int toggledLight : toggledLights) {
            this.toggleLightAt(toggledLight);
        }
        String result = this.getCurrentLightState();
        for (int toggledLight : toggledLights) {
            this.toggleLightAt(toggledLight);
        }
        return result;
    }

    public int getTotalButtonPresses() {
        return totalButtonPresses;
    }

    public boolean isSolved() {
        return this.targetIndicatorLights.contentEquals(String.copyValueOf(currentLightState));
    }
}
