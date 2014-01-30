package com.xtouchme.gamebase.entities.collision;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import com.xtouchme.gamebase.entities.Entity;

public class Quadtree {
	
	protected int maxObjects;
	protected int maxLevels;
	protected int level;
	protected List<Entity> objects;
	protected Rectangle2D bounds;
	protected Quadtree[] nodes;
	
	public Quadtree(int level, float x, float y, float width, float height) {
		this(level, new Rectangle2D.Float(x, y, width, height), 10, 5);
	}
	public Quadtree(int level, Rectangle2D bounds) {
		this(level, bounds, 10, 5);
	}
	public Quadtree(int level, float x, float y, float width, float height, int maxObjects, int maxLevels) {
		this(level, new Rectangle2D.Float(x, y, width, height), maxObjects, maxLevels);
	}
	public Quadtree(int level, Rectangle2D bounds, int maxObjects, int maxLevels) {
		this.level = level;
		this.bounds = bounds;
		this.maxObjects = maxObjects;
		this.maxLevels = maxLevels;
		this.objects = new ArrayList<>();
		this.nodes = new Quadtree[4];
	}
	
	/* For debug purposes */
	public void render(Graphics2D g) {
		Color def = g.getColor();
		g.setColor(Color.darkGray);
		
		for(int i = 0; i < nodes.length; i++) {
			if(nodes[i] != null) nodes[i].render(g);
		}
		g.draw(bounds);
		
		g.setColor(def);
	}
	
	public void clear() {
		objects.clear();
		
		for(int i = 0; i < nodes.length; i++) {
			if(nodes[i] != null) {
				nodes[i].clear();
				nodes[i] = null;
			}
		}
	}
	
	public void insert(Entity e) {
		if(nodes[0] != null) {
			int index = getIndex(e);
			
			if(index != -1) {
				nodes[index].insert(e);
				return;
			}
		}
		
		objects.add(e);
		
		if(objects.size() > maxObjects && level < maxLevels) {
			if(nodes[0] == null) split();
			
			int i = 0;
			while(i < objects.size()) {
				int index = getIndex(objects.get(i));
				if(index != -1) nodes[index].insert(objects.remove(i));
				else i++;
			}
		}
	}
	
	public List<Entity> retreive(List<Entity> returnObjects, Entity e) {
		int index = getIndex(e);
		if(index != -1 && nodes[0] != null) nodes[index].retreive(returnObjects, e);
		
		returnObjects.addAll(objects);
		
		return returnObjects;
	}
	
	private void split() {
		int subWidth = (int)(bounds.getWidth()/2);
		int subHeight = (int)(bounds.getHeight()/2);
		int x = (int)bounds.getX();
		int y = (int)bounds.getY();
		
		nodes[0] = new Quadtree(level+1, x + subWidth, y, subWidth, subHeight, maxObjects, maxLevels);
		nodes[1] = new Quadtree(level+1, x, y, subWidth, subHeight, maxObjects, maxLevels);
		nodes[2] = new Quadtree(level+1, x, y + subHeight, subWidth, subHeight, maxObjects, maxLevels);
		nodes[3] = new Quadtree(level+1, x + subWidth, y + subHeight, subWidth, subHeight, maxObjects, maxLevels);
	}
	
	protected int getIndex(Entity e) {
		int index = -1;
		double verticalMidpoint = bounds.getX() + (bounds.getWidth() / 2);
		double horizontalMidpoint = bounds.getY() + (bounds.getHeight() / 2);
		
		boolean topQuadrant = e.y() < horizontalMidpoint && e.y() + e.height() < horizontalMidpoint;
		boolean bottomQuadrant = e.y() > horizontalMidpoint;
		
		if(e.x() < verticalMidpoint && e.x() + e.width() < verticalMidpoint) {
			if(topQuadrant) {
				index = 1;
			} else if(bottomQuadrant) {
				index = 2;
			}
		} else if(e.x() > verticalMidpoint) {
			if(topQuadrant) {
				index = 0;
			} else if(bottomQuadrant) {
				index = 3;
			}
		}
		
		return index;
	}
	
}
