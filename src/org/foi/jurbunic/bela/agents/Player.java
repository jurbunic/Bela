package org.foi.jurbunic.bela.agents;

import jade.core.Agent;
import org.foi.jurbunic.bela.cards.Card;

import java.util.List;

public class Player extends Agent {

    private List<Card> myCards;

    public Player(){

    }

    @Override
    public void setup(){
        System.out.println("My name is " + getName());
        myCards = (List<Card>) getArguments()[0];
        listMyCards();

    }

    public void listMyCards(){
        System.out.println("My cards:");
        for(Card card : myCards){
            System.out.println(card.getName() + " " +card.getColourName());
        }
    }


}
