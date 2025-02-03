package game;

import game.gameobject.environment.Environment;
import game.gameobject.entity.Entity;
import game.bullet.Bullet;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        Game game = new Game("MyFirstMap");

        Map map = game.getMap();

        Environment[][] environmentMatrix = map.getEnvironment();
        Entity[][] entityMatrix = map.getEntity();
        List<Bullet>[][] enemyBulletsMatrix = map.getEnemyBullets();
        List<Bullet>[][] friendlyBulletsMatrix = map.getFriendlyBullets();

        // Display each matrix in separate windows
        MatrixDisplay.displayMatrix("Environment Matrix", environmentMatrix, "Environment");
        MatrixDisplay.displayMatrix("Entity Matrix", entityMatrix, "Entity");
        MatrixDisplay.displayMatrix("Enemy Bullets Matrix", enemyBulletsMatrix, "EnemyBullets");
        MatrixDisplay.displayMatrix("Friendly Bullets Matrix", friendlyBulletsMatrix, "FriendlyBullets");

        // Create and display the combined matrix
        Object[][] combinedMatrix = MatrixDisplay.createCombinedMatrix(
                environmentMatrix,
                entityMatrix,
                enemyBulletsMatrix,
                friendlyBulletsMatrix
        );
        MatrixDisplay.displayMatrix("Combined Matrix", combinedMatrix, "Combined");

        System.out.println("Game initialized successfully!");
    }
}

