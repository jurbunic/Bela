package org.foi.jurbunic.bela.agents;

import jade.core.Agent;
import org.foi.jurbunic.bela.agents.behaviours.PlayerTurn;
import org.foi.jurbunic.bela.agents.behaviours.Register;
import org.foi.jurbunic.bela.cards.Card;

import java.util.ArrayList;
import java.util.List;

public class Player extends Agent {

    private Integer status = 0;
    private Integer playerId;
    private List<Card> myCards = new ArrayList<>();

    public Player(){

    }

    @Override
    public void setup(){
        playerId =  (Integer) this.getArguments()[0];
        System.out.println("["+playerId+"]"+"My name is " + getName());
        sleep();
        addBehaviour(new Register(this));
        addBehaviour(new PlayerTurn(this));
    }

    public void listMyCards(){
        System.out.println("My cards:");
        for(Card card : myCards){
            System.out.println(card.getName() + " " +card.getColourName());
        }
    }

    public void setMyCards(List<Card> cards){
        this.myCards = cards;
    }

    public Integer getPlayerId() {
        return playerId;
    }

    public Integer getNumOfMyCards(){
        return myCards.size();
    }

    public Integer getStatus(){
        return status;
    }

    public List<Card> getMyCards() {
        return myCards;
    }

    public void setStatus(Integer status){
        this.status = status;
    }

    @Override
    protected void takeDown() {
        System.out.println("Umirem" + getAID().getName());
        super.takeDown();
    }

    private void sleep(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
