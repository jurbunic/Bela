package org.foi.jurbunic.bela;

import org.foi.jurbunic.bela.agents.Player;
import org.foi.jurbunic.bela.cards.BelaDeck;
import org.foi.jurbunic.bela.cards.Deck;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private static List<Player> players = new ArrayList<>();
    private static Game INSTANCE;
    private static Deck deck;

    private static Integer playerOnCall = 0;
    private static Integer playerOnTurn = 0;

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
            players.get(i).setMyCards(deck.deal());
        }
    }

    public void registerPlayer(Player player){
        players.add(player);
        int playerSize = players.size();
        if(players.size()>=4){
            dealCards();
        }
    }

    public int getPlayerOnTurn(){
        return playerOnTurn;
    }

    public void nextTurn(){
        playerOnTurn++;
        if(playerOnTurn>3){
            playerOnTurn=0;
        }
    }
}
