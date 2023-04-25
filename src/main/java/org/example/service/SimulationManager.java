package org.example.service;

import org.example.model.Client;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class SimulationManager implements Runnable{
    private final Scheduler scheduler;
    private final int MAX_TASKS = 1001;
    List<Client> clients;
    private final int maxTimeSim;
    private final int numOfClients;
    private final float avgServiceTime;
    private float avgWaitTime;
    private float peakHour;
    private final Writer writer;
    public SimulationManager ( int numOfClients,
                               int numOfQueues,
                               int maxTimeSim,
                               boolean isShortestTimeStrategy,
                               ArrayList<Client> clients) throws IOException {
        this.maxTimeSim = maxTimeSim;
        this.numOfClients = numOfClients;
        this.clients = clients;
        this.scheduler = new Scheduler(numOfQueues, MAX_TASKS);
        if(isShortestTimeStrategy) this.scheduler.toggleStrategy();
        this.writer = new FileWriter("output.txt");
        avgServiceTime = calculateAvgTime();
    }

    @Override
    public void run() {
        int counter = 0;
        int maxWaitTime = 0;
        String fileContent = "";
        while (counter <= maxTimeSim) {
            fileContent+="Time " + counter + "\n";

            for (int i = 0; i < clients.size(); i++) {
                if (clients.get(i).getArrivalTime() == counter) {
                    try {
                        scheduler.addClient(clients.get(i));
                    }
                    catch (InterruptedException e) {
                        System.out.println(e.getMessage());
                    }
                    clients.remove(i);
                    i--;
                }
            }

            int waitTime=0;
            for (int i = 0; i < scheduler.getServers().size(); i++) {
                waitTime += scheduler.getServers().get(i).getWaitTime();
            }
            avgWaitTime+=waitTime;

            if (waitTime > maxWaitTime) {
                maxWaitTime = waitTime;
                peakHour = counter+1;
            }
            fileContent += "Waiting clients: ";
            if(clients.size()==0){
                fileContent+="no waiting clients";
            }
            else for (Client generatedTask : clients){
                fileContent+=generatedTask.toString()+ " ";
            }
            fileContent+="\n";
            for (int i = 0; i < scheduler.getServers().size(); i++) {
                fileContent+="Queue " + (i + 1) + ": ";
                if(scheduler.getServers().get(i).getNumOfClients() == 0){
                    fileContent+="closed";
                }
                else for (int j = 0; j < scheduler.getServers().get(i).getNumOfClients(); j++) {
                    fileContent+=scheduler.getServers().get(i).getClient().get(j).toString()+ " ";
                }
                fileContent+="\n";
            }
            fileContent+="\n";

            for (int i = 0; i < scheduler.getServers().size(); i++) {
                if (scheduler.getServers().get(i).getNumOfClients() != 0) {
                    if (scheduler.getServers().get(i).getClient().get(0).getServiceTime() != 0){
                        scheduler.getServers().get(i).getClient().get(0).setServiceTime(
                                scheduler.getServers().get(i).getClient().get(0).getServiceTime() -1);
                    }

                }
            }
            counter++;

            if (clients.size()==0 && scheduler.isEmpty()) {
                break;
            }
            try {
                Thread.sleep(1000);
            }
            catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }

        for(int i=0;i<scheduler.getServers().size();i++) {
            if (scheduler.getServers().get(i).getNumOfClients() != 0) {
                scheduler.getServers().get(i).getCoada().clear();
            }
        }
        avgWaitTime /=numOfClients;
        fileContent+="Simulation finished\n";

        for (int i = 0; i < scheduler.getServers().size(); i++) {
            fileContent+="Queue " + i + ": closed\n";
        }
        fileContent+="\nStats:\n";
        fileContent+="Peek hour: " + peakHour + "\n";
        fileContent+="Average service time: " + avgServiceTime+"\n";
        fileContent+="Average waiting time: " + avgWaitTime;

        try{
            writer.write(fileContent);
            writer.close();
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }

        Thread.currentThread().interrupt();
    }

    public float calculateAvgTime(){
        float average=0;
        for (int i = 0; i < numOfClients; i++) {
            average += clients.get(i).getServiceTime();
        }
        average = average / numOfClients;
        return average;
    }
}
