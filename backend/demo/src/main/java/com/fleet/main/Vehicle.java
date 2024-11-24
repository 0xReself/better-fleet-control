package com.fleet.main;

import java.util.Random;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.node.POJONode;
import com.fleet.main.monitor.MonitorData;
import com.fleet.main.types.Position;

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

	@JsonIgnore
	public Position[] charger = {new Position(48.13, 11.53), new Position(48.16, 11.63)};

	@JsonIgnore
	public static String[] nameList = {
		"Puls XT",
		"Crystal 99",
		"Cyber Sport E",
		"Leo Cupra 600",
		"E63 Xyphos",
		"Olymp XL",
		"Future V",
		"Cyber Sport S",
		"Vision XLS",
		"Revolution",
		"Shine Qadro",
		"Porsche 911",
		"Ente Quak RS",
	};

	public Vehicle(Position initPosition) {
		Random rand = new Random();
		this.id = UUID.randomUUID();
		this.startPosition = initPosition;
		this.currentPosition = initPosition.copy();
		this.targetPosition = null;
		this.targetToPickup = null;
		this.customerInTransport = null;
		this.distanceTraveled = 0.;
		this.activeTime = 0.;
		this.numberOfTrips = 0;
		this.remainingTravelTime = 0.;
		this.vehicleSpeed = .005;
		this.remainingDistancePercentage = 0;
		this.modelName = nameList[rand.nextInt(nameList.length)];
		this.battery = rand.nextDouble(0.4, 1);
	}

	@JsonIgnore
	public boolean isAvailable() {
		if (this.customerInTransport == null && this.targetPosition == null) {
			return true;
		}
		return false;
	}

	public void caluclateRemainingTravelTime() {
		double dist = Math.sqrt(Math.pow(targetPosition.x - currentPosition.x, 2.f)
				+ Math.pow(targetPosition.y - currentPosition.y, 2.f));

		this.remainingTravelTime = dist / vehicleSpeed;
	}

	public double calculatePercentageDistance() {
		double dist_total = Math.sqrt(Math.pow(targetPosition.x - startPosition.x, 2.f)
				+ Math.pow(targetPosition.y - startPosition.y, 2.f));

		double dist = Math.sqrt(Math.pow(targetPosition.x - currentPosition.x, 2.f)
				+ Math.pow(targetPosition.y - currentPosition.y, 2.f));

		return dist / dist_total;
	}

	public double travelNextDistance() {
		double dist = Math.sqrt(Math.pow(targetPosition.x - currentPosition.x, 2.f)
				+ Math.pow(targetPosition.y - currentPosition.y, 2.f));

		if (dist < this.vehicleSpeed) {
			currentPosition = targetPosition.copy();
			this.distanceTraveled += dist;
			this.remainingTravelTime = 0;
			if(customerInTransport != null){
				customerInTransport.currentPosition = targetPosition.copy();
			}
			return dist;
		}

		//move speed distance && if customer move him with
		double x_difference = this.targetPosition.x - this.currentPosition.x;
		double y_difference = this.targetPosition.y - this.currentPosition.y;
		double length = Math.sqrt(Math.pow(x_difference, 2) + Math.pow(y_difference, 2));

		this.currentPosition.x += (x_difference * this.vehicleSpeed) / length;
		this.currentPosition.y += (y_difference * this.vehicleSpeed) / length;

		if(customerInTransport != null) {
			customerInTransport.currentPosition.x += (x_difference * this.vehicleSpeed) / length;
			customerInTransport.currentPosition.y += (y_difference * this.vehicleSpeed) / length;
		}
		
		this.distanceTraveled += vehicleSpeed;
		return vehicleSpeed;
	}

	public void update(double timeStamp) {
		if (targetPosition == null) {
			return;
		}

		this.activeTime += 1.0f;

		caluclateRemainingTravelTime();
		this.remainingDistancePercentage = calculatePercentageDistance();
		double dist = travelNextDistance();

		this.battery = Math.max(this.battery - dist * 1, 0.03);

		if (dist < vehicleSpeed) {
			if (targetToPickup != null) {
				MonitorData.waitingTimeDatapoint.addPoint((timeStamp - targetToPickup.spawnTime) * 0.5);
				MonitorData.emptyTravelDatapoint.addPoint(Scenario.latLanToKm(Position.getDis(this.startPosition, this.targetPosition)));
				customerInTransport = targetToPickup;
				targetPosition = targetToPickup.endPosition.copy();
				targetToPickup.pickedUp = true;
				targetToPickup.plannedToPickUp = false;
				targetToPickup = null;
				this.startPosition = this.currentPosition.copy();
			}else if(customerInTransport != null) {
				MonitorData.fullTravelDatapoint.addPoint(Scenario.latLanToKm(Position.getDis(customerInTransport.startPosition, customerInTransport.endPosition)));
				MonitorData.vehilceUtilizationDatapoint.addPoint(activeTime/timeStamp);
				MonitorData.numberOfTotalTripsDatapoint.addPoint(1);
				customerInTransport.pickedUp = false;
				targetPosition = null;
				customerInTransport = null;
				numberOfTrips += 1;

				if(this.battery < 0.3){
					charging = true;
					double d1 = Position.getDis(this.currentPosition, charger[0]); 
					double d2 = Position.getDis(this.currentPosition, charger[1]); 
					this.startPosition = currentPosition;
					if(d1 < d2){
						this.targetPosition = charger[0].copy();
					}else{
						this.targetPosition = charger[1].copy();
					}
				}
			}else if(this.charging == true){
				MonitorData.emptyTravelDatapoint.addPoint(Scenario.latLanToKm(Position.getDis(startPosition, targetPosition)));
				this.battery = 1.;
				this.charging = false;
				this.targetPosition = null;
			}
		}
	}

	public void setTargetToPickup(Customer customer) {
		if(this.targetToPickup != null) {
			this.targetToPickup.plannedToPickUp = false;
		}
		this.startPosition = this.currentPosition.copy();
		this.targetToPickup = customer;
		customer.plannedToPickUp = true;
		this.targetPosition = customer.startPosition.copy();
		this.vehicleSpeed = Position.rand.nextDouble(.001f, .002f);
	}

	public void setTargetPosition(Position targetPosition) {
		this.targetPosition = targetPosition;
	}
}
