package org.example;

import org.example.types.Customer;
import org.example.types.Position;
import org.example.types.Vehicle;
import org.example.utils.Request;

import java.util.*;

public class WeightedShortestPath {
    public float calculateDistance(Position a, Position b) {
        return (float) Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2));
    }

    public class PlannedTrip implements Comparable<PlannedTrip> {
        public Vehicle vehicle;
        public Customer customer;
        public double weight;

        public PlannedTrip(Vehicle vehicle, Customer customer, double weight) {
            this.vehicle = vehicle;
            this.customer = customer;
            this.weight = weight;
        }

        @Override
        public int compareTo(PlannedTrip plannedTrip) {
            if (this.weight < plannedTrip.weight) return -1;
            return 1;
        }
    }

    public void run() {
        try {
            List<Vehicle> vehicles = Request.getVehicles();
            List<Customer> customers = Request.getCustomers();
            PlannedTrip[] plannedTrips = new PlannedTrip[vehicles.size() * customers.size()];

            int i = 0;
            for (Vehicle vec : vehicles) {
                double current_weight = 0;
                if (vec.customerInTransport != null && vec.charging) {
                    current_weight = calculateDistance(vec.currentPosition, vec.targetPosition) / vec.vehicleSpeed;
                }

                for (Customer customer : customers) {
                    double trip_weight = calculateDistance(vec.currentPosition, customer.startPosition);
                    if (vec.customerInTransport != null) {
                        trip_weight = calculateDistance(vec.targetPosition, customer.startPosition);
                    }
                    if(i > plannedTrips.length) return;
                    plannedTrips[i] =  new PlannedTrip(vec, customer, current_weight + trip_weight);
                    i++;
                }
            }

            Arrays.sort(plannedTrips);

            for (PlannedTrip trip : plannedTrips) {
                if (trip.vehicle.customerInTransport == null && !trip.vehicle.charging && !trip.customer.plannedToPickUp && !trip.customer.pickedUp) {
                    trip.vehicle.customerInTransport = trip.customer;
                    trip.vehicle.targetToPickup = trip.customer;
                    trip.customer.plannedToPickUp = true;
                    trip.customer.pickedUp = true;
                    Request.setVehicleTarget(trip.vehicle.id, trip.customer.id);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
