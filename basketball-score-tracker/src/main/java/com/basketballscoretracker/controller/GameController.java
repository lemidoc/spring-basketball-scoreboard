package com.basketballscoretracker.controller;

import com.basketballscoretracker.model.Game;
import com.basketballscoretracker.model.Team;
import com.basketballscoretracker.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/game")
public class GameController {
    @Autowired
    private GameService gameService;

    @PostMapping("/start")
    public Game startGame(@RequestParam String homeTeamName, @RequestParam String awayTeamName, @RequestParam int durationInSeconds) {
        Team homeTeam = new Team(homeTeamName);
        Team awayTeam = new Team(awayTeamName);
        return gameService.startGame(homeTeam, awayTeam, durationInSeconds);
    }

    @PostMapping("/pause")
    public Game pauseGame() {
        return gameService.pauseGame();
    }

    @PostMapping("/resume")
    public Game resumeGame() {
        return gameService.resumeGame();
    }

    @PostMapping("/update")
    public Game updateScore(@RequestParam int homeScore, @RequestParam int awayScore) {
        return gameService.updateScore(homeScore, awayScore);
    }

    @GetMapping("/status")
    public Game getGameStatus() {
        return gameService.getGameStatus();
    }
}
