package org.foi.jurbunic.bela.agents.behaviours;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import org.foi.jurbunic.bela.Game;
import org.foi.jurbunic.bela.agents.Player;
import org.foi.jurbunic.bela.cards.*;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PlayerTurn extends CyclicBehaviour {

    private Player player;
    private Game game = Game.getInstance();
    private List<Card> cardsInPlay = new ArrayList<>();
    private int firstStart = 0;

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
                while(player.getStatus()==0){
                    ACLMessage message = player.receive();
                    if(message != null){
                        try {
                            cardsInPlay = (List<Card>) message.getContentObject();
                            //cardsInPlay.add((Card) message.getContentObject());
                        } catch (UnreadableException e) {
                            e.printStackTrace();
                        }
                        System.out.println("Agent["+player.getPlayerId()+"]----------");
                        System.out.println("Odigrano---------");
                        for(int i=0;i<cardsInPlay.size();i++){
                            if(cardsInPlay.size()==0) break;
                            Card cardOut = cardsInPlay.get(i);
                            if(cardOut == null) continue;
                            try {
                                System.out.print((player.getPlayerId() - 1) + cardOut.getName() + "-" + cardOut.getColourName() + ", ");
                            }catch (Exception e){
                                System.out.println("");
                            }
                        }
                        System.out.println();
                        if(cardsInPlay.size() >= 4){
                            cardsInPlay = new ArrayList<>();
                        }
                        System.out.println("-----------------");


                        player.setStatus(2);

                    }
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
                algorithm = new NextCardAlgoritm(player.getMyCards());
                algorithm.setCars(cardsInPlay);
                ACLMessage sendMessage = new ACLMessage(ACLMessage.INFORM);
                sendMessage.addReceiver(game.getNextPlayer(player.getPlayerId()));
                Card card = algorithm.bestAction();
                cardsInPlay.add(card);
                try {
                    sendMessage.setContentObject((Serializable) cardsInPlay);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                player.send(sendMessage);
                player.getMyCards().remove(card);
                player.setStatus(0);

                sleep();
                break;
            case 3:
                System.out.println("["+player.getPlayerId()+"] Nemam vise karta");
                player.doDelete();
                break;
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
