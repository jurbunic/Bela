package org.foi.jurbunic.bela.cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BelaDeck implements Deck{

    private static int EVEN;
    private List<Card> cards = new ArrayList<>();
    private int lastIndex = 0;
    public BelaDeck(){
        initCards();
    }

    private void initCards(){
        // i < 4 because there is 4 colours
        for (int i=0;i<4;i++){
            cards.add(new Card("Seven", i, 0));
            cards.add(new Card("Eight", i, 0));
            cards.add(new Card("Nine", i, 0));
            cards.add(new Card("Ten", i, 10));
            cards.add(new Card("Jack", i, 2));
            cards.add(new Card("Dame", i, 3));
            cards.add(new Card("King", i, 4));
            cards.add(new Card("Ace", i, 11));
        }
    }
    @Override
    public void shuffle(){
        Collections.shuffle(cards);
    }

    @Override
    public void splitEvenly(int numOfPlayers) {
        int sizeOfDeck = cards.size();
        EVEN = sizeOfDeck / numOfPlayers;
    }

    @Override
    public List<Card> getAllCards() {
        return cards;
    }

    @Override
    public List<Card> deal() {
        List<Card> playerDeck = new ArrayList<>();
        if(lastIndex >= cards.size()){
            return null;
        }
        for(int i=lastIndex;i<(lastIndex+EVEN);i++){
            playerDeck.add(cards.get(i));
        }
        lastIndex+=EVEN;
        return playerDeck;
    }

}
