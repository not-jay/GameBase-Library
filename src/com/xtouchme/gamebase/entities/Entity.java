package com.xtouchme.gamebase.entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;

import com.xtouchme.gamebase.phys.Dimension;

public class Entity {

	protected Dimension position		= new Dimension(0, 0);
	protected Dimension lastPosition	= new Dimension(0, 0);
	protected Dimension speed			= new Dimension(0, 0);
	private int width					= 0;
	private int height					= 0;
	private boolean centered			= false;
	private boolean collidable			= false;
	private float angle					= 0;
	
	protected Image sprite				= null;
	
	public Entity(float x, float y) {
		setPosition(x, y);
	}
	
	public void render(Graphics2D g) {
		AffineTransform defTrans = g.getTransform();
		if(angle != 0) 	g.rotate(Math.toRadians(angle), position.x(), position.y());
		
		if(sprite != null) {
			if(width == 0) width = sprite.getWidth(null);
			if(height == 0) height = sprite.getHeight(null);
			if(centered)
				g.drawImage(sprite, (int)(position.x() - (width/2)), (int)(position.y() - (height/2)), null);
			else
				g.drawImage(sprite, (int)position.x(), (int)position.y(), null);
		}
		
		/* "Hitbox" */
		Color def = g.getColor();
		g.setColor(Color.magenta);
		if(centered)
			g.drawRect((int)(position.x() - (width/2)), (int)(position.y() - (height/2)), width, height);
		else
			g.drawRect((int)position.x(), (int)position.y(), width, height);
		g.setColor(def);
		
		if(angle != 0) g.setTransform(defTrans);
		
		/* Speed Lines? */
		g.setColor(Color.blue);
		g.drawLine((int)position.x(), (int)position.y(), (int)(position.x() + speed.x()), (int)(position.y() + speed.y()));
		g.setColor(def);
	}
	
	public void update(int delta) {
		lastPosition.setX(position.x()).setY(position.y());
		position.add(speed);
	}
	
	public Entity setSprite(Image sprite) {
		this.sprite = sprite;
		width = sprite.getWidth(null);
		height = sprite.getHeight(null);
		return this;
	}
	
	public int width() {
		return width;
	}
	
	public int height() {
		return height;
	}
	
	public Entity setPosition(float x, float y) {
		lastPosition.setX(position.x()).setY(position.y());
		position.setX(x).setY(y);
		return this;
	}
	
	public Entity setSpeed(Dimension speed) {
		this.speed = speed;
		return this;
	}
	
	public Entity setSpeed(float x, float y) {
		return setSpeed(new Dimension(x, y));
	}
	
	public Entity setCentered(boolean centered) {
		this.centered = centered;
		return this;
	}
	
	public Entity setCollidable(boolean collidable) {
		this.collidable = collidable;
		return this;
	}
	
	public boolean isCollidable() {
		return collidable;
	}
	
	public boolean isVisible() { //TODO
		if(centered) return position.x() + (width/2) >= 0 && position.x() - (width/2) <= 480 && position.y() + (height/2) >= 0 && position.y() - (height/2) <= 800; //TODO
		else return position.x() + width >= 0 && position.x() <= 480 && position.y() + height >= 0 && position.y() <= 800; //TODO
	}
	
	public Entity setAngle(float angle) {
		this.angle = angle;
		return this;
	}
	
	public float angle() {
		return angle;
	}
	
	public Entity follow(Entity e) {
		setSpeed(e.speed);
		return this;
	}
}
