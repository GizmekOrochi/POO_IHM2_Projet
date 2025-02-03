package game;

import game.gameobject.environment.Environment;
import game.gameobject.environment.SolidEnvironment;
import game.gameobject.environment.GhostEnvironment;
import game.gameobject.environment.WallEnvironment;

import game.gameobject.entity.Entity;
import game.gameobject.entity.Player;
import game.gameobject.entity.NeutralMob;
import game.gameobject.entity.Enemy;
import game.gameobject.entity.EnemyType;

import game.bullet.Weapon;
import game.bullet.Bullet;
import game.bullet.Projectile;
import game.bullet.Laser;
import game.bullet.Hitscann;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@SuppressWarnings("unchecked")
public class Map {

    private String name;
    private int width;
    private int height;
    private int level;
    private int playerX;
    private int playerY;
    private int SpawnPlayerX;
    private int SpawnPlayerY;
    private Environment[][] environment;
    private Entity[][] entity;
    private List<Bullet>[][] enemyBullets;
    private List<Bullet>[][] friendlyBullets;

    public Map(String name, int level, int height, int width, int SpawnPlayerX, int SpawnPlayerY) {
        this.name = name;
        this.level = level;
        this.width = width;
        this.height = height;
        this.SpawnPlayerX = SpawnPlayerX;
        this.SpawnPlayerY = SpawnPlayerY;
        this.playerX = SpawnPlayerX;
        this.playerY = SpawnPlayerY;

        this.environment = new Environment[height][width];
        this.entity = new Entity[height][width];
        this.enemyBullets = (List<Bullet>[][]) new List[height][width];
        this.friendlyBullets = (List<Bullet>[][]) new List[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                this.environment[y][x] = null;
                this.entity[y][x] = null;
                this.enemyBullets[y][x] = new ArrayList<>();
                this.friendlyBullets[y][x] = new ArrayList<>();
            }
        }
    }

    public int getSpawnPlayerX() {
        return SpawnPlayerX;
    }

    public int getSpawnPlayerY() {
        return SpawnPlayerY;
    }

    public String getName() {
        return name;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getLevel() {
        return level;
    }

    public int getPlayerX() {
        return playerX;
    }

    public int getPlayerY() {
        return playerY;
    }

    public void setPlayerPosition(int x, int y) {
        this.playerX = x;
        this.playerY = y;
    }

    public Environment[][] getEnvironment() {
        return environment;
    }

    public void setEnvironment(Environment[][] environment) {
        this.environment = environment;
    }

    public Entity[][] getEntity() {
        return entity;
    }

    public void setEntity(Entity[][] entity) {
        this.entity = entity;
    }

    public List<Bullet>[][] getEnemyBullets() {
        return enemyBullets;
    }

    public void setEnemyBullets(List<Bullet>[][] enemyBullets) {
        this.enemyBullets = enemyBullets;
    }

    public List<Bullet>[][] getFriendlyBullets() {
        return friendlyBullets;
    }

    public void setFriendlyBullets(List<Bullet>[][] friendlyBullets) {
        this.friendlyBullets = friendlyBullets;
    }

    public Environment getEnvironment(int y, int x) {
        if (y >= 0 && y < height && x >= 0 && x < width) {
            return environment[y][x];
        } else {
            throw new IndexOutOfBoundsException("Invalid environment coordinates: ("+ y + ", " + x + ").");
        }
    }

    public Entity getEntity(int y, int x) {
        if (y >= 0 && y < height && x >= 0 && x < width) {
            return entity[y][x];
        } else {
            throw new IndexOutOfBoundsException("Invalid entity coordinates: ("+ y + ", " + x + ").");
        }
    }

    public void placeEnvironment(Environment env, int y, int x) {
        if (y >= 0 && y < height && x >= 0 && x < width) {
            environment[y][x] = env;
        } else {
            throw new IndexOutOfBoundsException(
                "Invalid coordinates for placing an environment object: ("+ y + ", " + x + ")."
            );
        }
    }

    public void placeEntity(Entity ent, int y, int x) {
        if (y >= 0 && y < height && x >= 0 && x < width) {
            entity[y][x] = ent;
        } else {
            throw new IndexOutOfBoundsException(
                "Invalid coordinates for placing an entity: ("+ y + ", " + x + ")."
            );
        }
    }

    public void printEntityMatrix() {
        System.out.println("=== Entity Matrix ===");
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (entity[i][j] == null) {
                    System.out.print(".\t");
                } else if (entity[i][j] instanceof Enemy) {
                    System.out.print("E\t");
                } else if (entity[i][j] instanceof NeutralMob) {
                    System.out.print("N\t");
                } else if (entity[i][j] instanceof Player) {
                    System.out.print("P\t");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    public void printEnvironmentMatrix() {
        System.out.println("=== Environment Matrix ===");
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (environment[i][j] == null) {
                    System.out.print(".\t");
                } else if (environment[i][j] instanceof GhostEnvironment) {
                    System.out.print("G\t");
                } else if (environment[i][j] instanceof WallEnvironment) {
                    System.out.print("W\t");
                } else if (environment[i][j] instanceof SolidEnvironment) {
                    System.out.print("S\t");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    public void printEnemyBulletsMatrix() {
        System.out.println("=== Enemy Bullets Matrix ===");
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (enemyBullets[i][j] == null || enemyBullets[i][j].isEmpty()) {
                    System.out.print(".\t");
                } else {
                    System.out.print(enemyBullets[i][j].size() + "\t");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    public void printFriendlyBulletsMatrix() {
        System.out.println("=== Friendly Bullets Matrix ===");
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (friendlyBullets[i][j] == null || friendlyBullets[i][j].isEmpty()) {
                    System.out.print(".\t");
                } else {
                    System.out.print(friendlyBullets[i][j].size() + "\t");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    public boolean isEnvironmentEmpty(int y, int x) {
    	System.out.println("Checking environment at (" + y + ", " + x + ") on map of size (" + height + ", " + width + ")");
    	if (y >= 0 && y < height && x >= 0 && x < width) {
    	    return environment[y][x] == null;
    	} 	
    	else {
        	throw new IndexOutOfBoundsException("Invalid environment coordinates: (" + y + ", " + x + ").");
    	}
    }


    public boolean isEntityEmpty(int y, int x) {
    	System.out.println("Checking environment at (" + y + ", " + x + ") on map of size (" + height + ", " + width + ")");
        if (y >= 0 && y < height && x >= 0 && x < width) {
            return entity[y][x] == null;
        } else {
            throw new IndexOutOfBoundsException("Invalid coordinates for entity check: ("+ y + ", " + x + ").");
        }
    }

    private double distance(int x1, int y1, int x2, int y2) {
        int dx = x2 - x1;
        int dy = y2 - y1;
        return Math.sqrt(dx * dx + dy * dy);
    }

    private boolean isWalkable(int y, int x) {
        if (y < 0 || y >= height || x < 0 || x >= width) return false;
        if (environment[y][x] instanceof WallEnvironment ||
            environment[y][x] instanceof SolidEnvironment) {
            return false;
        }
        if (entity[y][x] != null) {
            return false;
        }

        return true;
    }
    private int[][][] computeParentsBFS(int startY, int startX) {
        if (startY < 0 || startY >= height || startX < 0 || startX >= width) {
            return null;
        }

        int[][][] parents = new int[height][width][2];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                parents[y][x][0] = -2;
                parents[y][x][1] = -2;
            }
        }
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[] { startY, startX });
        parents[startY][startX][0] = -1;
        parents[startY][startX][1] = -1;
        int[] dy = { -1, 1, 0, 0 };
        int[] dx = { 0, 0, -1, 1 };
        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int cy = current[0];
            int cx = current[1];
            for (int i = 0; i < 4; i++) {
                int ny = cy + dy[i];
                int nx = cx + dx[i];
                if (isWalkable(ny, nx) && parents[ny][nx][0] == -2) {
                    parents[ny][nx][0] = cy;
                    parents[ny][nx][1] = cx;
                    queue.add(new int[] { ny, nx });
                }
            }
        }
        return parents;
    }

    private List<Object[]> planEnemyMoves(int[][][] parents) {
        List<Object[]> moves = new ArrayList<>();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (entity[y][x] instanceof Enemy) {
                    Enemy e = (Enemy) entity[y][x];
                    if (distance(x, y, playerX, playerY) <= e.getRange()) {
                        if (parents[y][x][0] != -2) {
                            int py = parents[y][x][0];
                            int px = parents[y][x][1];
                            if (py != -1 && px != -1) {
                                moves.add(new Object[] { e, y, x, py, px });
                            }
                        }
                    }
                }
            }
        }
        return moves;
    }

    private void applyEnemyMoves(List<Object[]> moves) {
        boolean[][] occupied = new boolean[height][width];
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                if (entity[r][c] instanceof Enemy) {
                    occupied[r][c] = true;
                }
            }
        }
        for (Object[] m : moves) {
            Enemy e  = (Enemy) m[0];
            int oy   = (int) m[1];
            int ox   = (int) m[2];
            int ny   = (int) m[3];
            int nx   = (int) m[4];
            if (entity[oy][ox] == e && !occupied[ny][nx]) {
                entity[oy][ox] = null;
                entity[ny][nx] = e;
                occupied[ny][nx] = true;
            }
        }
    }
    public void moveEnemiesInRange() {
        if (playerY < 0 || playerY >= height || playerX < 0 || playerX >= width) {
            return;
        }
        int[][][] parents = computeParentsBFS(playerY, playerX);
        if (parents == null) return;
        List<Object[]> moves = planEnemyMoves(parents);
        applyEnemyMoves(moves);
    }
}

