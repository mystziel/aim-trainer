package App;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    private JPanel mainPanel;
    private SplashScreenPanel splashScreen;
    private JPanel gamePanel;
    private JLabel scoreLabel, timeLabel;
    private TargetComponent target;
    private JPanel infoBar;

    // difficulty multiplier 
    private double difficultyMultiplier = 1.0;

    public GameFrame() {
        setTitle("Aim Trainer");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        mainPanel = new JPanel(new CardLayout());
        add(mainPanel);

        createSplashScreen();
        createGamePanel();

        // menu bar
        MenuController menu = new MenuController(this);
        setJMenuBar(menu.createMenuBar());

        showSplashScreen();
    }

    private void createSplashScreen() {
        splashScreen = new SplashScreenPanel();
        splashScreen.setLayout(null);

        JLabel title = new JLabel("AIM TRAINER", SwingConstants.CENTER);
        title.setFont(new Font("Verdana", Font.BOLD, 48));
        title.setForeground(Color.WHITE);
        title.setBounds(150, 150, 500, 80);
        splashScreen.add(title);

        JButton startButton = new JButton("START");
        startButton.setFont(new Font("Segoe UI", Font.BOLD, 24));
        startButton.setBackground(new Color(200, 50, 50));
        startButton.setForeground(Color.WHITE);
        startButton.setFocusPainted(false);
        startButton.setBounds(300, 400, 200, 70);
        startButton.addActionListener(e -> startGame());
        splashScreen.add(startButton);

        mainPanel.add(splashScreen, "Splash");
    }

    private void createGamePanel() {
        gamePanel = new JPanel(new BorderLayout());
        gamePanel.setBackground(new Color(30, 30, 30));

        // top info bar
        infoBar = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 10));
        infoBar.setBackground(new Color(45, 45, 45));

        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));

        timeLabel = new JLabel("Time: 30s");
        timeLabel.setForeground(Color.WHITE);
        timeLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));

        infoBar.add(scoreLabel);
        infoBar.add(timeLabel);
        gamePanel.add(infoBar, BorderLayout.NORTH);

        // center target
        target = new TargetComponent();
        gamePanel.add(target, BorderLayout.CENTER);

        mainPanel.add(gamePanel, "Game");
    }

    public void showSplashScreen() {
        ((CardLayout) mainPanel.getLayout()).show(mainPanel, "Splash");
    }

    public void startGame() {
        ((CardLayout) mainPanel.getLayout()).show(mainPanel, "Game");
    }

    // info panel visibility
    public void toggleInfoPanel(boolean visible) {
        infoBar.setVisible(visible);
        infoBar.revalidate();
        infoBar.repaint();
    }

    // difficulty setter
    public void setDifficulty(String level) {
        switch (level.toLowerCase()) {
            case "easy": difficultyMultiplier = 0.8; break;
            case "normal": difficultyMultiplier = 1.0; break;
            case "hard": difficultyMultiplier = 1.5; break;
        }
        target.setDifficulty(difficultyMultiplier);
    }

    public JLabel getScoreLabel() { return scoreLabel; }
    public JLabel getTimeLabel() { return timeLabel; }
    public TargetComponent getTarget() { return target; }
}
