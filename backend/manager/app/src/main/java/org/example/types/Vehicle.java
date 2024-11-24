package org.example.types;

import java.util.Optional;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Vehicle {
	public UUID id;
	public String modelName;
	public Position startPosition;
	public Position currentPosition;
	public Position targetPosition;
	public Customer targetToPickup;
	public Customer customerInTransport;
	public double distanceTraveled;
	public double activeTime;
	public int numberOfTrips;
	public double remainingTravelTime;
	public double remainingDistancePercentage;
	public double vehicleSpeed;
	public double battery;
	public boolean charging;
	
	public boolean isAvailable() {
		if (this.customerInTransport == null && this.targetPosition == null) {
			return true;
		}
		return false;
	}
}
