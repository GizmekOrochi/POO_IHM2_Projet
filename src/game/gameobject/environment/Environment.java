package game.gameobject.environment;

import game.gameobject.GameObject;

public abstract class Environment extends GameObject {

    private int health;

    //Constructor
    public Environment(String name, int health) {
        super(name);
        this.health = health;
    }

    //Getter for health
    public int getHealth() {
        return health;
    }

    //Method to damage the environment
    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            destroy();
        }
    }

    //Method to handle destruction
    private void destroy() {
        System.out.println(getName() + " has been destroyed!");
    }
}

