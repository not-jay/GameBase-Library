package com.xtouchme.gamebase.managers;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import com.xtouchme.gamebase.entities.Entity;
import com.xtouchme.gamebase.input.KeyboardInput;

public class EntityManager {
	
	List<Entity> entities = new ArrayList<>();
	List<Entity> toAdd	  = new ArrayList<>();
	List<Entity> toRemove = new ArrayList<>();
	
	private int lastDelta = 0;
	
	private static EntityManager instance = null;
	
	/** Sends a KeyEvent to all entities that implements a KeyboardInput */
	public void broadcastKeyEvent(KeyEvent e, KeyboardInput.EventType type) {
		for(Entity entity : entities) {
			if(entity instanceof KeyboardInput) {
				switch(type) {
				case KEY_PRESS:
					((KeyboardInput)entity).onKeyPress(e);
					break;
				case KEY_RELEASE:
					((KeyboardInput)entity).onKeyRelease(e);
					break;
				case KEY_TYPE:
					((KeyboardInput)entity).onKeyType(e);
					break;
				}
			}
		}
	}
	
	public void add(Entity e) {
		if(e == null) return;
		toAdd.add(e);
	}
	
	public void remove(Entity e) {
		if(e == null) return;
		toRemove.add(e);
	}
	
	public void update(int delta) {
		//Clean up
		for(Entity e : toRemove) {
			entities.remove(e);
		}
		toRemove.clear();
		
		for(Entity e : toAdd) {
			entities.add(e);
		}
		toAdd.clear();
		
		for(Entity e : entities) {
			e.update(delta);
		}
		
		lastDelta = delta;
	}
	
	public void render(Graphics2D g) {
		int rendered = 0;
		for(Entity e : entities) {
			if(e.isVisible()) {
				e.render(g);
				rendered++;
			}
		}
		
		Color def = g.getColor();
		g.setColor(Color.black);
		g.drawString(String.format("E: %d", entities.size()), 0, 10);
		g.drawString(String.format("QA: %d", toAdd.size()), 50, 10);
		g.drawString(String.format("R: %d", rendered), 0, 20);
		g.drawString(String.format("QR: %d", toRemove.size()), 50, 20);
		g.drawString(String.format("D: %d", lastDelta), 0, 30);
		g.setColor(def);
	}
	
	//-- Singleton methods --//
	private EntityManager() {}
	public static EntityManager getInstance() {
		if(instance == null) instance = new EntityManager();
		return instance;
	}
	@Override
	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}
}
