package org.example.types;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Customer {
	public UUID id;
	public Position startPosition;
	public Position currentPosition;
	public Position endPosition;
	public boolean pickedUp;
	public boolean plannedToPickUp;
	public boolean atDestination;
	public String customerColor;
	public double spawnTime;

	public boolean isAtDestination() {
		return currentPosition.equals(this.endPosition);
	}
}
