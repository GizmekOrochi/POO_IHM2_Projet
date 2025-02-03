package game.bullet;

import game.bullet.BulletType;

public class Weapon {
    private double fireRate;
    private BulletType type;
    private int power;

    //Constructor to initialize weapon properties
    public Weapon(double fireRate, BulletType type, int power) {
        this.fireRate = fireRate;
        this.type = type;
        this.power = power;
    }

    //Getter and Setter
    public double getFireRate() {
        return fireRate;
    }

    public void setFireRate(double fireRate) {
        this.fireRate = fireRate;
    }
    
    public BulletType getType() {
        return type;
    }

    public void setType(BulletType type) {
        this.type = type;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }
}

