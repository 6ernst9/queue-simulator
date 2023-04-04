package org.example.model;

import java.util.List;

public interface Strategy {
    void addClient(List<Server> servers, Client client);
}
