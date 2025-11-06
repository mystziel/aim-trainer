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
    
    public void setDifficulty(String level) {
        switch (level.toLowerCase()) {
            case "easy" -> difficultyMultiplier = 0.8;
            case "normal" -> difficultyMultiplier = 1.0;
            case "hard" -> difficultyMultiplier = 1.5;
        }
        frame.getGamePanel().getTarget().setDifficulty(difficultyMultiplier);
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
        frame.getCountdownOverlay().setVisible(false);
        initializeGameState();
        frame.getGamePanel().getTarget().moveTargetRandom();
    }
    
    private void initializeGameState() {
        updateUI();
        frame.getGamePanel().getTarget().setEnabled(true);
        frame.getGamePanel().getTarget().setTargetVisible(true);
    }
    
    private void resetGameState() {
        score = 0;
        timeLeft = 30;
    }
    
    private void stopTimers() {
        if (countdownTimer != null && countdownTimer.isRunning()) countdownTimer.stop();
    } //ADD THE STOP TIMER FOR THE COUNTDOWN IN GAME
    
    private void updateUI() {
        frame.getGamePanel().updateScore(score);
        frame.getGamePanel().updateTime(timeLeft);
    }
    
    // Getters
    public boolean isGameRunning() { return gameRunning; }
}