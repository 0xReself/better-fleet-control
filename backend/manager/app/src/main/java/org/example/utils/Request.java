package org.example.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;
import java.util.UUID;

import org.example.types.Customer;
import org.example.types.Vehicle;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Request {
    public static List<Customer> getCustomers() throws MalformedURLException, IOException, ProtocolException {
        URL url = new URL("http://localhost:8080/customers");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        InputStream inputStream = con.getInputStream();

        ObjectMapper mapper = new ObjectMapper();
        List<Customer> customer = mapper.readValue(inputStream, new TypeReference<List<Customer>>(){});
        return customer;
    } 

    public static Customer getCustomer(UUID uuid) throws MalformedURLException, IOException, ProtocolException {
        URL url = new URL("http://localhost:8080/customer" + "?uuid=" + uuid.toString());
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        InputStream inputStream = con.getInputStream();

        ObjectMapper mapper = new ObjectMapper();
        Customer customer = mapper.readValue(inputStream, Customer.class);
        return customer;
    } 

    public static List<Customer> getWaitingCustomer() throws MalformedURLException, IOException, ProtocolException {
        URL url = new URL("http://localhost:8080/waitingCustomers");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        InputStream inputStream = con.getInputStream();

        ObjectMapper mapper = new ObjectMapper();
        List<Customer> customer = mapper.readValue(inputStream, new TypeReference<List<Customer>>(){});
        return customer;
    } 

    public static List<Customer> getPickedUpCustomer() throws MalformedURLException, IOException, ProtocolException {
        URL url = new URL("http://localhost:8080/pickedUpCustomers");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        InputStream inputStream = con.getInputStream();

        ObjectMapper mapper = new ObjectMapper();
        List<Customer> customer = mapper.readValue(inputStream, new TypeReference<List<Customer>>(){});
        return customer;
    } 

    public static List<Vehicle> getVehicles() throws MalformedURLException, IOException, ProtocolException {
        URL url = new URL("http://localhost:8080/vehicles");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        InputStream inputStream = con.getInputStream();
        
        /* 
        StringBuilder textBuilder = new StringBuilder();
        try (Reader reader = new BufferedReader(new InputStreamReader
        (inputStream, StandardCharsets.UTF_8))) {
          int c = 0;
          while ((c = reader.read()) != -1) {
              textBuilder.append((char) c);
          }
        }
        System.out.println(textBuilder.toString());*/

        ObjectMapper mapper = new ObjectMapper();

        List<Vehicle> vehicles = mapper.readValue(inputStream, new TypeReference<List<Vehicle>>(){});
        return vehicles;
    } 

    public static Vehicle getVehicle(UUID uuid) throws MalformedURLException, IOException, ProtocolException {
        URL url = new URL("http://localhost:8080/vehicle" + "?uuid=" + uuid.toString());
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        InputStream inputStream = con.getInputStream();

        ObjectMapper mapper = new ObjectMapper();
        Vehicle vehicle = mapper.readValue(inputStream, Vehicle.class);
        return vehicle;
    } 

    public static List<Vehicle> getAvailableVehicle() throws MalformedURLException, IOException, ProtocolException {
        URL url = new URL("http://localhost:8080/availableVehicles");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        InputStream inputStream = con.getInputStream();

        ObjectMapper mapper = new ObjectMapper();
        List<Vehicle> vehicles = mapper.readValue(inputStream, new TypeReference<List<Vehicle>>(){});
        return vehicles;
    } 

    public static List<Vehicle> getBlockedVehicle() throws MalformedURLException, IOException, ProtocolException {
        URL url = new URL("http://localhost:8080/blockedVehicles");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        InputStream inputStream = con.getInputStream();

        ObjectMapper mapper = new ObjectMapper();
        List<Vehicle> vehicles = mapper.readValue(inputStream, new TypeReference<List<Vehicle>>(){});
        return vehicles;
    } 

    public static int setVehicleTarget(UUID vehicleUuid, UUID customerUuid) throws MalformedURLException, IOException, ProtocolException {
        URL url = new URL("http://localhost:8080/setVehicleTarget" + "?vehicle_uuid=" + vehicleUuid.toString() + "&customer_uuid=" + customerUuid.toString());
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        int responseCode = con.getResponseCode();

        /*
        StringBuilder textBuilder = new StringBuilder();
        try (Reader reader = new BufferedReader(new InputStreamReader
          (inputStream, StandardCharsets.UTF_8))) {
            int c = 0;
            while ((c = reader.read()) != -1) {
                textBuilder.append((char) c);
            }
        }
        System.out.println(textBuilder.toString());
        
        return "true".equals(textBuilder.toString());
         */
        return responseCode;
    } 
}