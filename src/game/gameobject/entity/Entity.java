package game.gameobject.entity;

import game.gameobject.GameObject;

public abstract class Entity extends GameObject {

    private int health;
    private int movespeed;

    //Constructor
    public Entity(String name, int health, int movespeed) {
        super(name);
        this.health = health;
        this.movespeed = movespeed;
    }

    //Getters and Setters
    public int getHealth() {
        return this.health;
    }
    
    public void setHealth(int health) {
        if (health < 0) {
            this.health = 0;
        } else {
            this.health = health;
        }
    }
    
    public int getMovespeed() {
        return this.movespeed;
    }
    
    public void setMovespeed(int movespeed) {
        if (movespeed < 0) {
            this.movespeed = 0;
        } else {
            this.movespeed = health;
        }
    }

    //Method to take damage
    public void takeDamage(int damage) {
        this.health -= damage;
        if (health <= 0) {
            this.health = 0;
            onDeath(); // call a death method
        }
    }
    protected void onDeath() {
        //Placeholder for handling death (int movespeedtrigger animation, remove from game)
        System.out.println(getName() + " has died.");
    }
}

