package com.xtouchme.gamebase.managers;

import java.io.Serializable;

import com.xtouchme.gamebase.Vector;

public class SettingsManager implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -509330800989451979L;

	private Vector resolution;
	
	//Collision Detection Variables
	private int maxObjects;
	private int maxLevels;
	
	private static SettingsManager instance = null;
	
	public SettingsManager setResolution(int width, int height) {
		return setResolution(new Vector(width, height));
	}
	
	public SettingsManager setResolution(Vector resolution) {
		this.resolution = resolution;
		return this;
	}
	
	public int gameWidth() {
		return (int)resolution.x();
	}
	
	public int gameHeight() {
		return (int)resolution.y();
	}
	
	public SettingsManager setMaxCollisionObjects(int maxObjects) {
		this.maxObjects = maxObjects;
		return this;
	}
	
	public SettingsManager setMaxCollisionLevels(int maxLevels) {
		this.maxLevels = maxLevels;
		return this;
	}
	
	public int maxCollisionObjects() {
		return maxObjects;
	}
	
	public int maxCollisionLevels() {
		return maxLevels;
	}
	
	//-- Singleton methods --//
	private SettingsManager() { 
		this.maxObjects = 10;
		this.maxLevels = 3;
	}
	public static SettingsManager getInstance() {
		if(instance == null) instance = new SettingsManager();
		return instance;
	}
	@Override
	protected Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}
}
