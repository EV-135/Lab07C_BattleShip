import javax.swing.SwingUtilities;

public class BattleShipRunner {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            Game game = new Game();

            BattleShipGUI gui = new BattleShipGUI(game);

            gui.setVisible(true);
        });
    }
}
