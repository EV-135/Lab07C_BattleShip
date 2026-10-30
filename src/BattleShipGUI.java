import javax.swing.*;
import java.awt.*;

public class BattleShipGUI extends JFrame {

    private Game game;

    private JButton[][] buttons =
            new JButton[BattleShipBoard.SIZE][BattleShipBoard.SIZE];

    private JLabel missLabel;
    private JLabel strikeLabel;
    private JLabel totalMissLabel;
    private JLabel totalHitLabel;

    public BattleShipGUI(Game game) {
        this.game = game;

        setTitle("Battleship");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Title
        JLabel title = new JLabel(
                "BATTLESHIP", SwingConstants.CENTER);

        title.setFont(new Font("Arial", Font.BOLD, 26));
        add(title, BorderLayout.NORTH);

        // Game board
        JPanel boardPanel =
                new JPanel(new GridLayout(10, 10, 3, 3));

        for (int row = 0; row < BattleShipBoard.SIZE; row++) {
            for (int col = 0; col < BattleShipBoard.SIZE; col++) {

                JButton button = new JButton("≈");

                button.setFont(new Font("Arial", Font.BOLD, 18));
                button.setForeground(Color.WHITE);
                button.setBackground(new Color(100, 180, 230));
                button.setFocusPainted(false);

                final int r = row;
                final int c = col;

                button.addActionListener(
                        event -> fireAt(r, c));

                buttons[row][col] = button;
                boardPanel.add(button);
            }
        }

        add(boardPanel, BorderLayout.CENTER);

        // Counter panel
        JPanel counterPanel = new JPanel(
                new GridLayout(2, 2, 10, 5));

        missLabel = new JLabel("MISS: 0 / 5");
        strikeLabel = new JLabel("STRIKES: 0 / 3");
        totalMissLabel = new JLabel("TOTAL MISS: 0 / 83");
        totalHitLabel = new JLabel("TOTAL HIT: 0 / 17");

        Font counterFont = new Font(
                "Arial", Font.BOLD, 14);

        missLabel.setFont(counterFont);
        strikeLabel.setFont(counterFont);
        totalMissLabel.setFont(counterFont);
        totalHitLabel.setFont(counterFont);

        counterPanel.add(missLabel);
        counterPanel.add(strikeLabel);
        counterPanel.add(totalMissLabel);
        counterPanel.add(totalHitLabel);

        // Bottom panel
        JPanel bottomPanel = new JPanel(new BorderLayout());

        bottomPanel.add(counterPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();

        JButton playAgainButton = new JButton("Play Again");
        JButton quitButton = new JButton("Quit");

        playAgainButton.addActionListener(
                event -> playAgain());

        quitButton.addActionListener(
                event -> quitGame());

        buttonPanel.add(playAgainButton);
        buttonPanel.add(quitButton);

        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(bottomPanel, BorderLayout.SOUTH);

        updateCounters();

        setSize(750, 850);
        setLocationRelativeTo(null);
    }

    private void fireAt(int row, int col) {

        if (!game.isPlaying()) {
            return;
        }

        ShotResult result = game.fire(row, col);

        JButton button = buttons[row][col];

        if (result == ShotResult.ALREADY_FIRED) {
            return;
        }

        if (result == ShotResult.MISS) {
            button.setText("M");
            button.setBackground(Color.YELLOW);
            button.setForeground(Color.BLACK);
            button.setEnabled(false);
        }

        else if (result == ShotResult.HIT
                || result == ShotResult.SUNK
                || result == ShotResult.WIN) {

            button.setText("X");
            button.setBackground(Color.RED);
            button.setForeground(Color.WHITE);
            button.setEnabled(false);
        }

        updateCounters();

        if (result == ShotResult.SUNK) {
            JOptionPane.showMessageDialog(
                    this,
                    "You sunk a ship!",
                    "Ship Sunk",
                    JOptionPane.INFORMATION_MESSAGE);
        }

        if (result == ShotResult.WIN) {
            JOptionPane.showMessageDialog(
                    this,
                    "Congratulations! You destroyed all the ships!",
                    "You Win!",
                    JOptionPane.INFORMATION_MESSAGE);

            askToPlayAgain();
        }

        else if (result == ShotResult.LOSS) {
            JOptionPane.showMessageDialog(
                    this,
                    "You received 3 strikes. Game Over!",
                    "You Lose!",
                    JOptionPane.INFORMATION_MESSAGE);

            askToPlayAgain();
        }
    }

    private void updateCounters() {

        missLabel.setText(
                "MISS: " + game.getMissCounter() + " / 5");

        strikeLabel.setText(
                "STRIKES: " + game.getStrikeCounter() + " / 3");

        totalMissLabel.setText(
                "TOTAL MISS: " + game.getTotalMisses() + " / 83");

        totalHitLabel.setText(
                "TOTAL HIT: " + game.getTotalHits() + " / 17");
    }

    private void askToPlayAgain() {

        int choice = JOptionPane.showConfirmDialog(
                this,
                "Would you like to play another game?",
                "Play Again?",
                JOptionPane.YES_NO_OPTION);

        if (choice == JOptionPane.YES_OPTION) {
            game.reset();

            // Reset all board buttons
            for (int row = 0;
                 row < BattleShipBoard.SIZE; row++) {

                for (int col = 0;
                     col < BattleShipBoard.SIZE; col++) {

                    buttons[row][col].setText("≈");
                    buttons[row][col].setBackground(
                            new Color(100, 180, 230));

                    buttons[row][col].setForeground(Color.WHITE);
                    buttons[row][col].setEnabled(true);
                }
            }

            updateCounters();
        } else {
            dispose();
        }
    }

    private void playAgain() {

        int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to start a new game?",
                "New Game",
                JOptionPane.YES_NO_OPTION);

        if (choice == JOptionPane.YES_OPTION) {
            game.reset();

            for (int row = 0;
                 row < BattleShipBoard.SIZE; row++) {

                for (int col = 0;
                     col < BattleShipBoard.SIZE; col++) {

                    buttons[row][col].setText("≈");
                    buttons[row][col].setBackground(
                            new Color(100, 180, 230));

                    buttons[row][col].setForeground(Color.WHITE);
                    buttons[row][col].setEnabled(true);
                }
            }

            updateCounters();
        }
    }

    private void quitGame() {

        int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to quit?",
                "Quit Game",
                JOptionPane.YES_NO_OPTION);

        if (choice == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(
                    this, "Thanks for playing Battleship!");

            dispose();
        }
    }
}
