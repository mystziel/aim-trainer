package App;

import javax.swing.SwingUtilities;

public class AimTrainer {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameFrame window = new GameFrame();
            window.setVisible(true);
        });
    }
}
