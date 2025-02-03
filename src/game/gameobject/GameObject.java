package game.gameobject;

public abstract class GameObject {

    private String name;

    //Constructor
    public GameObject(String name) {
        this.name = name;
    }

    //Getter for the name
    public String getName() {
        return this.name;
    }
}

