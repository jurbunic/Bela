package org.foi.jurbunic.bela;

import org.foi.jurbunic.bela.cards.Card;
import org.foi.jurbunic.bela.cards.Colour;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CommonKnowledge implements Serializable{

    private static CommonKnowledge INSTANCE = new CommonKnowledge();

    private int maxCards = 32;
    private boolean jackTrumpOut = false;
    private boolean nineTrumpOut = false;

    private List<Card> aces;

    private CommonKnowledge(){
        initAces();
    }

    public static CommonKnowledge getInstance(){
        return INSTANCE;
    }

    public void reset(){
        maxCards=32;
        jackTrumpOut = false;
        nineTrumpOut = false;
        initAces();
    }

    public void testCard(Card card){
        maxCards--;
        if(card.getColour().isTrump()) {
            if (card.getName().equals("Jack")) jackTrumpOut = true;
            if (card.getName().equals("Nine")) nineTrumpOut = true;
        }
        aceOut(card);
    }

    public void aceOut(Card card){
        if(!card.getName().equals("Ace")){
            return;
        }
        aces.removeIf(ace -> ace.getColour().equals(card.getColour()));
    }

    public boolean isAceOut(Colour colour){
        for(Card ace : aces){
            if(ace.getColour().equals(colour)) return true;
        }
        return false;
    }

    private void initAces(){
        aces = new ArrayList<>();
        for(int i=0;i<4;i++)
            aces.add(new Card("Ace", "A", Colour.getColour(i), 11));
    }

    public boolean isJackTrumpOut() {
        return jackTrumpOut;
    }

    public boolean isNineTrumpOut() {
        return nineTrumpOut;
    }

    public int getMaxCards() {
        return maxCards;
    }
}
