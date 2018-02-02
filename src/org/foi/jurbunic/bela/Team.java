package org.foi.jurbunic.bela;

import org.foi.jurbunic.bela.agents.Player;
import org.foi.jurbunic.bela.cards.Hand;

import java.util.ArrayList;
import java.util.List;

public class Team {

    private List<Hand> teamHands = new ArrayList<>();
    private List<Player> players = new ArrayList<>();

    public Team() { }

    public void addPlayer(Player player){
        players.add(player);
    }

    public void collectHand(Hand hand){
        teamHands.add(hand);
    }

    public int calculateScore(){
        int score = teamHands.stream().mapToInt(Hand::getHandValue).sum();
        teamHands.clear();
        return score;
    }


    public Player findPlayer(Integer playerId){
        Player searcedPlayer = null;
        for(Player player : players){
            if(player.getPlayerId().equals(playerId)){
                searcedPlayer = player;
            }
        }
        return searcedPlayer;
    }

    public boolean playerExists(Integer playerId){
        return players.stream().anyMatch(player -> player.getPlayerId().equals(playerId));
    }
}
