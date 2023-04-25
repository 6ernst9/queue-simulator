package org.example.strategy;

import org.example.model.Client;
import org.example.model.Pair;
import org.example.model.Server;

import java.util.List;

public class ShortestQueueStrategy implements Strategy{
    public void addClient(List<Server> servers, Client client) {
        Pair minim = new Pair(-1, 999);
        for(int i=0;i<servers.size();i++){
            if(servers.get(i).getNumOfClients() < minim.getValue()){
                minim.setIndex(i);
                minim.setValue(servers.get(i).getNumOfClients());
            }
        }
        servers.get(minim.getIndex()).add(client);
    }
}
