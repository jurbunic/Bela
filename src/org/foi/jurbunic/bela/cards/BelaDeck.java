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
            cards.add(new Card("Seven", Colour.getColour(i), 0));
            cards.add(new Card("Eight", Colour.getColour(i), 0));
            cards.add(new Card("Nine", Colour.getColour(i), 0));
            cards.add(new Card("Ten", Colour.getColour(i), 10));
            cards.add(new Card("Jack", Colour.getColour(i), 2));
            cards.add(new Card("Dame", Colour.getColour(i), 3));
            cards.add(new Card("King", Colour.getColour(i), 4));
            cards.add(new Card("Ace", Colour.getColour(i), 11));
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
