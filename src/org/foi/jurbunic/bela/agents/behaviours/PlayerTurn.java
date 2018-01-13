package org.foi.jurbunic.bela.agents.behaviours;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import org.foi.jurbunic.bela.Game;
import org.foi.jurbunic.bela.agents.Player;
import org.foi.jurbunic.bela.cards.CardAlgorithm;
import org.foi.jurbunic.bela.cards.Colour;
import org.foi.jurbunic.bela.cards.TrumpAlgorithm;

public class PlayerTurn extends CyclicBehaviour {

    private Player player;
    private Game game = Game.getInstance();

    public PlayerTurn(Agent a) {
        super(a);
        this.player = (Player) a;
    }

    @Override
    public void action() {
        sleep();
        while (player.getNumOfMyCards() != 8) {
            System.out.println(player.getPlayerId()+" nema sve karte");
            sleep();
        }
        switch (player.getStatus()){
            //Waiting for turn
            case 0:
                while(player.getStatus()==0){
                    ACLMessage message = player.receive();
                    if(message != null){
                        System.out.println("Primatelj:["+player.getPlayerId()+"]");
                        System.out.println("Sadrzaj: " + message.getContent());
                        player.setStatus(2);
                    }
                }
                break;
            //I call trump
            case 1:
                CardAlgorithm algorithm = new TrumpAlgorithm(player.getMyCards());
                Colour colour = algorithm.bestAction().getColour();
                colour.setTrump(true);
                System.out.println("Zovem: "+colour.getColourName());
                player.setStatus(2);
                break;
            //My turn
            case 2:
                ACLMessage sendMessage = new ACLMessage(ACLMessage.INFORM);
                sendMessage.addReceiver(game.getNextPlayer(player.getPlayerId()));
                sendMessage.setContent("Sadrzaj od ["+player.getPlayerId()+"]: Hitil sam ");
                player.send(sendMessage);
                player.setStatus(0);
                sleep();
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
