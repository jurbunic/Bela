package org.foi.jurbunic.bela.agents.behaviours;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import org.foi.jurbunic.bela.Game;
import org.foi.jurbunic.bela.agents.Player;

public class PlayerTurn extends CyclicBehaviour {

    private Player player;
    private Game game = Game.getInstance();

    public PlayerTurn(Agent a) {
        super(a);
        this.player = (Player) a;
    }

    @Override
    public void action() {
        while (player.getNumOfMyCards() != 8) {

        }
        if (game.getPlayerOnTurn() == player.getPlayerId()) {
            System.out.println("["+player.getPlayerId()+"]"+"Jupi, moj potez!");
            player.listMyCards();
            game.nextTurn();
        } else {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                System.out.println("Prekid cekanja!");
                e.printStackTrace();
            }
        }
    }
}
