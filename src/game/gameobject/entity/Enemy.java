package game.gameobject.entity;

import game.bullet.Weapon;
import game.gameobject.entity.Entity;

public class Enemy extends Entity {
    private EnemyType enemyType;
    private Weapon weapon;
    private int armor;
    private int sizeDetection;
    private int range;

    public Enemy(String name, int health, int moveSpeed, int armor, int sizeDetection, int range, EnemyType enemyType, Weapon weapon) {
        super(name, health, moveSpeed);
        this.armor = armor;
        this.sizeDetection = sizeDetection;
        this.enemyType = enemyType;
        this.weapon = weapon;
    }

    //Getters and Setters
    public EnemyType getEnemyType() {
        return enemyType;
    }

    public void setEnemyType(EnemyType enemyType) {
        this.enemyType = enemyType;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    public int getSizeDetection() {
        return sizeDetection;
    }

    public void setSizeDetection(int sizeDetection) {
        this.sizeDetection = sizeDetection;
    }
    
    public int getRange() {
        return range;
    }
    
    public void setRange(int range) {
        this.range = range;
    }
}

