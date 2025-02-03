package game;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import game.gameobject.environment.Environment;
import game.gameobject.entity.Entity;
import game.bullet.Bullet;

public class MatrixDisplay {
    public static void displayMatrix(String title, Object[][] matrix, String type) {
        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 600);

        DefaultTableModel tableModel = new DefaultTableModel();
        for (int col = 0; col < matrix[0].length; col++) {
            tableModel.addColumn("Col " + col);
        }

        for (Object[] row : matrix) {
            Object[] displayRow = new Object[row.length];
            for (int i = 0; i < row.length; i++) {
                displayRow[i] = row[i];
            }
            tableModel.addRow(displayRow);
        }

        JTable table = new JTable(tableModel);
        table.setDefaultRenderer(Object.class, new MatrixCellRenderer(type));
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setCellSelectionEnabled(false);
        table.setRowSelectionAllowed(false);
        table.setColumnSelectionAllowed(false);

        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

        frame.setVisible(true);
    }
    static class MatrixCellRenderer extends DefaultTableCellRenderer {
        private String type;

        public MatrixCellRenderer(String type) {
            this.type = type;
            setHorizontalAlignment(SwingConstants.CENTER);
        }

        @Override
        public Component getTableCellRendererComponent(
                JTable table, Object value, boolean isSelected,
                boolean hasFocus, int row, int column) {

            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            setFont(new Font("Arial", Font.BOLD, 12));
            setBackground(Color.WHITE);
            setForeground(Color.BLACK);
            setText("");

            // Customize cell appearance based on type and value
            switch (type) {
                case "Environment":
                    if (value == null) {
                        setText(".");
                    } else if (value instanceof game.gameobject.environment.GhostEnvironment) {
                        setBackground(Color.LIGHT_GRAY);
                        setText("G");
                    } else if (value instanceof game.gameobject.environment.WallEnvironment) {
                        setBackground(Color.DARK_GRAY);
                        setText("W");
                    } else if (value instanceof game.gameobject.environment.SolidEnvironment) {
                        setBackground(Color.BLACK);
                        setForeground(Color.WHITE);
                        setText("S");
                    }
                    break;

                case "Entity":
                    if (value == null) {
                        setText(".");
                    } else if (value instanceof game.gameobject.entity.Enemy) {
                        setBackground(Color.RED);
                        setText("E");
                    } else if (value instanceof game.gameobject.entity.NeutralMob) {
                        setBackground(Color.ORANGE);
                        setText("N");
                    } else if (value instanceof game.gameobject.entity.Player) {
                        setBackground(Color.BLUE);
                        setForeground(Color.WHITE);
                        setText("P");
                    }
                    break;

                case "EnemyBullets":
                    if (value == null || ((List<?>) value).isEmpty()) {
                        setText(".");
                    } else {
                        setBackground(Color.PINK);
                        setText(String.valueOf(((List<?>) value).size()));
                    }
                    break;

                case "FriendlyBullets":
                    if (value == null || ((List<?>) value).isEmpty()) {
                        setText(".");
                    } else {
                        setBackground(Color.CYAN);
                        setText(String.valueOf(((List<?>) value).size()));
                    }
                    break;

                case "Combined":
                    String combinedText = "";
                    Color combinedColor = Color.WHITE;

                    if (value instanceof CombinedCell) {
                        CombinedCell cell = (CombinedCell) value;
                        // Example: Combine Environment and Entity
                        if (cell.environment != null) {
                            if (cell.environment instanceof game.gameobject.environment.GhostEnvironment) {
                                combinedText += "G";
                            } else if (cell.environment instanceof game.gameobject.environment.WallEnvironment) {
                                combinedText += "W";
                            } else if (cell.environment instanceof game.gameobject.environment.SolidEnvironment) {
                                combinedText += "S";
                            }
                        }

                        if (cell.entity != null) {
                            if (cell.entity instanceof game.gameobject.entity.Enemy) {
                                combinedText += "E";
                            } else if (cell.entity instanceof game.gameobject.entity.NeutralMob) {
                                combinedText += "N";
                            } else if (cell.entity instanceof game.gameobject.entity.Player) {
                                combinedText += "P";
                            }
                        }

                        if (cell.enemyBullets != null && !cell.enemyBullets.isEmpty()) {
                            combinedText += "H" + cell.enemyBullets.size();
                            combinedColor = Color.MAGENTA;
                        }

                        if (cell.friendlyBullets != null && !cell.friendlyBullets.isEmpty()) {
                            combinedText += "F" + cell.friendlyBullets.size();
                            combinedColor = Color.GREEN;
                        }

                        setText(combinedText);
                        setBackground(combinedColor);
                    } else {
                        setText(".");
                        setBackground(Color.WHITE);
                    }
                    break;

                default:
                    setText(".");
                    break;
            }

            return this;
        }
    }


    public static class CombinedCell {
        public Environment environment;
        public Entity entity;
        public List<Bullet> enemyBullets;
        public List<Bullet> friendlyBullets;

        public CombinedCell(Environment environment, Entity entity, List<Bullet> enemyBullets, List<Bullet> friendlyBullets) {
            this.environment = environment;
            this.entity = entity;
            this.enemyBullets = enemyBullets;
            this.friendlyBullets = friendlyBullets;
        }
    }
    public static Object[][] createCombinedMatrix(Environment[][] environmentMatrix, Entity[][] entityMatrix,
                                                  List<Bullet>[][] enemyBulletsMatrix, List<Bullet>[][] friendlyBulletsMatrix) {
        int height = environmentMatrix.length;
        int width = environmentMatrix[0].length;
        Object[][] combinedMatrix = new Object[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                combinedMatrix[y][x] = new CombinedCell(
                        environmentMatrix[y][x],
                        entityMatrix[y][x],
                        enemyBulletsMatrix[y][x],
                        friendlyBulletsMatrix[y][x]
                );
            }
        }

        return combinedMatrix;
    }
}

