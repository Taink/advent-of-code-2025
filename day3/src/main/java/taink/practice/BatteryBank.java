package taink.practice;

public class BatteryBank {
    private final String batteries;
    private int firstBatteryPos;
    private int secondBatteryPos;
    private int maxJoltage;

    public BatteryBank(String batteries) {
        this.batteries = batteries;
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
        String firstBatteryCandidates = this.batteries.substring(0, this.batteries.length() - 1); // exclude last battery
        int firstBatteryPos = getLargestRatingPosInBank(firstBatteryCandidates);
        String secondBatteryCandidates = this.batteries.substring(firstBatteryPos + 1); // after first battery
        int secondBatteryPos = getLargestRatingPosInBank(secondBatteryCandidates) + firstBatteryPos + 1;

        this.firstBatteryPos = firstBatteryPos;
        this.secondBatteryPos = secondBatteryPos;

        this.maxJoltage = getBatteryRatingAtPos(this.batteries, firstBatteryPos) * 10
             + getBatteryRatingAtPos(this.batteries, secondBatteryPos);
    }

    public int getMaxJoltage() {
        return maxJoltage;
    }

    public int getSecondBatteryPos() {
        return secondBatteryPos;
    }

    public int getFirstBatteryPos() {
        return firstBatteryPos;
    }

}
