package App;

import javax.swing.*;

public class MenuController {
    private GameController gameController;
    
    public MenuController(GameController gameController) {
        this.gameController = gameController;
    }

    public JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // Game Menu
        JMenu gameMenu = new JMenu("Game");
        gameMenu.add(createMenuItem("Start", e -> gameController.startGameWithCountdown()));
        gameMenu.add(createMenuItem("Stop", e -> gameController.stopGame()));
        gameMenu.add(createMenuItem("Reset", e -> gameController.resetGame()));
        gameMenu.addSeparator();
        gameMenu.add(createMenuItem("Exit", e -> System.exit(0)));

        // Difficulty Menu
        JMenu difficultyMenu = new JMenu("Difficulty");
        difficultyMenu.add(createMenuItem("Easy", e -> gameController.setDifficulty("Easy")));
        difficultyMenu.add(createMenuItem("Normal", e -> gameController.setDifficulty("Normal")));
        difficultyMenu.add(createMenuItem("Hard", e -> gameController.setDifficulty("Hard")));

        // Help Menu
        JMenu helpMenu = new JMenu("Help");
        helpMenu.add(createMenuItem("About", e -> showAboutDialog()));

        menuBar.add(gameMenu);
        menuBar.add(difficultyMenu);
        menuBar.add(helpMenu);

        return menuBar;
    }
    
    private JMenuItem createMenuItem(String text, java.awt.event.ActionListener action) {
        JMenuItem menuItem = new JMenuItem(text);
        menuItem.addActionListener(action);
        return menuItem;
    }
    
    private void showAboutDialog() {
        JOptionPane.showMessageDialog(
            null,
            "Aim Trainer Game v1.0\nCreated by Group 4",
            "About",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
}