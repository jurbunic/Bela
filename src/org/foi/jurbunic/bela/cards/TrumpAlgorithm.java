package org.foi.jurbunic.bela.cards;



import java.util.List;

public class TrumpAlgorithm extends CardOperation implements CardAlgorithm{

    public TrumpAlgorithm(List<Card> myCards) {
        this.myCards = myCards;
    }

    @Override
    public Card bestAction() {
        return findBest();
    }

    @Override
    public void setCars(List<Card> cards) {
        this.myCards = cards;
    }

    private Card findBest(){
        for (Card card : myCards){
            splitCardsByColour(card);
        }
        Integer bestColour = calculateBestColour();
        switch (bestColour){
            case 0:
                return diamond.get(0);
            case 1:
                return heart.get(0);
            case 2:
                return club.get(0);
            default:
                return spade.get(0);
        }
    }

    private Integer calculateBestColour(){
        Integer bestColour;
        Integer currentBest;
        Integer colourSum;
        
        currentBest = diamond.stream().mapToInt(Card::getValue).sum();
        bestColour = 0;
        colourSum = heart.stream().mapToInt(Card::getValue).sum();

        if (currentBest<= colourSum){
            currentBest = colourSum;
            bestColour = 1;
        }
        colourSum = club.stream().mapToInt(Card::getValue).sum();
        if (currentBest <= colourSum){
            currentBest = colourSum;
            bestColour = 2;
        }
        colourSum = spade.stream().mapToInt(Card::getValue).sum();
        if (currentBest <= colourSum){
            currentBest = colourSum;
            bestColour = 3;
        }
        return bestColour;
    }

}
