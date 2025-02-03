package game.gameobject.entity;

import game.bullet.Weapon;
import game.gameobject.entity.Entity;

public class Player extends Entity {
    private Weapon weapon;

    public Player(String name, int health, int moveSpeed, Weapon weapon) {
        super(name, health, moveSpeed);
        this.weapon = weapon;
    }

    //Getter and Setter
    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }
}

