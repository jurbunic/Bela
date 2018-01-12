package org.foi.jurbunic.bela;


import jade.core.*;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;
import org.foi.jurbunic.bela.agents.Player;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class MainClass {

    private static Integer numPlayer = 1;
    private static List<AgentController> agentControllers = new ArrayList<>();

    public static void main(String[] args){
        System.out.println("Starting app...");
        ContainerController container = getPlatform();
        for(int i=0;i<4;i++) {
            Player p = new Player();
            p.setArguments(new Object[]{i});
            agentControllers.add(startAgent(container, p));
        }
    }

    private static AgentController startAgent(ContainerController container, Agent agent){
        AgentController agentController = null;
        String nickname = "Player-"+numPlayer++;
        try {
            agentController = container.createNewAgent(nickname,agent.getClass().getName(),agent.getArguments());
            agentController.start();
        } catch (StaleProxyException e) {
            e.printStackTrace();
        }
        return agentController;
    }

    private static ContainerController getPlatform(){
        Runtime runtime = Runtime.instance();
        Profile profile = new ProfileImpl();
        String host = "";
        try{
            host = Inet4Address.getLocalHost().getHostAddress();
        }catch (UnknownHostException e){

        }
        profile.setParameter(Profile.MAIN_HOST, host);
        profile.setParameter(Profile.MAIN_PORT, "1100");
        profile.setParameter(Profile.CONTAINER_NAME, "Belot");
        ContainerController cc = runtime.createAgentContainer(profile);
        return cc;
    }
}
