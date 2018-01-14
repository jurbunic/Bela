package org.foi.jurbunic.bela.cards;

import org.foi.jurbunic.bela.Game;

import java.util.Comparator;
import java.util.List;

public class NextCardAlgoritm extends CardOperation implements CardAlgorithm {

    private List<Card> cardsOnTable;
    private Game game = Game.getInstance();

    public NextCardAlgoritm(List<Card> myCards) {
        this.myCards = myCards;
    }

    @Override
    public void setCars(List<Card> cards) {
        cardsOnTable = cards;
    }

    @Override
    public Card bestAction() {
        Card bestCard = null;
        if (cardsOnTable == null || cardsOnTable.size()==0){
            bestCard = choseCard();
        }else{
            bestCard = choseCard(cardsOnTable);
        }
        return bestCard;
    }

    private Card choseCard(List<Card> cardsOnTable){
        Card cardToPlay = null;
        if(cardsOnTable.size() == 0){
            return choseCard();
        }
        Colour colorToPlay = cardsOnTable.get(0).getColour();
        boolean isSliced = false;
        Card lastCardToWatch = null;
        for(int i=0;i<cardsOnTable.size();i++){
            Card cardToWatch = cardsOnTable.get(i);
            Colour colourCardOnTable = cardToWatch.getColour();
            if(!colorToPlay.equals(colourCardOnTable)) {
                if(colourCardOnTable.isTrump()){
                    isSliced=true;
                    //lastCardToWatch = cardToWatch;
                }
            }else{
                if(!isSliced){
                    lastCardToWatch = cardToWatch;
                }
            }
        }
        final Integer valueOfLastCard = lastCardToWatch.getValue();
        if(filterByColour(lastCardToWatch.getColour().getColourId()).stream().filter(card -> card.getValue() > valueOfLastCard).findFirst().isPresent()){
            cardToPlay = filterByColour(lastCardToWatch.getColour().getColourId())
                    .stream().filter(card -> card.getValue() > valueOfLastCard).findFirst().get();
        }
        if(filterByColour(game.getTrumpColour().getColourId()).size()>0){
            cardToPlay = filterByColour(game.getTrumpColour().getColourId()).get(0);
        }else {
            cardToPlay = myCards.stream().min(Comparator.comparingInt(Card::getValue)).get();
        }
        return cardToPlay;
    }

    private Card choseCard(){
        Card cardToPlay = null;
        Integer risk = 100;
        for(int i=0;i<4;i++){
            List<Card> cards = filterByColour(i);
            if(cards.size()>=4){
                if(cards.get(0).getColour().isTrump()){
                    cardToPlay = startTrump(cards);
                }else {
                    risk = 80;
                    cardToPlay = cards.stream().min(Comparator.comparingInt(Card::getValue)).get();
                }
            }else{
                risk = 50;
                if(cards.size()>0)
                    cardToPlay = cards.stream().max(Comparator.comparingInt(Card::getValue)).get();
            }
        }
        return cardToPlay;
    }

    private Card startTrump(List<Card> cards){
        if (cards.stream().anyMatch(card -> card.getName().equals("Jack"))){
            return cards.stream().filter(card -> card.getName().equals("Jack")).findFirst().get();
        }
        return cards.stream().min(Comparator.comparingInt(Card::getValue)).get();
    }
}
