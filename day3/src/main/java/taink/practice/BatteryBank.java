package taink.practice;

public class BatteryBank {
    private final String batteries;
    private final int targetBatteryCount;
    private int maxJoltage;

    public BatteryBank(String batteries, int targetBatteryCount) {
        this.batteries = batteries;
        this.targetBatteryCount = targetBatteryCount;
        this.computeLargestJoltage();
    }

    private int getBatteryRatingAtPos(String bank, int pos) {
        return bank.charAt(pos) - '0';
    }

    private int getLargestRatingPosInBank(String bank) {
        int maxRating = 0;
        int posCandidate = -1;

        for (int pos = 0; pos < bank.length(); pos++) {
            if (getBatteryRatingAtPos(bank, pos) > maxRating) {
                maxRating = bank.charAt(pos) - '0';
                posCandidate = pos;
            }
        }

        return posCandidate;
    }

    private void computeLargestJoltage() {
        int previousBatteryPos = -1;
        for (int remainingBatteries = this.targetBatteryCount; remainingBatteries > 0; remainingBatteries--) {
            int minPos = previousBatteryPos + 1;
            String batteryCandidates = this.batteries.substring(minPos, this.batteries.length() - remainingBatteries + 1);
            int batteryPos = getLargestRatingPosInBank(batteryCandidates) + minPos;

            this.maxJoltage += getBatteryRatingAtPos(this.batteries, batteryPos) * Math.powExact(10, remainingBatteries - 1);
            previousBatteryPos = batteryPos;
        }
    }

    public int getMaxJoltage() {
        return maxJoltage;
    }
}
