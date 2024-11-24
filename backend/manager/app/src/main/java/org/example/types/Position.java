package org.example.types;

import java.util.Random;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.ALWAYS)
public class Position {
	public static Random rand = new Random();
	@JsonProperty
	public double x;
	@JsonProperty
	public double y;

	public Position(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Position() {
		this.x = rand.nextDouble(50.f);
		this.y = rand.nextDouble(50.f);
	}

	public Position copy(){
		return new Position(this.x, this.y);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (obj.getClass() != this.getClass()) {
			return false;
		}

		final Position other = (Position) obj;

		if (Math.abs(this.x - other.x) > 0.1f || Math.abs(this.y - other.y) > 0.1f) {
			return false;
		}

		return true;
	}
}
