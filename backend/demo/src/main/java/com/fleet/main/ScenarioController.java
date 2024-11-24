package com.fleet.main;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fleet.main.types.Position;

@RestController
public class ScenarioController {

    @GetMapping(value = "/customers")
    public Collection<Customer> getCustomers() {
        return ScenarioRunner.scenario.customers.values();
    }

    @GetMapping(value = "/customer")
    public Customer getCustomer(@RequestParam("uuid") UUID uuid) {
        return ScenarioRunner.scenario.customers.get(uuid);
    }

    @GetMapping(value = "/vehicles")
    public Collection<Vehicle> getVehicles() {
        return ScenarioRunner.scenario.vehicles.values();
    }

    @GetMapping(value = "/vehicle")
    public Vehicle getVehicle(@RequestParam("uuid") UUID uuid) {
        return ScenarioRunner.scenario.vehicles.get(uuid);
    }

    @GetMapping(value = "/waitingCustomers")
    public List<Customer> getWaitingCustomer() {
        List<Customer> waitingCustomer = new ArrayList<Customer>();
        for (Customer curCustomer : ScenarioRunner.scenario.customers.values()) {
            if(!curCustomer.pickedUp && !curCustomer.plannedToPickUp){
                waitingCustomer.add(curCustomer);
            }
        }
        return waitingCustomer;
    }

    @GetMapping(value = "/pickedUpCustomers")
    public List<Customer> getPickedUpCustomer() {
        List<Customer> pickedUpCustomer = new ArrayList<Customer>();
        for (Customer curCustomer : ScenarioRunner.scenario.customers.values()) {
            if(curCustomer.pickedUp){
                pickedUpCustomer.add(curCustomer);
            }
        }
        return pickedUpCustomer;
    }

    @GetMapping(value = "/availableVehicles")
    public List<Vehicle> getAvailableVehicle() {
        List<Vehicle> activeVehicles = new ArrayList<Vehicle>();
        for (Vehicle curVehicle : ScenarioRunner.scenario.vehicles.values()) {
            if(curVehicle.isAvailable()){
                activeVehicles.add(curVehicle);
            }
        }
        return activeVehicles;
    }

    // in Carbon kg source: https://www.transportenvironment.org/articles/how-clean-are-electric-cars
    @GetMapping(value = "/climateBenefit")
    public double getClimateBenefit() {
        double distance = 0;
        for (Vehicle curVehicle: ScenarioRunner.scenario.vehicles.values()) {
            distance += curVehicle.distanceTraveled;
        }

        distance = Scenario.latLanToKm(distance);
        double our = distance * 75;
        double gas = distance * 241;
        return (gas - our) / 1000;
    }

    @GetMapping(value = "/economicsBenefit")
    public double getEconomicsBenefit() {
        double distance = 0;
        for (Vehicle curVehicle: ScenarioRunner.scenario.vehicles.values()) {
            distance += curVehicle.distanceTraveled;
        }

        distance = Scenario.latLanToKm(distance);
        double our = distance * 75;
        double gas = distance * 241;
        return (gas - our) / 1000;
    }


    @GetMapping(value = "/getSingleInfos")
    public HashMap<String, Integer> getSingleInfos() {
        int charging = 0;
        int available = 0;
        int blocked = 0;
        int numTrips = 0;
        double distance = 0;
        for (Vehicle curVehicle : ScenarioRunner.scenario.vehicles.values()) {
            if(curVehicle.charging){
                charging += 1;
            }
            if(curVehicle.isAvailable()){
                available += 1;
            }
            if(!curVehicle.isAvailable()){
                blocked += 1;
            }
            numTrips += curVehicle.numberOfTrips;
            distance += curVehicle.distanceTraveled; 
        }

        int notMatchedCus = 0;
        for (Customer curCustomer : ScenarioRunner.scenario.customers.values()) {
            if(!curCustomer.pickedUp && !curCustomer.plannedToPickUp){
                notMatchedCus += 1;
            }
        }

        distance = Scenario.latLanToKm(distance);

        HashMap<String, Integer> returnObj = new HashMap<>();
        returnObj.put("numChargingVehicle", charging);
        returnObj.put("numAvailableVehicle", available);
        returnObj.put("numBlockedVehicle", blocked);
        returnObj.put("numNotMatchedCustomer", notMatchedCus);
        returnObj.put("numTotalTrips", numTrips);
        returnObj.put("totalDistanceTraveled", (int) distance);

        return returnObj;
    }

    @GetMapping(value = "/blockedVehicles")
    public List<Vehicle> getBlockedVehicle() {
        List<Vehicle> blockedVehicles = new ArrayList<Vehicle>();
        for (Vehicle curVehicle : ScenarioRunner.scenario.vehicles.values()) {
            if(!curVehicle.isAvailable()){
                blockedVehicles.add(curVehicle);
            }
        }
        return blockedVehicles;
    }

    @RequestMapping(value = "/setVehicleTarget", method = RequestMethod.POST)
    public ResponseEntity<?> setVehicleTarget(@RequestParam("vehicle_uuid") UUID vUuid, @RequestParam("customer_uuid") UUID cUuid) {
        Customer customer = ScenarioRunner.scenario.customers.get(cUuid);
        Vehicle vehicle = ScenarioRunner.scenario.vehicles.get(vUuid);

        if(customer == null || customer.pickedUp || !vehicle.isAvailable()){
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }
        vehicle.setTargetToPickup(customer);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @RequestMapping(value = "/deleteVehicle", method = RequestMethod.DELETE)
    public Vehicle deleteVehicle(@RequestParam("uuid") UUID uuid) {
        Vehicle vehicle = ScenarioRunner.scenario.vehicles.remove(uuid);

        return vehicle;
    }

    @RequestMapping(value = "/addVehicle", method = RequestMethod.POST)
    public Vehicle addVehicle() {
        Vehicle vehicle = new Vehicle(new Position());
        ScenarioRunner.scenario.vehicles.put(vehicle.id, vehicle);
        return vehicle;
    }

    @RequestMapping(value = "/resetScenario", method = RequestMethod.POST)
    public ResponseEntity<?> resetScenario(@RequestParam("num_vehicles") int nv, @RequestParam("num_customers") int nc) {
        ScenarioRunner.scenario = new Scenario(nv, nc);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

}
