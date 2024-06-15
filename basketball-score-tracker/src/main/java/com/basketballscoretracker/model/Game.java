package com.basketballscoretracker.model;

import java.util.Timer;
import java.util.TimerTask;

public class Game {
    private final Team homeTeam;
    private final Team awayTeam;
    private int homeTeamScore;
    private int awayTeamScore;
    private int durationInSeconds;
    private Timer timer;
    private GameStatus status;

    public Game(Team homeTeam, Team awayTeam, int durationInSeconds) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.durationInSeconds = durationInSeconds;
        this.homeTeamScore = 0;
        this.awayTeamScore = 0;
        this.status = GameStatus.PAUSED;
        this.timer = new Timer();
    }

    public enum GameStatus {
        ONGOING,
        FINISHED,
        PAUSED
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    public synchronized void startGame() {
        if (status == GameStatus.FINISHED) {
            System.out.println("Cannot start, the game is already finished.");
        } else if (status == GameStatus.PAUSED) {
            status = GameStatus.ONGOING;
            if (timer == null) {
                timer = new Timer();
                startTimer();
            }
            System.out.println("Game resumed.");
        } else if (status == GameStatus.ONGOING) {
            System.out.println("The game is already ongoing.");
        } else {
            timer = new Timer();
            startTimer();
            System.out.println("Game starts now.");
            status = GameStatus.ONGOING;
        }
    }

    public void pauseGame() {
        if (status == GameStatus.ONGOING) {
            status = GameStatus.PAUSED;
            stopTimer();
            System.out.println("Game paused.");
        } else if (status == GameStatus.FINISHED) {
            System.out.println("Cannot pause, the game is already finished.");
        } else {
            System.out.println("The game is already paused.");
        }
    }

    private void startTimer() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                synchronized (Game.this) {
                    durationInSeconds--;
                    if (durationInSeconds <= 0) {
                        stopTimer();
                        status = GameStatus.FINISHED;
                        System.out.println("Time's up. Game over!");
                    } else if (durationInSeconds % 5 == 0) {
                        System.out.println("Time remaining: " + getTimeRemainingFormatted());
                    }
                }
            }
        }, 0, 1000);
    }

    private synchronized void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public int getHomeTeamScore() {
        return homeTeamScore;
    }

    public int getAwayTeamScore() {
        return awayTeamScore;
    }

    public int getDurationInSeconds() {
        return durationInSeconds;
    }

    public void updateScore(int homeTeamScore, int awayTeamScore) {
        this.homeTeamScore += homeTeamScore;
        this.awayTeamScore += awayTeamScore;
    }

    public String getTimeRemainingFormatted() {
        int minutes = durationInSeconds / 60;
        int seconds = durationInSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    public String getHomeTeamName() {
        return homeTeam.getTeamName();
    }

    public String getAwayTeamName() {
        return awayTeam.getTeamName();
    }
}
