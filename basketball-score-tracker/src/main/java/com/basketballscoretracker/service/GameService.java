package com.basketballscoretracker.service;

import com.basketballscoretracker.model.Game;
import com.basketballscoretracker.model.Team;
import org.springframework.stereotype.Service;

@Service
public class GameService {
    private Game game;

    public Game startGame(Team homeTeam, Team awayTeam, int durationInSeconds) {
        game = new Game(homeTeam, awayTeam, durationInSeconds);
        game.startGame();
        return game;
    }

    public Game pauseGame() {
        if (game != null) {
            game.pauseGame();
        }
        return game;
    }

    public Game resumeGame() {
        if (game != null) {
            game.startGame();
        }
        return game;
    }

    public Game updateScore(int homeTeamScore, int awayTeamScore) {
        if (game != null) {
            game.updateScore(homeTeamScore, awayTeamScore);
        }
        return game;
    }

    public Game getGameStatus() {
        return game;
    }
}
