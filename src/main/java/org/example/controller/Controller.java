package org.example.controller;

import org.example.model.Client;
import org.example.service.SimulationManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

public class Controller {
    public static void control() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter input data\nNumber of clients: ");
        int numOfClients = scanner.nextInt();

        System.out.println("Number of queues: ");
        int numOfQueues = scanner.nextInt();

        System.out.println("Max time simulation: ");
        int maxTimeSim = scanner.nextInt();

        System.out.println("Min time arrival: ");
        int minTimeArrival = scanner.nextInt();

        System.out.println("Max time arrival: ");
        int maxTimeArrival = scanner.nextInt();

        System.out.println("Min time service: ");
        int minTimeService = scanner.nextInt();

        System.out.println("Max time service: ");
        int maxTimeService = scanner.nextInt();

        System.out.println("Do you want to have shortest time strategy (shortest queue by default)? (Y/n)");
        String response = scanner.nextLine();
        boolean changeStrategy = Objects.equals(response, "Y");

        System.out.println("Simulation in progress...");

        SimulationManager simulationManager= new SimulationManager(numOfClients, numOfQueues, maxTimeSim, changeStrategy,
                generateClients(
                        numOfClients,
                        minTimeArrival,
                        maxTimeArrival,
                        minTimeService,
                        maxTimeService));
        Thread thread = new Thread(simulationManager);
        thread.start();
    }
    private static ArrayList<Client> generateClients(int numOfClients,
                                                  int minArrivalTime,
                                                  int maxArrivalTime,
                                                  int minTimeService,
                                                  int maxTimeService) {
        Random random = new Random();
        ArrayList<Client> clients = new ArrayList<>();

        for (int i = 0; i < numOfClients; i++) {
            int arrivalTime = random.nextInt(maxArrivalTime - minArrivalTime) + minArrivalTime;
            int serviceTime = random.nextInt(maxTimeService - minTimeService) + minTimeService;
            clients.add(new Client(arrivalTime, serviceTime));
        }

        for (int i = 0; i < numOfClients - 1; i++) {
            for (int j = i + 1; j < numOfClients; j++) {
                if (clients.get(i).getArrivalTime() > clients.get(j).getArrivalTime()) {
                    swapClients(clients, i, j);
                }
            }
        }
        return clients;
    }
    public static void swapClients(ArrayList<Client> clients, int i, int j){
        Client client = clients.get(i);
        clients.set(i, clients.get(j));
        clients.set(j, client);
    }
}
