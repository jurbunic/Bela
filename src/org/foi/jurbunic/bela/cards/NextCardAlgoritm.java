package org.foi.jurbunic.bela.cards;

import org.foi.jurbunic.bela.CommonKnowledge;
import org.foi.jurbunic.bela.Game;

import java.util.*;
import java.util.stream.Collectors;

public class NextCardAlgoritm extends CardOperation {

    private CommonKnowledge knowledge = CommonKnowledge.getInstance();
    private List<Card> possibleCards = null;
    private List<Card> trumps = null;

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
        return nextCardLogic(cardsOnTable);
    }

    private Card nextCardLogic(List<Card> cards){
        Card bestCard = null;
        Card firstCard;
        Card uber;
        Colour colour;
        if(cards.size()>0){
            firstCard = cards.get(0);
            colour =firstCard.getColour();
            possibleCards = filterByColour(colour.getColourId());
            if(trumps.size()<1 && possibleCards.size()<1){
                return myCards.stream().min(Comparator.comparingDouble(Card::getValue)).get();
            }
            uber = findUber(cards);
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
        double devider = knowledge.getMaxCards()/4d;
        double riskDiamon = diamond.size() / devider;
        double riskHeart = heart.size() / devider;
        double riskClub = club.size() / devider;
        double riskSpade = spade.size() / devider;

        riskDiamon = calculateTrumpStartRisk(riskDiamon, diamond);
        riskHeart = calculateTrumpStartRisk(riskHeart, heart);
        riskClub = calculateTrumpStartRisk(riskClub, club);
        riskSpade = calculateTrumpStartRisk(riskSpade, spade);

        riskDiamon = calculateNonTrumpStartRisk(riskDiamon, diamond);
        riskHeart = calculateNonTrumpStartRisk(riskHeart, heart);
        riskClub = calculateNonTrumpStartRisk(riskClub, club);
        riskSpade = calculateNonTrumpStartRisk(riskSpade, spade);

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
        Random r = new Random();
        if(good.get(0).getColour().isTrump()){
            if(good.stream().anyMatch(card -> card.getName().equals("Jack"))){
                bestCard = good.stream().filter(card -> card.getName().equals("Jack")).findFirst().get();
            }else {
                bestCard = good.get(r.nextInt(good.size()));
            }
        }else {
            if(good.stream().anyMatch(card -> card.getName().equals("Ace"))){
                bestCard = good.stream().filter(card -> card.getName().equals("Ace")).findFirst().get();
            }else {
                bestCard = good.get(r.nextInt(good.size()));
            }
        }
        return bestCard;
    }

    private Card findUber(List<Card> cards){
        boolean needUber = true;
        boolean sliced = false;
        Card firstCard = cards.get(0);
        Card uber = firstCard;
        Colour colour = firstCard.getColour();
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
                    if(possibleCards.size()>0){
                        needUber=false;
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
        return uber;
    }
    private double calculateNonTrumpStartRisk(double baseRisk, List<Card> cards){

        if(cards.size()==0){
            return baseRisk;
        }
        if(cards.get(0).getColour().isTrump()){
            return baseRisk;
        }else {
            if(cards.stream().anyMatch(card -> card.getName().equals("Ace"))){
                return baseRisk = getRiskBasedOnSize(cards, baseRisk);
            }
            if(knowledge.isAceOut(cards.get(0).getColour())){
                if(cards.stream().anyMatch(card -> card.getName().equals("Ten"))){
                    baseRisk *= 0.4;
                }
            }

        }
        return baseRisk;
    }

    private double getRiskBasedOnSize(List<Card> cards, double baseRisk){
        if(cards.size()>5){
            return baseRisk = 1;
        }else if(cards.size()>3){
            return baseRisk = 0.5;
        }else {
            return baseRisk *= 0.5d;
        }
    }

    private double calculateTrumpStartRisk(double baseRisk, List<Card> trumps){
        if(trumps.size()==0){
            return baseRisk;
        }
        if(trumps.get(0).getColour().isTrump()){
            if(trumps.stream().anyMatch(card -> card.getName().equals("Jack"))){
                baseRisk = 0.0001d;
                if(trumps.stream().anyMatch(card -> card.getName().equals("Nine"))){
                    return baseRisk *= 0.9d;
                }
            }
            else if(knowledge.isJackTrumpOut()){
                if(trumps.stream().anyMatch(card -> card.getName().equals("Nine"))) return baseRisk *= 0.7d;
                if(knowledge.isNineTrumpOut()){
                    if(trumps.stream().anyMatch(card -> card.getName().equals("Ace"))){
                        return baseRisk *= 0.7d;
                    }
                }
            }
            else if(!knowledge.isJackTrumpOut()){
                return baseRisk = 1;
            }
        }
        return baseRisk;
    }

}
