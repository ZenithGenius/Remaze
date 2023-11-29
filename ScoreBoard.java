import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.nio.file.*;

public class ScoreBoard {

    private Frame frame;
    private int coins;
    private int bloodLevel;
    private int mobsKilled;
    private int level;
    private double totalScore;
    private double bestScore;
    private JPanel panel;
    private JDialog dialog; // Reference to the dialog containing the score board

    public ScoreBoard(int coins, int bloodLevel, int mobsKilled, int level) {
        this.coins = coins;
        this.bloodLevel = bloodLevel;
        this.mobsKilled = mobsKilled;
        this.level = level;
        this.totalScore = coins + bloodLevel + mobsKilled + level;

        panel = new JPanel(new BorderLayout());

        // Read the best score from the file
        bestScore = readBestScore();

        // Check if the new score is a high score
        if (totalScore > bestScore) {
            bestScore = totalScore;
            writeBestScore((int) bestScore);
            JOptionPane.showMessageDialog(null, "YOU WIN \nNew High Score! Score: " + bestScore);
        } else {
            JOptionPane.showMessageDialog(null, "YOU WIN \nScore: " + totalScore);
        }

        // Display the score board with buttons
        displayScoreBoard();
    }

    private void displayScoreBoard() {
        String[] columnNames = {"Description", "Value"};
        Object[][] data = {
                {"Coins Left", coins},
                {"Blood Level", bloodLevel},
                {"Mobs Killed", mobsKilled},
                {"Level", level},
                {"Total Score", totalScore},
                {"Best Score", bestScore}
        };

        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(model);

        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton mainMenuButton = new JButton("Main Menu");
        JButton restartButton = new JButton("Restart");
        JButton exitButton = new JButton("Exit Game");

        // Show the panel in a JOptionPane dialog without the default "Ok" button
        dialog = new JDialog();
        dialog.setModal(true);
        dialog.setContentPane(panel);
        dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        dialog.setSize(300, 200); // Or pack() for actual sizes of components

        // Add action listeners to buttons
        mainMenuButton.addActionListener(e -> {
           // dialog.dispose(); // Dispose of the dialog
            goToMainMenu();
        });

        restartButton.addActionListener(e -> {
            dialog.dispose(); // Dispose of the dialog
            restartGame();
        });

        exitButton.addActionListener(e -> {
            dialog.dispose(); // Dispose of the dialog
            exitGame();
        });

        buttonPanel.add(mainMenuButton);
        buttonPanel.add(restartButton);
        buttonPanel.add(exitButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true); // Show the dialog
    }

    private void goToMainMenu() {
        // Code to transition to the main menu
        dialog.dispose();
        System.out.println("Hello 1");
        frame = new Frame();
    }


    private void restartGame() {
        // Code to restart the game
        dialog.dispose();
        frame = new Frame();
        frame.startGame();
    }

    private void exitGame() {
        System.out.println("Hello 3");
        System.exit(0);
    }

    private int readBestScore() {
        try {
            Path filePath = Paths.get("score.txt");
            if (Files.exists(filePath)) {
                String content = new String(Files.readAllBytes(filePath));
                return Integer.parseInt(content.trim());
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private void writeBestScore(int score) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("score.txt"))) {
            writer.write(String.valueOf(score));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}