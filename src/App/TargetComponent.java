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
    private boolean targetVisible = true;
    private GameController controller;
    private Timer hitEffectTimer;
    
    public TargetComponent(GameController controller) {
        this.controller = controller;
        
        setBackground(new Color(50, 50, 50));
        setEnabled(true);
        setupMouseListener();
    }
    
    private void setupMouseListener() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (!isEnabled() || !targetVisible) return;
                
                if (isHit(e.getX(), e.getY())) {
                    flashHitEffect();
                    moveTargetRandom();
                    controller.onTargetHit();
                }
            }
        });
    }
    
    private boolean isHit(int mouseX, int mouseY) {
        if (!targetVisible) {
            return false;
        }

        double centerX = targetX + targetSize / 2.0;
        double centerY = targetY + targetSize / 2.0;
        double distance = Math.sqrt(Math.pow(mouseX - centerX, 2)
                + Math.pow(mouseY - centerY, 2));
        return distance <= targetSize / 2.0;
    }
    
    public void setDifficulty(String difficulty) {
        targetSize = switch (difficulty.toLowerCase()) {
            case "easy" ->
                100;
            case "normal" ->
                60;
            case "hard" ->
                40;
            default -> {
                System.err.println("Unknown difficulty: " + difficulty);
                yield 60;
            }
        };
        repaint();
    }
      
    public void resetTarget() {
        int centerX = (getWidth() - targetSize) / 2;
        int centerY = (getHeight() - targetSize) / 2;
        
        targetX = (centerX > 0) ? centerX : 300;
        targetY = (centerY > 0) ? centerY : 200;
        
        targetColor = Color.RED;
        repaint();
    }

    public void moveTargetRandom() {
        int width = getWidth() - targetSize;
        int height = getHeight() - targetSize;
        
        if (width > 0 && height > 0) {
            targetX = rand.nextInt(width);
            targetY = rand.nextInt(height);
            repaint();
        } else {
            resetTarget();
        }
    }

    private void flashHitEffect() {
        if (hitEffectTimer != null && hitEffectTimer.isRunning()) {
            hitEffectTimer.stop();
        }

        targetColor = Color.GREEN;
        repaint();

        hitEffectTimer = new Timer((int) (150 / difficultyMultiplier), e -> {
            targetColor = Color.RED;
            repaint();
            hitEffectTimer.stop();
        });
        hitEffectTimer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if (!targetVisible) return;
        
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        drawTarget(g2);
    }
    
    private void drawTarget(Graphics2D g2) {
        
        g2.setColor(targetColor);
        g2.fillOval(targetX, targetY, targetSize, targetSize);

        g2.setColor(Color.WHITE);
        g2.fillOval(targetX + targetSize/5, targetY + targetSize/5, 
                   (int)(targetSize*0.6), (int)(targetSize*0.6));

        g2.setColor(Color.BLACK);
        g2.fillOval(targetX + targetSize/2 - 6, targetY + targetSize/2 - 6, 12, 12);
    }
    
    public void setTargetVisible(boolean visible) {
        this.targetVisible = visible;
        repaint();
    }
    
    public boolean isTargetVisible() {
        return targetVisible;
    }
    
    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        setCursor(enabled ? 
            Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR) : 
            Cursor.getDefaultCursor());
        repaint();
    }
}