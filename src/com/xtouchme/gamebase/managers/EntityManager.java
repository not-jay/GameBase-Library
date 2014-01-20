package com.xtouchme.gamebase.managers;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import com.xtouchme.gamebase.entities.Entity;
import com.xtouchme.gamebase.entities.collision.Quadtree;

public class EntityManager {
	
	List<Entity> entities = new ArrayList<>();
	List<Entity> toAdd	  = new ArrayList<>();
	List<Entity> toRemove = new ArrayList<>();
	Quadtree tree		  = new Quadtree(0, 0, 0, 480, 800, 10, 3); //TODO: GameManager
	List<Entity> collidables = new ArrayList<>();
	
	private int lastDelta = 0;
	
	private static EntityManager instance = null;
	
	/** Gets all entities */
	public List<Entity> getEntities() {
		return entities;
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
		synchronized (toRemove) {
			for(Entity e : toRemove) {
				entities.remove(e);
			}
			toRemove.clear();
		}
		
		synchronized (toAdd) {
			for(Entity e : toAdd) {
				entities.add(e);
			}
			toAdd.clear();
		}
		
		synchronized (entities) {
			//Update quadtree
			tree.clear();
			for(Entity e : entities) {
				tree.insert(e);
			}
			
			//check collision
			for(Entity e : entities) {
				collidables.clear();
				tree.retreive(collidables, e);
				
				for(Entity other : collidables) {
					if(e == other || !e.isCollidable() || !other.isCollidable()) continue;
					if(e.collides(other)) e.collisionResponse();
				}
			}
			
			//update all entities (including ones to be removed due to collision)
			for(Entity e : entities) {
				e.update(delta);
			}
			
			lastDelta = delta;
		}
	}
	
	public void render(Graphics2D g) {
		int rendered = 0;
		synchronized (entities) {
			for(Entity e : entities) {
				if(e.isVisible()) {
					e.render(g);
					rendered++;
				}
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
		
		tree.render(g);
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
