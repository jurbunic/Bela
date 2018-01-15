package org.foi.jurbunic.bela.cards;

import org.foi.jurbunic.bela.agents.Player;

import java.util.ArrayList;
import java.util.List;

public class Team {
    private List<Player> team1 = new ArrayList<>();
    private List<Player> team2 = new ArrayList<>();

    private Player playerCalled;
    private static final int MAX_SCORE = 162;

    public Team(){ }

    public void fillTeams(List<Player> players){
        for(int i=0;i<players.size();i++){
            if(i%2!=0){
                team2.add(players.get(i));
            }else {
                team1.add(players.get(i));
            }
        }
    }

    public void playerCalled(Player player){
        playerCalled = player;
    }

    public void calculateWinner(){
        Integer scoreTeam1 = calculateTeamScore(team1);
        Integer scoreTeam2 = calculateTeamScore(team2);
        printWinner(scoreTeam1,scoreTeam2);
    }

    private void printWinner(Integer team1Score, Integer team2Score){
        System.out.println("Team 1: result -> " + team1Score);
        System.out.println("Team 2: result -> " + team2Score);
        if(team1.stream().anyMatch(player -> player.getPlayerId().equals(playerCalled.getPlayerId()))){
            if(team1Score<=team2Score){
                System.out.println("Winner: Team 2 (Team 1 falls)");
            }else {
                System.out.println("Winner: Team 1 (Team 1 pass)");
            }
        }else {
            if(team2Score<=team1Score){
                System.out.println("Winner: Team 1 (Team 2 falls)");
            }else {
                System.out.println("Winner: Team 2 (Team 2 pass)");
            }
        }
    }

    private Integer calculateTeamScore(List<Player> team){
        Integer score = 0;
        for (Player player : team){
            for(Hand hand : player.getHands()){
                score += hand.getHandValue();
            }
        }
        return score;
    }
}
