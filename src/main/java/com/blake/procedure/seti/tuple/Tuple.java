package com.blake.procedure.seti.tuple;

import java.io.Serializable;

import com.vividsolutions.jts.geom.Coordinate;

public class Tuple extends Coordinate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	long time;
	
	public Tuple(Double x, Double y, long time) {
		
		super(x,y);
		this.time = time;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "Tuple [time=" + time + ", x=" + super.x + ", y=" + super.y + ", z=" + super.z
				+ "]";
	}

}
