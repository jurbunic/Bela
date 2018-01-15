package org.foi.jurbunic.bela.cards;

import org.foi.jurbunic.bela.Game;

import java.util.*;
import java.util.stream.Collectors;

public class NextCardAlgoritm extends CardOperation implements CardAlgorithm {

    private boolean canTakeHand;
    private int chanceOfTaking;

    List<Card> possibleCards = null;
    List<Card> trumps = null;

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
        trumps = filterByColour(game.getTrumpColour().getColourId());
        Card bestCard = nextCardLogic(cardsOnTable);
        if(bestCard == null){
            System.out.println("Debug");
        }
        return nextCardLogic(cardsOnTable);
    }

    private Card nextCardLogic(List<Card> cards){
        int chance = 0;
        boolean needUber = true;
        boolean sliced = false;
        Card bestCard = null;
        Card firstCard = null;
        Card uber = null;
        Colour colour = null;
        if(cards.size()>0){
            firstCard = cards.get(0);
            uber = firstCard;
            colour =firstCard.getColour();
            possibleCards = filterByColour(colour.getColourId());
            if(trumps.size()<1 && possibleCards.size()<1){
                return myCards.stream().min(Comparator.comparingDouble(Card::getValue)).get();
            }
            for(Card card : cards){
                if(!card.getColour().isTrump() && !card.getColour().equals(colour))
                    continue;
                if(card.getColour().isTrump()){
                    if(card.getColour().equals(colour)){
                        Card finalUber2 = uber;
                        if(finalUber2 == null){

                        }else {
                            if (trumps.stream().noneMatch(trump -> trump.getValue()>= finalUber2.getValue())){
                                needUber=false;
                            }
                        }

                    }else {
                        sliced = true;
                        if (trumps.stream().noneMatch(trump -> trump.getValue()>=card.getValue())){
                            needUber = false;
                            continue;
                        }
                    }

                }
                if(sliced && needUber){
                    uber = card;
                }
                if(needUber){
                    if(uber.getValue()<=card.getValue()) uber = card;
                    if(possibleCards.size()>0) {
                        Card finalUber1 = uber;
                        if(possibleCards.stream().noneMatch(myCard -> myCard.getValue()>= finalUber1.getValue()))
                            needUber = false;
                    }
                }
                if(!needUber){
                    uber = null;
                }

            }
        }else {
            return choseCard();
        }
        if(uber == null){
            if(possibleCards.size()>0){
                bestCard = possibleCards.stream().min(Comparator.comparingDouble(Card::getValue)).get();
            }else if(trumps.size()>0){
                bestCard = trumps.stream().min(Comparator.comparingDouble(Card::getValue)).get();
            }else {
                bestCard = myCards.stream().min(Comparator.comparingDouble(Card::getValue)).get();
            }
        }
        else {
            List<Card> possible = null;
            if(possibleCards.size()>0){
                Card finalUber = uber;
                possible = possibleCards.stream().filter(card -> card.getValue()> finalUber.getValue()).collect(Collectors.toList());
                if(possible.size()>0){
                    bestCard = possible.get(0);
                }
            }else if(trumps.size()>0){
                Card finalUber = uber;
                if(trumps.stream().min(Comparator.comparingDouble(Card::getValue)).isPresent())
                    bestCard = trumps.stream().min(Comparator.comparingDouble(Card::getValue)).get();
                /*if(possible.size()>0){
                    bestCard = possible.get(0);
                }*/
            }else {
                if(myCards.stream().min(Comparator.comparingDouble(Card::getValue)).isPresent())
                    bestCard = myCards.stream().min(Comparator.comparingDouble(Card::getValue)).get();
            }
        }
        return bestCard;
    }



    private Card choseCard(){
        Card bestCard;
        splitCardsByColour(myCards);
        double riskDiamon = diamond.size() / 8d;
        double riskHeart = heart.size() / 8d;
        double riskClub = club.size() / 8d;
        double riskSpade = spade.size() / 8d;

        calculateTrumpStartRisk(riskDiamon, diamond);
        calculateTrumpStartRisk(riskHeart, heart);
        calculateTrumpStartRisk(riskClub, club);
        calculateTrumpStartRisk(riskSpade, spade);

        calculateNonTrumpStartRisk(riskDiamon, diamond);
        calculateNonTrumpStartRisk(riskDiamon, heart);
        calculateNonTrumpStartRisk(riskDiamon, club);
        calculateNonTrumpStartRisk(riskDiamon, spade);

        HashMap<Double, List<Card>> risks = new HashMap<>();
        risks.put(riskDiamon,diamond);
        risks.put(riskHeart,heart);
        risks.put(riskClub,club);
        risks.put(riskSpade,spade);

        List<Card> good = null;
        Map.Entry<Double, List<Card>> lowest = null;
        for(Map.Entry<Double, List<Card>> entry : risks.entrySet()){
            if(entry.getKey() == 0){
                continue;
            }
            if(lowest == null){
                lowest = entry;
                good = entry.getValue();
                continue;
            }
            if(lowest.getKey()>entry.getKey()){
                lowest = entry;
                good = entry.getValue();
            }
        }
        if(good.get(0).getColour().isTrump()){
            if(good.stream().anyMatch(card -> card.getName().equals("Jack"))){
                bestCard = good.stream().filter(card -> card.getName().equals("Jack")).findFirst().get();
            }else {
                bestCard = good.stream().min(Comparator.comparingDouble(Card::getValue)).get();
            }
        }else {
            if(good.stream().anyMatch(card -> card.getName().equals("Ace"))){
                bestCard = good.stream().filter(card -> card.getName().equals("Ace")).findFirst().get();
            }else {
                bestCard = good.stream().min(Comparator.comparingDouble(Card::getValue)).get();
            }
        }

        return bestCard;
    }

    private void calculateNonTrumpStartRisk(double baseRisk, List<Card> cards){

        if(cards.size()==0){
            return;
        }
        if(cards.get(0).getColour().isTrump()){
            return;
        }else {
            if(cards.stream().anyMatch(card -> card.getName().equals("Ace"))){
                baseRisk *= 0.7d;
            }
        }
    }

    private void calculateTrumpStartRisk(double baseRisk, List<Card> trumps){
        if(trumps.size()==0){
            return;
        }
        if(trumps.get(0).getColour().isTrump()){
            if(trumps.stream().anyMatch(card -> card.getName().equals("Jack"))){
                baseRisk *= 0.5d;
                if(trumps.stream().anyMatch(card -> card.getName().equals("Nine"))){
                    baseRisk *= 0.7d;
                }
            }
        }
    }

}
