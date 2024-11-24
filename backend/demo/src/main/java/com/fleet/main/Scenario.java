package com.fleet.main;

import java.util.UUID;

import com.fleet.main.monitor.MonitorData;
import com.fleet.main.types.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Scenario {
    public UUID id;
    public HashMap<UUID, Customer> customers;
    public HashMap<UUID, Vehicle> vehicles;
    public int numCustomers;
    public double timeStamp = 0;
    public final int STATISTICS_FRAME = 50;

    public Scenario(int numVehicles, int numCustomers) {
        this.numCustomers = numCustomers;
        this.id = UUID.randomUUID();
        this.customers = new HashMap<UUID, Customer>();
        this.vehicles = new HashMap<UUID, Vehicle>();

        for (int i = 0; i < numVehicles; i++) {
            Vehicle curVehicle = new Vehicle(new Position());
            vehicles.put(curVehicle.id, curVehicle);
        }

        for (int i = 0; i < numCustomers; i++) {
            Customer curCustomer = new Customer(
                new Position(),
                new Position(), 0.);
            customers.put(curCustomer.id, curCustomer);
        }
    }

    public Scenario(HashMap<UUID, Vehicle> tempVeh, HashMap<UUID, Customer> tempCus) {
        this.numCustomers = tempCus.size();
        this.id = UUID.randomUUID();
        this.customers = tempCus;
        this.vehicles = tempVeh;
    }

    public static double latLanToKm(double latDistance){
        return (latDistance / 0.2) * 22;
    }

    public void update() {
        for (Vehicle vec : this.vehicles.values()) {
            vec.update(timeStamp);
        }
        
        this.removeDoneCustomer();

        // random chance to spawn new customers when number of customers below numCustomers
        float cusDiff = this.numCustomers - customers.size();
        float chance = (float) Math.max(Math.min(cusDiff * 0.05, 1.), 0.1);
        if (customers.size() < this.numCustomers) {
            if (Position.rand.nextDouble(1) < chance) {
                Customer curCustomer = new Customer(
                        new Position(),
                        new Position(), timeStamp);
                customers.put(curCustomer.id, curCustomer);
            }
        }

        if(timeStamp % STATISTICS_FRAME == STATISTICS_FRAME - 1){
            MonitorData.endStep();
        }

        timeStamp += 1;

    }

    public void removeDoneCustomer(){
        List<Customer> toRemove = new ArrayList<Customer>();
        for(Customer customer: customers.values()){
            if(customer.isAtDestination()){
                toRemove.add(customer);
            }
        }
        for(Customer customer: toRemove){
            MonitorData.travelDistanceDatapoint.addPoint(Position.getDis(customer.endPosition, customer.startPosition));
            customers.remove(customer.id);
        }
    }
}