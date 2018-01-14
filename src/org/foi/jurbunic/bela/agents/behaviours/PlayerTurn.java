package org.foi.jurbunic.bela.agents.behaviours;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import org.foi.jurbunic.bela.Game;
import org.foi.jurbunic.bela.agents.Player;
import org.foi.jurbunic.bela.cards.*;


public class PlayerTurn extends CyclicBehaviour {

    private Player player;
    private Game game = Game.getInstance();
    private int firstStart = 0;
    private Hand hand = new Hand();

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
        CardAlgorithm algorithm = null;
        switch (player.getStatus()){
            //Waiting for turn
            case 0:
                while (player.getStatus()==0) {
                    sleep();
                }
                break;
            //I call trump
            case 1:
                algorithm = new TrumpAlgorithm(player.getMyCards());
                Colour colour = algorithm.bestAction().getColour();
                colour.setTrump(true);
                game.setTrump(colour);
                System.out.println("Zovem: "+colour.getColourName());
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
                System.out.println("["+player.getPlayerId()+"] Odigrao sam: "+ card.getName()+"-"+card.getColourName());
                player.getMyCards().remove(card);
                player.setStatus(0);
                game.setNextPlayer(player.getPlayerId());
                sleep();
                break;
            case 3:
                System.out.println("["+player.getPlayerId()+"] Nemam vise karta");
                player.doDelete();
                break;
            default:
                player.setStatus(0);
        }
    }

    private void sleep(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("Prekid cekanja!");
            e.printStackTrace();
        }
    }
}
