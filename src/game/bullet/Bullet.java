package game.bullet;

public abstract class Bullet {
    private int speed;
    private int[][] target;

    // Constructor
    public Bullet(int speed, int[][] target) {
        this.speed = speed;
        this.target = target;
    }

    // Getter and Setter methods
    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}

