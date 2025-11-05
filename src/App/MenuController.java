package App;

import App.GameFrame;
import javax.swing.*;
import java.awt.event.*;

public class MenuController {
    private GameFrame frame;

    public MenuController(GameFrame frame) {
        this.frame = frame;
    }

    public JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // game
        JMenu gameMenu = new JMenu("Game");

        JMenuItem start = new JMenuItem("Start");
        start.addActionListener(e -> frame.startGame());

        JMenuItem stop = new JMenuItem("Stop");
        stop.addActionListener(e -> JOptionPane.showMessageDialog(frame,
            "Stop functionality not yet implemented", "Info", JOptionPane.INFORMATION_MESSAGE));

        JMenuItem reset = new JMenuItem("Reset");
        reset.addActionListener(e -> JOptionPane.showMessageDialog(frame,
            "Reset functionality not yet implemented", "Info", JOptionPane.INFORMATION_MESSAGE));

        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(e -> System.exit(0));

        gameMenu.add(start);
        gameMenu.add(stop);
        gameMenu.add(reset);
        gameMenu.addSeparator();
        gameMenu.add(exit);

        // view
        JMenu viewMenu = new JMenu("View");
        JCheckBoxMenuItem showInfo = new JCheckBoxMenuItem("Show Info Panel", true);
        showInfo.addActionListener(e -> frame.toggleInfoPanel(showInfo.isSelected()));
        viewMenu.add(showInfo);

        // difficulty
        JMenu difficultyMenu = new JMenu("Difficulty");
        JMenuItem easy = new JMenuItem("Easy");
        easy.addActionListener(e -> frame.setDifficulty("easy"));
        JMenuItem normal = new JMenuItem("Normal");
        normal.addActionListener(e -> frame.setDifficulty("normal"));
        JMenuItem hard = new JMenuItem("Hard");
        hard.addActionListener(e -> frame.setDifficulty("hard"));

        difficultyMenu.add(easy);
        difficultyMenu.add(normal);
        difficultyMenu.add(hard);

        // help 
        JMenu helpMenu = new JMenu("Help");
        JMenuItem about = new JMenuItem("About");
        about.addActionListener(e ->
            JOptionPane.showMessageDialog(
                frame,
                "Aim Trainer Game v1.0\nCreated by Group 4",
                "About",
                JOptionPane.INFORMATION_MESSAGE
            )
        );
        helpMenu.add(about);

        // all menus 
        menuBar.add(gameMenu);
        menuBar.add(viewMenu);
        menuBar.add(difficultyMenu);
        menuBar.add(helpMenu);

        return menuBar;
    }
}
