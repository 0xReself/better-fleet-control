package com.fleet.main;

import java.util.Random;
import java.util.UUID;

import com.fleet.main.types.Position;

public class Customer {
	public UUID id;
	public Position startPosition;
	public Position currentPosition;
	public Position endPosition;
	public boolean pickedUp;
	public String customerColor;
	public double spawnTime;

	public static String[] colorList = {
		"#e11d48",
		"#db2777",
		"#c026d3",
		"#9333ea",
		"#7c3aed",
		"#4f46e5",
		"#2563eb",
		"#0284c7",
		"#0891b2",
		"#0d9488",
		"#0d9488",
		"#059669",
		"#16a34a",
		"#65a30d",
		"#ca8a04",
		"#d97706",
		"#ea580c",
		"#dc2626"
	};
	public boolean plannedToPickUp;

	public Customer(Position startPosition, Position endPosition, double timeStamp) {
		Random rand = new Random();
		this.id = UUID.randomUUID();
		this.startPosition = startPosition;
		this.currentPosition = startPosition.copy();
		this.endPosition = endPosition;
		this.pickedUp = false;
		this.customerColor = colorList[rand.nextInt(colorList.length)];
		this.plannedToPickUp = false;
		this.spawnTime = timeStamp;
	}

	public boolean isAtDestination() {
		return currentPosition.equals(this.endPosition);
	}
}
