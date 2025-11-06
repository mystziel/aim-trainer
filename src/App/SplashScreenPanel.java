package App;

import javax.swing.*;
import java.awt.*;

public class SplashScreenPanel extends JPanel {
    private int circleSize = 100;
    private boolean growing = true;
    private Timer timer;

    public SplashScreenPanel(GameController controller) {
    setLayout(null);
        setBackground(new Color(25, 25, 25));

        createUI(controller);
        startAnimation();
    }
    
    private void createUI(GameController controller) {
        JLabel title = createTitleLabel();
        JButton startButton = createStartButton(controller);
        
        add(title);
        add(startButton);
    }
    
    private JLabel createTitleLabel() {
        JLabel title = new JLabel("AIM TRAINER", SwingConstants.CENTER);
        title.setFont(new Font("Verdana", Font.BOLD, 48));
        title.setForeground(Color.WHITE);
        title.setBounds(150, 150, 500, 80);
        return title;
    }
    
    private JButton createStartButton(GameController controller) {
        JButton startButton = new JButton("START");
        startButton.setFont(new Font("Segoe UI", Font.BOLD, 24));
        startButton.setBackground(new Color(200, 50, 50));
        startButton.setForeground(Color.WHITE);
        startButton.setFocusPainted(false);
        startButton.setBounds(300, 400, 200, 70);
        startButton.addActionListener(e -> controller.startGameWithCountdown());
        return startButton;
    }
    
    private void startAnimation() {
        timer = new Timer(50, e -> {
            if (growing) circleSize += 2;
            else circleSize -= 2;

            if (circleSize >= 120) growing = false;
            if (circleSize <= 100) growing = true;

            repaint();
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int x = getWidth()/2 - circleSize/2;
        int y = 250;

        g.setColor(Color.RED);
        g.fillOval(x, y, circleSize, circleSize);

        g.setColor(Color.WHITE);
        g.fillOval(x + circleSize/5, y + circleSize/5, (int)(circleSize*0.6), (int)(circleSize*0.6));

        g.setColor(Color.BLACK);
        g.fillOval(x + circleSize/2 - 10, y + circleSize/2 - 10, 20, 20);
    }
}