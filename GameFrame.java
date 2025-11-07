package App;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    private GameController gameController;
    private SplashScreenPanel splashScreen;
    private GamePanel gamePanel;
    private CountdownOverlay countdownOverlay;
    private MenuController menuController;
    private JPanel mainPanel;
    private CardLayout cardLayout;
    
    public GameFrame() {
        setTitle("Aim Trainer");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        gameController = new GameController(this);
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        gamePanel = new GamePanel(gameController);
        countdownOverlay = new CountdownOverlay();
        menuController = new MenuController(gameController, gamePanel);
        splashScreen = new SplashScreenPanel(gameController);
        
        setupLayout();
        showSplashScreen();
    }
    
    private void setupLayout() {
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setLayout(new OverlayLayout(layeredPane));
        setContentPane(layeredPane);
        
        mainPanel.setSize(800, 600);
        mainPanel.add(splashScreen, "Splash");
        mainPanel.add(gamePanel, "Game");
        
        layeredPane.add(mainPanel, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(countdownOverlay, JLayeredPane.MODAL_LAYER);
        
        setJMenuBar(menuController.createMenuBar());
    }
    
    public void showSplashScreen() {
        cardLayout.show(mainPanel, "Splash");
        countdownOverlay.setVisible(false);
    }
    
    public void showGameScreen() {
        cardLayout.show(mainPanel, "Game");
        countdownOverlay.setVisible(false);
    }
    
    // Getters
    public GamePanel getGamePanel() { return gamePanel; }
    public CountdownOverlay getCountdownOverlay() { return countdownOverlay; }
}