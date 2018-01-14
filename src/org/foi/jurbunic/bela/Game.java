package org.foi.jurbunic.bela;

import jade.core.AID;
import org.foi.jurbunic.bela.agents.Player;
import org.foi.jurbunic.bela.cards.BelaDeck;
import org.foi.jurbunic.bela.cards.Colour;
import org.foi.jurbunic.bela.cards.Deck;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private static List<Player> players = new ArrayList<>();
    private static Game INSTANCE;
    private static Deck deck;

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
}
