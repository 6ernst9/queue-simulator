package org.example.strategy;

import org.example.model.Client;
import org.example.model.Server;

import java.util.List;

public interface Strategy {
    void addClient(List<Server> servers, Client client);
}
