package com.fleet.main.types;

import java.util.Random;

public class Position {
	public static Random rand = new Random();
	public double x;
	public double y;
	static final double MUNICH_XMIN = 48.113000f;
	static final double MUNICH_XMAX = 48.165312f;
	static final double MUNICH_YMIN = 11.503302f;
	static final double MUNICH_YMAX = 11.646708f;

	public Position(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Position() {
		this.x = rand.nextDouble(MUNICH_XMAX - MUNICH_XMIN) + MUNICH_XMIN;
		this.y = rand.nextDouble(MUNICH_YMAX - MUNICH_YMIN) + MUNICH_YMIN;
	}

	public Position copy(){
		return new Position(this.x, this.y);
	}

	public static double getDis(Position a, Position b){
		double d_x = Math.abs(a.x - b.x);
		double d_y = Math.abs(a.y - b.y);
		return Math.sqrt(Math.pow(d_x, 2.f)
		+ Math.pow(d_y, 2.f));
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

		if (Math.abs(this.x - other.x) > 0.00000001 || Math.abs(this.y - other.y) > 0.00000001) {
			return false;
		}

		return true;
	}
}
