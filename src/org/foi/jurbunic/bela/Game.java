package org.foi.jurbunic.bela;

import jade.core.AID;
import org.foi.jurbunic.bela.agents.Player;
import org.foi.jurbunic.bela.cards.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Game implements Serializable{

    private static List<Player> players = new ArrayList<>();
    private static Game INSTANCE;
    private static Deck deck;
    private Hand hand = new Hand();

    private static Integer playerOnTurn = 0;

    private static Colour trump;

    private Game() {
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
            try{
                players.get(i).setMyCards(deck.deal());
                if(playerOnTurn==i)
                    players.get(i).setStatus(2);
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
            players.get(0).setStatus(1);
        }
    }

    public Colour getTrumpColour(){
        return trump;
    }

    public void setTrump(Colour trump){
        Game.trump = trump;
    }

    public AID getNextPlayer(Integer myId){
        Integer nextPlayer = myId+1;
        if(nextPlayer>3){
            nextPlayer = 0;
        }
        return players.get(nextPlayer).getAID();
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
}
