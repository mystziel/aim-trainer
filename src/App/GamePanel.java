package App;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private JLabel scoreLabel;
    private JLabel timeLabel;
    private TargetComponent target;
    
    public GamePanel(GameController controller) {
        setLayout(new BorderLayout());
        setBackground(new Color(30, 30, 30));
        
        createInfoBar();
        createGameArea(controller);
    }
    
    private void createInfoBar() {
        JPanel infoBar = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 10));
        infoBar.setBackground(new Color(45, 45, 45));
        
        scoreLabel = createStyledLabel("Score: 0");
        timeLabel = createStyledLabel("Time: 30s");
        
        infoBar.add(scoreLabel);
        infoBar.add(timeLabel);
        add(infoBar, BorderLayout.NORTH);
    }
    
    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Segoe UI", Font.BOLD, 18));
        return label;
    }
    
    private void createGameArea(GameController controller) {
        target = new TargetComponent(controller);
        add(target, BorderLayout.CENTER);
    }
    
    public void updateScore(int score) {
        scoreLabel.setText("Score: " + score);
    }
    
    public void updateTime(int time) {
        timeLabel.setText("Time: " + time + "s");
    }
    
    public TargetComponent getTarget() {
        return target;
    }
}