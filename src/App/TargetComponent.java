package App;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class TargetComponent extends JPanel {
    private int targetX = 300;
    private int targetY = 200;
    private int targetSize = 60;
    private Color targetColor = Color.RED;
    private double difficultyMultiplier = 1.0;
    private Random rand = new Random();

    public TargetComponent() {
        setBackground(new Color(50, 50, 50));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int mx = e.getX();
                int my = e.getY();
                double distance = Math.sqrt(Math.pow(mx - (targetX + targetSize / 2), 2)
                        + Math.pow(my - (targetY + targetSize / 2), 2));
                if (distance <= targetSize / 2) {
                    flashHitEffect();
                    moveTargetRandom();
                }
            }
        });
    }

    public void setDifficulty(double multiplier) {
        this.difficultyMultiplier = multiplier;
    }

    private void moveTargetRandom() {
        int width = getWidth() - targetSize;
        int height = getHeight() - targetSize;
        targetX = rand.nextInt(Math.max(width,1));
        targetY = rand.nextInt(Math.max(height,1));
        repaint();
    }

    private void flashHitEffect() {
        targetColor = Color.GREEN;
        repaint();
        Timer t = new Timer((int)(150 / difficultyMultiplier), e -> {
            targetColor = Color.RED;
            repaint();
            ((Timer) e.getSource()).stop();
        });
        t.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(targetColor);
        g2.fillOval(targetX, targetY, targetSize, targetSize);

        g2.setColor(Color.WHITE);
        g2.fillOval(targetX + targetSize/5, targetY + targetSize/5, (int)(targetSize*0.6), (int)(targetSize*0.6));

        g2.setColor(Color.BLACK);
        g2.fillOval(targetX + targetSize/2 - 6, targetY + targetSize/2 - 6, 12, 12);
    }
}
