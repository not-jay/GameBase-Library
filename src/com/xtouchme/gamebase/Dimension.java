package com.xtouchme.gamebase;

public class Dimension {

	private float x;
	private float y;
	
	/** Copy constructor */
	public Dimension(Dimension d) {
		this(d.x, d.y);
	}
	public Dimension(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public Dimension add(Dimension d) {
		x += d.x;
		y += d.y;
		return this;
	}
	
	public Dimension add(float x, float y) {
		this.x += x;
		this.y += y;
		return this;
	}
	
	public Dimension subtract(Dimension d) {
		x -= d.x;
		y -= d.y;
		return this;
	}
	
	public Dimension subtract(float x, float y) {
		this.x -= x;
		this.y -= y;
		return this;
	}
	
	public Dimension setX(float x) {
		this.x = x;
		return this;
	}
	
	public Dimension setY(float y) {
		this.y = y;
		return this;
	}
	
	public float x() {
		return x;
	}
	
	public float y() {
		return y;
	}
	
	public float distanceFrom(Dimension other) {
		return (float)Math.sqrt(Math.pow(x - other.x, 2) + Math.pow(y - other.y, 2));
	}
	
	@Override
	public String toString() {
		return String.format("x: %f\ty: %f", x, y);
	}
	
}
