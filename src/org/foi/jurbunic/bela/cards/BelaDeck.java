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
            cards.add(new Card("Seven","7", Colour.getColour(i), 0.1d));
            cards.add(new Card("Eight","8", Colour.getColour(i), 0.2d));
            cards.add(new Card("Nine","9", Colour.getColour(i), 0.3d));
            cards.add(new Card("Ten","10", Colour.getColour(i), 10d));
            cards.add(new Card("Jack","J", Colour.getColour(i), 2d));
            cards.add(new Card("Dame","D", Colour.getColour(i), 3d));
            cards.add(new Card("King","K", Colour.getColour(i), 4d));
            cards.add(new Card("Ace","A", Colour.getColour(i), 11d));
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
            lastIndex = 0;
        }
        for(int i=lastIndex;i<(lastIndex+EVEN);i++){
            playerDeck.add(cards.get(i));
        }
        lastIndex+=EVEN;
        return playerDeck;
    }

}
