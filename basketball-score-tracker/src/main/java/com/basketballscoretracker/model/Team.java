package com.basketballscoretracker.model;

import java.util.ArrayList;
import java.util.List;

public class Team {
    private List<Player> players;
    private String teamName;

    public Team(String teamName) {
        this.teamName = teamName;
        this.players = new ArrayList<>();
    }

    public String getTeamName() {
        return teamName;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void addPlayer(Player player) {
        if (players.size() < 5) { // Assuming a team can have up to 5 players
            players.add(player);
        } else {
            System.out.println("Cannot add more than 5 players to the team.");
        }
    }

    public void printTeam() {
        System.out.println("Team: " + this.teamName);
        for (Player player : players) {
            System.out.println(player);
        }
    }
}
