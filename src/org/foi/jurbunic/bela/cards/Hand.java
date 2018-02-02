package org.foi.jurbunic.bela.cards;

import org.foi.jurbunic.bela.agents.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Hand implements Serializable {

    private static int handNumber = 1;
    private boolean winnerDecided = false;
    private int winnerId;
    private HashMap<Player,Card> cardsInPlay = new HashMap<>();
    private boolean last=false;

    public Hand(){
        cardsInPlay = new HashMap<>();
    }

    public Hand(Hand hand){
        this.cardsInPlay = hand.cardsInPlay;
        this.winnerId = hand.winnerId;
        this.winnerDecided = hand.winnerDecided;
        this.last = hand.last;
    }

    public void addCard(Player player, Card card){
        winnerDecided = false;
        last = false;
        cardsInPlay.put(player, card);
        if(cardsInPlay.size()==4){
            decideWinner();
            handNumber++;
            if(handNumber>8){
                handNumber=1;
            }
        }
    }

    private void decideWinner(){
        Card bestCard = null;
        Player winner = null;
        for (Map.Entry<Player, Card> entry : cardsInPlay.entrySet()){
            Card cardToCompare = entry.getValue();
            if(bestCard == null) {
                bestCard = cardToCompare;
                winner = entry.getKey();
            }
            else {
                if(cardToCompare.getColour().isTrump()){
                    if(bestCard.getColour().isTrump()){
                        if(bestCard.getValue() < cardToCompare.getValue()){
                            bestCard = cardToCompare;
                            winner = entry.getKey();
                        }
                    }else {
                        bestCard = cardToCompare;
                        winner = entry.getKey();
                    }
                }else {
                    if(!bestCard.getColour().isTrump()){
                        if(bestCard.getValue() < cardToCompare.getValue()){
                            bestCard = cardToCompare;
                            winner = entry.getKey();
                        }
                    }
                }
            }
        }
        if(handNumber==8){
            last = true;
        }
        winnerDecided = true;
        winnerId = winner.getPlayerId();
    }

    public List<Card> getCardsInPlay(){
        List<Card> cards = new ArrayList<>();
        for(Map.Entry<Player, Card> entry : cardsInPlay.entrySet()){
            cards.add(entry.getValue());
        }
        return cards;
    }

    public boolean isWinnerDecided() {
        return winnerDecided;
    }

    public int getWinnerId() {
        return winnerId;
    }

    public int getHandNumber() {
        return handNumber;
    }

    public int getHandValue(){
        Integer value = 0;
        for(Map.Entry<Player, Card> entry : cardsInPlay.entrySet()){
            value += (int) entry.getValue().getValue();
        }
        if(last) value+=10;
        return value;
    }
}
