package org.foi.jurbunic.bela.agents.behaviours;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import org.foi.jurbunic.bela.Game;
import org.foi.jurbunic.bela.agents.Player;

public class Register extends OneShotBehaviour {
    Game game = Game.getInstance();
    Player player;

    public Register(Agent a) {
        super(a);
        this.player = (Player) a;
    }

    @Override
    public void action() {
        game.registerPlayer(player);
        System.out.println("["+player.getPlayerId()+"] Prijava u igru!");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
