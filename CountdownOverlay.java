package App;

import javax.swing.*;
import java.awt.*;

public class CountdownOverlay extends JPanel {
    private JLabel countdownLabel;
    
    public CountdownOverlay() {
        setLayout(new BorderLayout());
        setBackground(new Color(0, 0, 0, 180));
        setOpaque(true);
        setVisible(false);
        setSize(800, 600);
        
        createCountdownLabel();
    }
    
    private void createCountdownLabel() {
        countdownLabel = new JLabel("", SwingConstants.CENTER);
        countdownLabel.setFont(new Font("Segoe UI", Font.BOLD, 48));
        countdownLabel.setForeground(Color.WHITE);
        countdownLabel.setBackground(new Color(30, 30, 30));
        countdownLabel.setOpaque(true);
        countdownLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        
        add(countdownLabel, BorderLayout.CENTER);
    }
    
    public void showMessage(String text) {
        countdownLabel.setText(text);
        setVisible(true);
    }
}