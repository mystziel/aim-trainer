package App;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameController {
    private GameFrame frame;
    private Timer gameTimer;
    private Timer countdownTimer;
    private int countdownValue;
    private boolean gameRunning = false;
    private int score = 0;
    private int timeLeft = 30;
    private double difficultyMultiplier = 1.0;
    private String currentDifficulty = "Normal";
    
    public GameController(GameFrame frame) {
        this.frame = frame;
    }
    
    public void startGameWithCountdown() {
        if (!gameRunning) {
            gameRunning = true;
            frame.showGameScreen();
            startCountdownStart();
        }
    }
    
    public void stopGame() {
        if (gameRunning) {
            gameRunning = false;
            stopTimers();
            frame.getGamePanel().getTarget().setTargetVisible(false);
            frame.getCountdownOverlay().setVisible(false);
            frame.showSplashScreen();
        }
    }
    
    public void resetGame() {
        if (gameRunning) {
            startCountdownStart();
        } else {
            startGameWithCountdown();
        }
    }

    public void onTargetHit() {
        score++;
        frame.getGamePanel().updateScore(score);
    }
    
    //Countdown for reset
    private void startCountdownStart() {
        stopTimers();
        
        frame.getGamePanel().getTarget().setEnabled(false);
        frame.getGamePanel().getTarget().setTargetVisible(false);
        resetGameState();
        updateUI();
        
        countdownValue = 3;
        frame.getCountdownOverlay().showMessage("Get ready...");
        
        Timer readyTimer = new Timer(800, e -> {
            ((Timer) e.getSource()).stop();
            startNumberCountdown();
        });
        readyTimer.setRepeats(false);
        readyTimer.start();
    }
    
    private void startNumberCountdown() {
        countdownTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (countdownValue > 0) {
                    frame.getCountdownOverlay().showMessage(countdownValue + "");
                    countdownValue--;
                } else {
                    countdownTimer.stop();
                    frame.getCountdownOverlay().showMessage("GO!");
                    
                    Timer goTimer = new Timer(500, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent evt) {
                            ((Timer) evt.getSource()).stop();
                            startActualGame();
                        }
                    });
                    goTimer.setRepeats(false);
                    goTimer.start();
                }
            }
        });
        countdownTimer.start();
    }
    
    private void startActualGame() {
        initializeGameState();
            setDifficulty(currentDifficulty);
      
        frame.getCountdownOverlay().setVisible(false);
        initializeGameState();
        frame.getGamePanel().getTarget().moveTargetRandom();
        startGameTimer();
    }
    
    private void initializeGameState() {
        updateUI();
        frame.getGamePanel().getTarget().setEnabled(true);
        frame.getGamePanel().getTarget().setTargetVisible(true);
    }
    
    private void resetGameState() {
        score = 0;

        switch (currentDifficulty) {
            case "Easy" -> timeLeft = 45;
            case "Normal" -> timeLeft = 30;
            case "Hard" -> timeLeft = 20;
            default -> timeLeft = 30;
        }
    }
    
    private void stopTimers() {
        if (countdownTimer != null && countdownTimer.isRunning()) countdownTimer.stop();
        if (gameTimer != null && gameTimer.isRunning()) gameTimer.stop();
    } 
    
    private void startGameTimer() {
        if (gameTimer != null && gameTimer.isRunning()) gameTimer.stop();

        gameTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (timeLeft > 0) {
                    timeLeft--;
                    updateUI();
                } else {
                    ((Timer) e.getSource()).stop();
                    JOptionPane.showMessageDialog(frame, "Time’s up! Your score: " + score);
                    stopGame();
                }
            }
        });
        gameTimer.start();
    }
    
    private void updateUI() {
        frame.getGamePanel().updateScore(score);
        frame.getGamePanel().updateTime(timeLeft);
    }
    
    public void setDifficulty(String level) {
        this.currentDifficulty = level.toLowerCase();
        
        frame.getGamePanel().getTarget().setDifficulty(currentDifficulty);
        frame.getGamePanel().updateDifficulty(currentDifficulty);
        frame.getGamePanel().revalidate();
        frame.getGamePanel().repaint();

        switch (currentDifficulty) {
            case "Easy" -> {
                difficultyMultiplier = 0.8;
                timeLeft = 40;
            }
            case "Normal" -> {
                difficultyMultiplier = 1.0;
                timeLeft = 30;
            }
            case "Hard" -> {
                difficultyMultiplier = 1.5;
                timeLeft = 20;
            }
        }

        frame.getGamePanel().getTarget().setDifficulty(currentDifficulty);
        updateUI();
        
    if (!isGameRunning()) {
        JOptionPane.showMessageDialog(
            frame,
            "Difficulty set to " + currentDifficulty.toUpperCase() +
            "\nTime limit: " + timeLeft + " seconds",
            "Difficulty Changed",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
}    
    
    
    // Getters
    public boolean isGameRunning() { return gameRunning; }
    
}