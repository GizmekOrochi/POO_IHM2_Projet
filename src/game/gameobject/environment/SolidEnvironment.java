package game.gameobject.environment;

//Solid Environment: Bullet can pass, act like a wall for the entities
public class SolidEnvironment extends Environment {

    public SolidEnvironment(String name, int health) {
        super(name, health);
    }
}
