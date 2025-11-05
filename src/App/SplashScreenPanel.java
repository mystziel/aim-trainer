package App;

import javax.swing.*;
import java.awt.*;

public class SplashScreenPanel extends JPanel {
    private int circleSize = 100;
    private boolean growing = true;
    private Timer timer;

    public SplashScreenPanel() {
        setBackground(new Color(25, 25, 25));

        // animation
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
