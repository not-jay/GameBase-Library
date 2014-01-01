package com.xtouchme.gamebase.phys;

public class Dimension {

	private float x;
	private float y;
	
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
	
	@Override
	public String toString() {
		return String.format("x: %f\ty: %f", x, y);
	}
	
}
