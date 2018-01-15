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
        splitCardsByColour(myCards);
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
        
        currentBest = (int) diamond.stream().mapToDouble(Card::getValue).sum() + containsJackOrNine(diamond);
        bestColour = 0;
        colourSum = (int) heart.stream().mapToDouble(Card::getValue).sum() + containsJackOrNine(heart);

        if (currentBest<= colourSum){
            currentBest = colourSum;
            bestColour = 1;
        }
        colourSum = (int) club.stream().mapToDouble(Card::getValue).sum() + containsJackOrNine(club);
        if (currentBest <= colourSum){
            currentBest = colourSum;
            bestColour = 2;
        }
        colourSum = (int) spade.stream().mapToDouble(Card::getValue).sum() + containsJackOrNine(spade);
        if (currentBest <= colourSum){
            currentBest = colourSum;
            bestColour = 3;
        }
        return bestColour;
    }

    private int containsJackOrNine(List<Card> trump) {
        int bonus = 0;
        if(trump.stream().anyMatch(card -> card.getName().equals("Jack"))){
            bonus += 3;
            if(trump.stream().anyMatch(card -> card.getName().equals("Nine"))){
                bonus += 10;
            }
        }
        return bonus;
    }

}
