package org.example.service;

import org.example.model.Client;
import org.example.model.Server;
import org.example.strategy.ShortestQueueStrategy;
import org.example.strategy.ShortestTimeStrategy;
import org.example.strategy.Strategy;
import org.example.strategy.StrategyEnum;

import java.util.ArrayList;

public class Scheduler {
    private final ArrayList<Server> servers;
    private Strategy strategy = new ShortestQueueStrategy();
    private StrategyEnum currentStrategy = StrategyEnum.SHORTEST_QUEUE;

    public Scheduler(int maxNumOfTasks, int maxTasksPerServer){
        servers = new ArrayList<>();
        initServers(maxNumOfTasks, maxTasksPerServer);
    }

    private void initServers(int maxNumOfTasks, int maxTasksPerServer){
        for(int i=0;i<maxNumOfTasks;i++){
            Server server = new Server(maxTasksPerServer);
            servers.add(server);
            Thread th = new Thread(server);
            th.start();
        }
    }

    public ArrayList<Server> getServers() {
        return servers;
    }

    public void addClient(Client client) throws InterruptedException {
        strategy.addClient(this.servers, client);
    }

    public void toggleStrategy(){
        if(currentStrategy == StrategyEnum.SHORTEST_QUEUE){
            strategy = new ShortestTimeStrategy();
            currentStrategy = StrategyEnum.SHORTEST_TIME;
        }
        else{
            strategy = new ShortestQueueStrategy();
            currentStrategy = StrategyEnum.SHORTEST_QUEUE;
        }
    }

    public boolean isEmpty() {
        for (Server server : servers){
            if(server.getNumOfClients()!=0) return false;
        }
        return true;
    }
}
