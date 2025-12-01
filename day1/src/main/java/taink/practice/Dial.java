package taink.practice;

public class Dial {
    private int position;
    private int zeroCounter;

    public Dial(int initialPos) {
        this.position = initialPos;
        this.zeroCounter = 0;
    }
    public Dial() {
        this(50);
    }

    public int getPosition() {
        return position;
    }

    public int getPassword() {
        return zeroCounter;
    }

    public void turnLeft(int increment) {
        int distance = increment % 100;
        position -= distance;
        if (position < 0) {
            // position is negative, so we add to subtract
            position = 100 + position;
        }
        if (position == 0) {
            zeroCounter++;
        }
    }

    public void turnRight(int increment) {
        int distance = increment % 100;
        position += distance;
        if (position > 99) {
            position -= 100;
        }
        if (position ==  0) {
            zeroCounter++;
        }
    }

    public void parseTextRotation(String text) {
        int increment = Integer.parseInt(text.substring(1));
        switch(text.charAt(0)) {
            case 'L':
                turnLeft(increment);
                break;
            case 'R':
                turnRight(increment);
                break;
        }
    }
}
