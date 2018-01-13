package org.foi.jurbunic.bela.cards;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class CardOperation implements CardAlgorithm{

    protected List<Card> myCards;

    protected List<Card> diamond = new ArrayList<>();
    protected List<Card> heart = new ArrayList<>();
    protected List<Card> club = new ArrayList<>();
    protected List<Card> spade = new ArrayList<>();

    public void clearLists(){
        diamond.clear();heart.clear();club.clear();spade.clear();
    }

    protected void splitCardsByColour(Card card){
        Integer colour = card.getColour().getColourId();
        switch (colour){
            case 0:
                diamond.add(card);
                break;
            case 1:
                heart.add(card);
                break;
            case 2:
                club.add(card);
                break;
            case 3:
                spade.add(card);
                break;
        }
    }

    protected List<Card> filterByColour(int colour){
        return myCards.stream().filter(card -> card.getColour().getColourId() == colour).collect(Collectors.toList());
    }

}
