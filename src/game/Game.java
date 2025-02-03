package game;

import game.gameobject.environment.Environment;
import game.gameobject.entity.Entity;
import game.gameobject.environment.SolidEnvironment;
import game.gameobject.environment.GhostEnvironment;
import game.gameobject.environment.WallEnvironment;
import game.gameobject.entity.Player;
import game.gameobject.entity.Enemy;
import game.gameobject.entity.NeutralMob;
import game.gameobject.entity.EnemyType;
import game.gameobject.entity.NeutralMob;
import game.bullet.Weapon;
import game.bullet.BulletType;
import java.util.Random;

public class Game {

    private Map map;
    private int level;
    private Random random;

    public Game(String str) {
    	this.level = 1;
        random = new Random();
        initMap(str);
    }

    public Map getMap() {
        return this.map;
    }
    
    public int getLevel() {
        return this.level;
    }

    public void initMap(String name) {
        int width = random.nextInt(40) + 20 + getLevel();
	int height = (int) ((2.0 / 3.0) * width);

        int spawnX = width - 1;
        int spawnY = height / 2 - height / 10;
        if (spawnY < 0) {
            spawnY = 0;
        }

        map = new Map(name, getLevel() + 1, width, height, spawnX, spawnY);
        
        Weapon weapon = new Weapon(
                    30,
                    BulletType.PROJECTILE,
                    30
                );          
        Player player = new Player("Hero", 100, 50, weapon);
        map.placeEntity(player, spawnX, spawnY);
        int nb_loc_spawn = width * height / 3;
        spawnEnvironments(2 * (nb_loc_spawn / 3));
        spawnEnemies(nb_loc_spawn / 3 - (nb_loc_spawn / 3) / 10);
        spawnNeutralNob((nb_loc_spawn / 3) / 10);
    }
    private void spawnEnvironments(int count) {
        Environment env;
        for (int i = 0; i < count; i++) {
            int y = random.nextInt(map.getWidth());
            int x = random.nextInt(map.getHeight());
            if ((x == map.getSpawnPlayerX() && y == map.getSpawnPlayerY())
                || !map.isEnvironmentEmpty(x, y)) {
                i--;
                continue;
            }
            int envChoice = random.nextInt(3);
            switch (envChoice) {
                case 0:
                    env = new SolidEnvironment("mur", 150);
                    map.placeEnvironment(env, x, y);
                    break;
                case 1:
                    env = new GhostEnvironment("decor", 10);
                    map.placeEnvironment(env, x, y);
                    break;
                case 2:
                    env = new WallEnvironment("protection", 500);
                    map.placeEnvironment(env, x, y);
                    break;
                default:
                    // Should not happen but belek
                    break;
            }
        }
    }
    private void spawnNeutralNob(int count) {
        Entity ent;
        for (int i = 0; i < count; i++) {
            int y = random.nextInt(map.getWidth());
            int x = random.nextInt(map.getHeight());
            if ((x == map.getSpawnPlayerX() && y == map.getSpawnPlayerY())
                || !map.isEnvironmentEmpty(x, y)
                || !map.isEntityEmpty(x, y)) {
                i--;
                continue;
            }

            int envChoice = random.nextInt(4);
            switch (envChoice) {
                case 0:
                    ent = new NeutralMob("Zack", 50, 30);
                    map.placeEntity(ent, x, y);
                    break;
                case 1:
                    ent = new NeutralMob("Elouan", 15, 15);
                    map.placeEntity(ent, x, y);
                    break;
                case 2:
                    ent = new NeutralMob("Phasm", 5, 5);
                    map.placeEntity(ent, x, y);
                    break;
                case 3:
                    ent = new NeutralMob("Cat, I have farted", 1, 0);
                    map.placeEntity(ent, x, y);
                    break;
                default:
                    //Should not happen but belek again
                    break;
            }
        }
    }
    private void spawnEnemies(int count) {
        for (int i = 0; i < count; i++) {
            int y = random.nextInt(map.getWidth());
            int x = random.nextInt(map.getHeight());
            if ((x == map.getSpawnPlayerX() && y == map.getSpawnPlayerY())
                || !map.isEnvironmentEmpty(x, y)
                || !map.isEntityEmpty(x, y)) {
                i--;
                continue;
            }

            //10 % for the Behemoth, 20 / 170 of the Colossal, 30 / 170 of the Armored, 60 / 170 for the Normal and 50 / 170 for the LIGHT. Why / 170 and not / 100 ? BECAUSE.
            int randomNum = random.nextInt(170);
            Enemy enemy;

            if (randomNum < 10) {
                enemy = createBehemoth();
            } else if (randomNum < 30) {
                enemy = createColossal();
            } else if (randomNum < 60) {
                enemy = createArmored();
            } else if (randomNum < 120) {
                enemy = createNormal();
            } else {
                enemy = createLight();
            }

            map.placeEntity(enemy, x, y);
        }
    }

    //Creating the different "types" of enemies
    private Enemy createBehemoth() {
        int health = random.nextInt(200) + getLevel() * 10 + 1000;
        int armor  = 3;
        int movespeed = 35;
        int sizeDetection = map.getWidth() * map.getHeight();
        int range = 4 + random.nextInt(3) - random.nextInt(3);
        Weapon weapon;

        int weaponRoll = random.nextInt(3);
        switch (weaponRoll) {
            case 0:
                weapon = new Weapon(
                    25,
                    BulletType.HITSCAN,
                    40
                );
                break;
            case 1:
                weapon = new Weapon(
                    50,
                    BulletType.PROJECTILE,
                    30
                );
                break;
            default:
                weapon = new Weapon(
                    40,
                    BulletType.LASER,
                    30
                );
                break;
        }

        return new Enemy("World ender", health, movespeed, armor, sizeDetection, range, EnemyType.BEHEMOTH, weapon);
    }

    private Enemy createColossal() {
        int health = random.nextInt(45) + getLevel() * 3 + 450;
        int armor  = 2;
        int movespeed = 25;
        int sizeDetection = 20;
        int weaponRoll = random.nextInt(2);
        int range = 5 + random.nextInt(2) - random.nextInt(3);
        Weapon weapon;

        switch (weaponRoll) {
            case 0:
                weapon = new Weapon(
                    30,
                    BulletType.PROJECTILE,
                    30
                );
                break;
            default:
                weapon = new Weapon(
                    20,
                    BulletType.LASER,
                    40
                );
                break;
        }

        return new Enemy("menace", health, movespeed, armor, sizeDetection, range, EnemyType.COLOSAL, weapon);
    }

    private Enemy createArmored() {
        int health = random.nextInt(20) + getLevel() * 3 + 150;
        int armor  = 5;
        int movespeed = 45;
        int sizeDetection = 15;
        int weaponRoll = random.nextInt(2);
        int range = 6 + random.nextInt(3) - random.nextInt(3);
        Weapon weapon;

        switch (weaponRoll) {
            case 0:
                weapon = new Weapon(
                    50,
                    BulletType.PROJECTILE,
                    20
                );
                break;
            default:
                weapon = new Weapon(
                    20,
                    BulletType.LASER,
                    30
                );
                break;
        }

        return new Enemy("commander", health, movespeed, armor, sizeDetection, range, EnemyType.ARMORED, weapon);
    }

    private Enemy createNormal() {
        int health = random.nextInt(10) + getLevel() * 2 + 80;
        int armor  = 2;
        int movespeed = 50;
        int sizeDetection = 15;
        int range = 5 + random.nextInt(3) - random.nextInt(3);
        int weaponRoll = random.nextInt(3);
        Weapon weapon;

        switch (weaponRoll) {
            case 0:
                weapon = new Weapon(
                    10,
                    BulletType.HITSCAN,
                    35
                );
                break;
            case 1:
                weapon = new Weapon(
                    25,
                    BulletType.PROJECTILE,
                    15
                );
                break;
            default:
                weapon = new Weapon(
                    15,
                    BulletType.LASER,
                    25
                );
                break;
        }

        return new Enemy("soldier", health, movespeed, armor, sizeDetection, range, EnemyType.NORMAL, weapon);
    }

    private Enemy createLight() {
        int health = random.nextInt(5) + getLevel() + 30;
        int armor  = 1;
        int movespeed = 70;
        int sizeDetection = 20;
        int range = 3 + random.nextInt(2) - random.nextInt(2);

        Weapon weapon = new Weapon(
            25,
            BulletType.PROJECTILE,
            15
        );

        return new Enemy("under servant", health, movespeed, armor, sizeDetection, range, EnemyType.LIGHT, weapon);
    }
}

