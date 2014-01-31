package com.xtouchme.gamebase;

import java.io.Serializable;

public class Vector implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1101195365528347386L;

	public enum Angle {
		DEGREES, RADIAN
	}
	
	private float x;
	private float y;
	
	/** Copy constructor */
	public Vector(Vector d) {
		this(d.x, d.y);
	}
	public Vector(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector add(Vector d) {
		x += d.x;
		y += d.y;
		return this;
	}
	
	public Vector add(float x, float y) {
		this.x += x;
		this.y += y;
		return this;
	}
	
	public Vector subtract(Vector d) {
		x -= d.x;
		y -= d.y;
		return this;
	}
	
	public Vector subtract(float x, float y) {
		this.x -= x;
		this.y -= y;
		return this;
	}
	
	public Vector multiply(Vector d) {
		x *= d.x;
		y *= d.y;
		return this;
	}
	
	public Vector multiply(float x, float y) {
		this.x *= x;
		this.y *= y;
		return this;
	}
	
	public Vector divide(Vector d) {
		x /= d.x;
		y /= d.y;
		return this;
	}
	
	public Vector divide(float x, float y) {
		this.x /= x;
		this.y /= y;
		return this;
	}
	
	public Vector setX(float x) {
		this.x = x;
		return this;
	}
	
	public Vector setY(float y) {
		this.y = y;
		return this;
	}
	
	public float x() {
		return x;
	}
	
	public float y() {
		return y;
	}
	
	public float theta(Angle type) {
		switch(type) {
		case DEGREES:
			return (float)Math.toDegrees(Math.atan2(y, x));
		case RADIAN:
		default:
			return (float)Math.atan2(y, x);
		}
	}
	
	public float r() {
		return (float) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
	}
	
	public float distanceFrom(Vector other) {
		return (float)Math.sqrt(Math.pow(x - other.x, 2) + Math.pow(y - other.y, 2));
	}
	
	public Vector truncate(float max) {
		float magnitude = r();
		if(magnitude > max) {
			float angle = theta(Angle.RADIAN);
			x = (float)(max * Math.cos(angle));
			y = (float)(max * Math.sin(angle));
		}
		return this;
	}
	
	public static Vector polar(float r, float theta) {
		return new Vector((float)(r * Math.cos(Math.toRadians(theta))), (float)(r * Math.sin(Math.toRadians(theta))));
	}
	
	@Override
	public String toString() {
		return String.format("x: %f\ty: %f", x, y);
	}
	
}
