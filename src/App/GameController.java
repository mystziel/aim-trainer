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
    private String currentDifficulty = "Normal";
    
    public GameController(GameFrame frame) {
        this.frame = frame;
    }
    
    public void startGameWithCountdown() {
        if (!gameRunning) {
            gameRunning = true;
            frame.showGameScreen();
            startCountdown();
        }
    }
    
    public void stopGame() {
        if (gameRunning) {
            gameRunning = false;
            stopTimers();
            resetTargetVisibility();
            frame.showSplashScreen();
            resetTimeForCurrentDifficulty();
            updateUI();
        }
    }

    public void resetGame() {
        if (gameRunning) {
            startCountdown();
        } else {
            startGameWithCountdown();
        }
    }

    public void onTargetHit() {
        score++;
        frame.getGamePanel().updateScore(score);
    }
    
    public void setDifficulty(String level) {
        this.currentDifficulty = level;
        applyDifficultySettings();
        updateGameComponents();
        
        if (!isGameRunning()) {
            showDifficultyChangeMessage();
        } else {
            restartGameTimer();
        }
    }
    
    public boolean isGameRunning() { 
        return gameRunning; 
    }
    
    // Private methods
    private void startCountdown() {
        stopTimers();
        resetGameState();
        setupCountdown();
    }
    
    private void setupCountdown() {
        frame.getGamePanel().getTarget().setEnabled(false);
        frame.getGamePanel().getTarget().setTargetVisible(false);
        
        countdownValue = 3;
        frame.getCountdownOverlay().showMessage("Get ready...");
        
        Timer readyTimer = createTimer(800, e -> startNumberCountdown(), false);
        readyTimer.start();
    }
    
    private void startNumberCountdown() {
        countdownTimer = createTimer(1000, new CountdownAction(), true);
        countdownTimer.start();
    }
    
    private void startActualGame() {
        initializeGameState();
        frame.getCountdownOverlay().setVisible(false);
        frame.getGamePanel().getTarget().moveTargetRandom();
        startGameTimer();
    }
    
    private void initializeGameState() {
        frame.getGamePanel().getTarget().setEnabled(true);
        frame.getGamePanel().getTarget().setTargetVisible(true);
        updateUI();
    }
    
    private void resetGameState() {
        score = 0;
        resetTimeForCurrentDifficulty();
        updateUI();
    }
    
    private void resetTimeForCurrentDifficulty() {
        timeLeft = switch (currentDifficulty.toLowerCase()) {
            case "easy" -> 45;
            case "hard" -> 20;
            default -> 30;
        };
    }
    
    private void stopTimers() {
        if (countdownTimer != null) {
            countdownTimer.stop();
        }
        if (gameTimer != null) {
            gameTimer.stop();
        }
    }
    
    private void startGameTimer() {
        stopTimers();
        
        gameTimer = createTimer(1000, new GameTimerAction(), true);
        gameTimer.start();
    }
    
    private void restartGameTimer() {
        stopTimers();
        startGameTimer();
    }
    
    private void updateUI() {
        if (timeLeft < 0) timeLeft = 0;
        frame.getGamePanel().updateScore(score);
        frame.getGamePanel().updateTime(timeLeft);
    }
    
    private void applyDifficultySettings() {
        switch (currentDifficulty.toLowerCase()) {
            case "easy" -> {
                timeLeft = 45;
            }
            case "normal" -> {
                timeLeft = 30;
            }
            case "hard" -> {
                timeLeft = 20;
            }
        }
    }
    
    private void updateGameComponents() {
        frame.getGamePanel().getTarget().setDifficulty(currentDifficulty);
        frame.getGamePanel().updateDifficulty(currentDifficulty);
        updateUI();
    }
    
    private void showDifficultyChangeMessage() {
        JOptionPane.showMessageDialog(
            frame,
            "Difficulty set to " + currentDifficulty.toUpperCase()
            + "\nTime limit: " + timeLeft + " seconds",
            "Difficulty Changed",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
    
    private void resetTargetVisibility() {
        frame.getGamePanel().getTarget().setTargetVisible(false);
        frame.getCountdownOverlay().setVisible(false);
    }
    
    private Timer createTimer(int delay, ActionListener listener, boolean repeats) {
        Timer timer = new Timer(delay, listener);
        timer.setRepeats(repeats);
        return timer;
    }
    
    private class CountdownAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (countdownValue > 0) {
                frame.getCountdownOverlay().showMessage(countdownValue + "");
                countdownValue--;
            } else {
                countdownTimer.stop();
                frame.getCountdownOverlay().showMessage("GO!");
                
                Timer goTimer = createTimer(500, evt -> startActualGame(), false);
                goTimer.start();
            }
        }
    }
    
    private class GameTimerAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (timeLeft > 0) {
                timeLeft--;
                updateUI();
            } else {
                gameTimer.stop();
                JOptionPane.showMessageDialog(frame, "Time's up! Your score: " + score);
                stopGame();
            }
        }
    }
}