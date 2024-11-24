package org.example;

import java.io.IOException;

import org.example.types.Customer;
import org.example.types.Position;
import org.example.types.Vehicle;

import java.util.List;

import org.example.utils.Request;

public class NaiveShortestPath {
    public float calculateDistance(Position a, Position b) {
        return (float) Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2));
    }

    public void run() throws IOException {
        List<Vehicle> available_vehicles = Request.getAvailableVehicle();
        for (Vehicle vec : available_vehicles) {
            List<Customer> waiting_customers = Request.getWaitingCustomer();
            if (waiting_customers.isEmpty()) {
                break;
            }

            Customer shortest = waiting_customers.get(0);
            for (Customer cus : waiting_customers) {
                if (calculateDistance(vec.currentPosition, cus.startPosition) <
                        calculateDistance(vec.currentPosition, shortest.startPosition)) {
                    shortest = cus;
                }
            }
            Request.setVehicleTarget(vec.id, shortest.id);
        }
    }
}
