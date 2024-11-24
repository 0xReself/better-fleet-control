package org.example;

import java.io.IOException;

import org.example.types.Customer;
import org.example.types.Position;
import org.example.types.Vehicle;

import java.util.List;
import java.util.Random;

import org.example.utils.Request;

public class RandomPath {
    public void run() throws IOException {
        Random rand = new Random();
        List<Vehicle> available_vehicles = Request.getAvailableVehicle();
        for (Vehicle vec : available_vehicles) {
            List<Customer> waiting_customers = Request.getWaitingCustomer();
            if (waiting_customers.isEmpty()) {
                break;
            }
            int len = waiting_customers.size();
            int randomIdx = rand.nextInt(len);

        
            Request.setVehicleTarget(vec.id, waiting_customers.get(randomIdx).id);
        }
    }
}
