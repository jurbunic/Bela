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
    private static Game INSTANCE;
    private static Deck deck;
    private Hand hand = new Hand();
    private Team team;

    private static Integer playerOnTurn = 0;

    private static Colour trump;

    private Game() {
        deck = new BelaDeck();
        team = new Team();
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
            try{
                if(players.get(i).getHands().size()>0){
                    players.get(i).getHands().clear();
                }
                players.get(i).setMyCards(deck.deal());
                if(playerOnTurn==i)
                    players.get(i).setStatus(2);
                else players.get(i).setStatus(0);
            }catch (Exception e){
                System.out.println("Debug");
            }

        }
    }

    public void registerPlayer(Player player){
        players.add(player);
        int playerSize = players.size();
        if(players.size()>=4){
            dealCards();
            team.fillTeams(players);
            players.get(0).setStatus(1);
        }
    }

    public Colour getTrumpColour(){
        return trump;
    }

    public void setTrump(Player player,Colour trump){
        team.playerCalled(player);
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
                team.calculateWinner();
                System.out.print("New round? (y/n) >");
                Scanner scanner = new Scanner(System.in);
                String input = scanner.next();
                if (input.equals("y")) {
                    playerOnTurn++;
                    dealCards();
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
}
