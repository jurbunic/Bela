package org.foi.jurbunic.bela;

import org.foi.jurbunic.bela.agents.Player;
import org.foi.jurbunic.bela.cards.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game implements Serializable{

    private static List<Player> players = new ArrayList<>();
    private List<Player> playersWaitList = new ArrayList<>();
    private List<Team> teams= new ArrayList<>();
    private static Player playerCalled;
    private static Game INSTANCE;
    private static Deck deck;
    private Hand hand = new Hand();

    private static Integer playerOnTurn = 0;

    private static Colour trump;

    private Game() {
        for(int i=0; i < 2; i++) teams.add(new Team());
        deck = new BelaDeck();
    }

    public static Game getInstance() {
        if(INSTANCE == null){
            INSTANCE = new Game();
        }
        return INSTANCE;
    }

    private void dealCards(){
        deck.splitEvenly(players.size());
        deck.shuffle();
        for(int i=0;i<players.size();i++){
            players.get(i).setMyCards(deck.deal());
            if(playerOnTurn==i)
                players.get(i).setStatus(2);
            else players.get(i).setStatus(0);
        }
    }

    public void registerPlayer(Player player){
        players.add(player);
        int playerSize = players.size();
        teams.get(playerSize%2).addPlayer(player);
        player.setPlayerTeam(playerSize%2);
        if(players.size()>=4){
            dealCards();
            players.get(0).setStatus(1);
        }
    }

    public Colour getTrumpColour(){
        return trump;
    }

    public void setTrump(Player player,Colour trump){
        Game.playerCalled = player;
        Game.trump = trump;
    }


    public void setNextPlayer(Integer myId){
        Integer nextPlayer = myId+1;
        if(nextPlayer>3){
            nextPlayer = 0;
        }
        if(hand.isWinnerDecided()){
            for(Player player : players){
                if( player.getPlayerId() == hand.getWinnerId()){
                    System.out.println("Winner: ["+player.getPlayerId()+"]");
                    teams.get(player.getPlayerTeam()).collectHand(new Hand(hand));
                    player.setStatus(2);
                    hand = new Hand();
                    return;
                }
            }
        }
        players.get(nextPlayer).setStatus(2);
    }


    public synchronized void playInHand(Player player, Card card){
        hand.addCard(player, card);
    }

    public Hand getHand(){
        return hand;
    }

    public void waitNewGame(Player p){
        playersWaitList.add(p);
        if(playersWaitList.size()==4){
            while (true) {
                calculateWinner();
                System.out.print("New round? (y/n) >");
                Scanner scanner = new Scanner(System.in);
                String input = scanner.next();
                if (input.equals("y")) {
                    playerOnTurn++;
                    dealCards();
                    sleep();
                    playersWaitList.clear();
                    break;
                }
                if (input.equals("n")) {
                    for(Player player : playersWaitList){
                        player.setStatus(4);
                    }
                    try {
                        Thread.sleep(5000);
                        System.exit(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }
    }

    private void sleep(){
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void calculateWinner(){
        Integer team1Score = teams.get(0).calculateScore();
        Integer team2Score = teams.get(1).calculateScore();

        System.out.println("Team 1: result -> " + team1Score);
        System.out.println("Team 2: result -> " + team2Score);
        if(teams.get(0).playerExists(playerCalled.getPlayerId())){
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
}
