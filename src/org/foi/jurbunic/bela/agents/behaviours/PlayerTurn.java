package org.foi.jurbunic.bela.agents.behaviours;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import org.foi.jurbunic.bela.Game;
import org.foi.jurbunic.bela.agents.Player;
import org.foi.jurbunic.bela.cards.*;


public class PlayerTurn extends Behaviour {

    private Player player;
    private Game game = Game.getInstance();
    private int firstStart = 0;
    private Hand hand = new Hand();

    private static int numOfCardsOnTable = 0;

    public PlayerTurn(Agent a) {
        super(a);
        this.player = (Player) a;
    }

    @Override
    public void action() {
        sleep();
        if(firstStart==0){
            while (player.getNumOfMyCards() != 8) {
                System.out.println(player.getPlayerId()+" nema sve karte");
                sleep();
            }
            firstStart++;
        }
        if(firstStart==1){
            printMyCards();
            firstStart++;
        }
        CardAlgorithm algorithm = null;
        switch (player.getStatus()){
            //Waiting for turn
            case 0:
                sleep();
                break;
            //I call trump
            case 1:
                algorithm = new TrumpAlgorithm(player.getMyCards());
                Colour colour = algorithm.bestAction().getColour();
                colour.setTrump(true);
                game.setTrump(player,colour);
                System.out.println("["+player.getPlayerId()+"] Trump is: "+colour.getColourName());
                player.setStatus(2);
                break;
            //My turn
            case 2:
                if(player.getMyCards().size()==0){
                    player.setStatus(3);
                    return;
                }
                hand = game.getHand();
                algorithm = new NextCardAlgoritm(player.getMyCards());
                algorithm.setCars(hand.getCardsInPlay());
                Card card = algorithm.bestAction();
                game.playInHand(player, card);
                String out = "["+player.getPlayerId()+"] Odigrao sam: "+card.getCardGraphic();
                if(card.getColour().isTrump()){
                    out += " (A)";
                }
                System.out.println(out);
                player.getMyCards().remove(card);
                player.setStatus(0);
                game.setNextPlayer(player.getPlayerId());
                if(player.getMyCards().size()==0){
                    player.setStatus(3);
                }
                sleep();
                break;
            // Wait for player decision
            case 3:
                for(int i=0;i<5;i++)
                    sleep();
                System.out.println("["+player.getPlayerId()+"] Nemam vise karta");
                game.waitNewGame(player);
                while (player.getStatus()==3){
                    sleep();
                }
                break;
            case 4:
                //game over
                break;
            default:
                player.setStatus(0);
        }
    }

    @Override
    public boolean done() {
        if(player.getStatus()==4){
            return true;
        }else return false;
    }

    private void printMyCards() {
        StringBuilder out = new StringBuilder("[" + player.getPlayerId() + "]  ");
        for(Card card : player.getMyCards()){
            out.append(card.getCardGraphic());
        }
        System.out.println(out.toString());
    }

    private void sleep(){
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            System.out.println("Prekid cekanja!");
            player.doDelete();
        }
    }
}
