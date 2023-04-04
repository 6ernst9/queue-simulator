package org.example.model;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable {
    private final BlockingQueue<Client> coada;
    private final AtomicInteger waitTime;

    public Server(int maxClients){
        coada= new ArrayBlockingQueue<Client>(maxClients);
        waitTime = new AtomicInteger();
    }
    public void run() {
        while(true) {
            if (!coada.isEmpty()) {
                int time = coada.peek().getServiceTime();
                try {
                    Thread.sleep(time* 1000L);
                }
                catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                coada.poll();
                waitTime.addAndGet(-time);

            }
        }
    }
    public void add(Client client) {
        coada.add(client);
        waitTime.addAndGet(client.getServiceTime());
    }

    public Client[] getClient(){
        return coada.toArray(new Client[coada.size()]);
    }

    public int getNumOfClients() {
        return coada.size();
    }

    public int getWaitTime(){
        return waitTime.get();
    }

    public void delete(){
        coada.clear();
    }
}
