package com.APES.Model;

import java.util.LinkedList;

public class Trace {
	private LinkedList<Point> points;
	private long id;

	public Trace() {
	}

	
	public Trace(LinkedList<Point> points) {
		super();
		this.points = points;
	}


	public Trace(LinkedList<Point> points, long id) {
		this.points = points;
		this.id = id;
	}


	public LinkedList<Point> getPoints() {
		return points;
	}

	public void setPoints(LinkedList<Point> points) {
		this.points = points;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
