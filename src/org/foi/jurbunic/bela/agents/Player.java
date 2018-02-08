package org.foi.jurbunic.bela.agents;

import jade.core.Agent;
import org.foi.jurbunic.bela.agents.behaviours.PlayerTurn;
import org.foi.jurbunic.bela.agents.behaviours.Registration;
import org.foi.jurbunic.bela.cards.Card;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Player extends Agent implements Serializable {

    private Integer status = 0;
    private Integer playerId;
    private Integer playerTeam;
    private List<Card> myCards = new ArrayList<>();

    public Player(){

    }

    @Override
    public void setup(){
        playerId =  (Integer) this.getArguments()[0];
        System.out.println("["+playerId+"]"+"My name is " + getName());
        sleep();
        addBehaviour(new Registration(this));
        addBehaviour(new PlayerTurn(this));
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


    public Integer getPlayerTeam() {
        return playerTeam;
    }


    public void setPlayerTeam(Integer playerTeam) {
        this.playerTeam = playerTeam;
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
