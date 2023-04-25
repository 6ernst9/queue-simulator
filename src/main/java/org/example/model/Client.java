package org.example.model;

public class Client {
    private final int ID;
    private static int globalId=1;
    private final int arrivalTime;
    private int serviceTime;
    private int waitTime;

    public Client(int arrivalTime, int serviceTime) {
        this.ID = globalId;
        globalId++;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
    }

    public int getId() {
        return ID;
    }

    public void setWaitTime(int waitTime){
        this.waitTime = waitTime;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(int time){
        this.serviceTime = time;
    }

    public int getWaitTime(){
        return waitTime;
    }

    @Override
    public String toString() {
        return "(" + ID + ", " + arrivalTime + ", " + serviceTime + "); ";
    }
}
