package com.xtouchme.gamebase.entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.util.HashMap;

import com.xtouchme.gamebase.Animation;
import com.xtouchme.gamebase.AnimationFrame;
import com.xtouchme.gamebase.Vector;
import com.xtouchme.gamebase.managers.ResourceManager;

public class Entity {

	protected Vector position				= new Vector(0, 0);
	protected Vector lastPosition			= new Vector(0, 0);
	protected Vector speed					= new Vector(0, 0);
	private int width						= 0;
	private int height						= 0;
	private boolean centered				= false;
	private boolean collidable				= false;
	private float angle						= 0;
	private Shape hitbox					= null;
	protected Entity following				= null;
	
	private HashMap<String, Image> imageMap	= new HashMap<>();
	protected Image sprite					= null;
	protected Animation animation			= null;
	
	public Entity(float x, float y) {
		setPosition(x, y);
	}
	
	public void updateHitbox() {}
	public void collisionResponse() {}
	public void onPreAdd() {}
	public void onPostAdd() {}
	public void onPreRemove() {}
	public void onPostRemove() {}
	
	public void render(Graphics2D g) {
		AffineTransform defTrans = g.getTransform();
		if(angle != 0) 	g.rotate(Math.toRadians(angle), position.x(), position.y());
		
		if(animation != null) {
			if(width == 0) width = animation.getImage().getWidth(null);
			if(height == 0) height = animation.getImage().getHeight(null);
			if(centered)
				g.drawImage(animation.getImage(), (int)(position.x() - (width/2)), (int)(position.y() - (height/2)), null);
			else
				g.drawImage(animation.getImage(), (int)position.x(), (int)position.y(), null);
		} else if(sprite != null) {
			if(width == 0) width = sprite.getWidth(null);
			if(height == 0) height = sprite.getHeight(null);
			if(centered)
				g.drawImage(sprite, (int)(position.x() - (width/2)), (int)(position.y() - (height/2)), null);
			else
				g.drawImage(sprite, (int)position.x(), (int)position.y(), null);
		}
		if(angle != 0) g.setTransform(defTrans);
		
		/* "Hitbox" */
		Color def = g.getColor();
		g.setColor(Color.MAGENTA);
		if(hitbox != null) {
			g.draw(hitbox);
		}
		g.setColor(def);
		
		/* Speed Lines? */
		g.setColor(Color.blue);
		g.drawLine((int)position.x(), (int)position.y(), (int)(position.x() + speed.x()), (int)(position.y() + speed.y()));
		g.setColor(def);
	}
	
	public void update(int delta) {
		if(animation != null) animation.update(delta);
		
		lastPosition.setX(position.x()).setY(position.y());
		if(following != null) position.setX(following.x()).setY(following.y());
		else position.add(speed);
		updateHitbox();
	}
	
	public Entity setAnimation(AnimationFrame... frames) {
		Animation a = new Animation();
		for(AnimationFrame f : frames) {
			a.addFrame(f);
		}
		return setAnimation(a);
	}
	
	public Entity setAnimation(Animation animation) {
		this.animation = animation;
		return this;
	}
	
	public Entity setSprite(Image sprite) {
		this.sprite = sprite;
		width = sprite.getWidth(null);
		height = sprite.getHeight(null);
		return this;
	}
	
	public float x() {
		return position.x();
	}
	
	public float y() {
		return position.y();
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
	
	public Entity setSpeed(Vector speed) {
		this.speed = speed;
		return this;
	}
	
	public Entity setSpeed(float x, float y) {
		return setSpeed(new Vector(x, y));
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
		if(angle < 0) angle += 360 * ((int)Math.abs(angle/360)+1);
		angle %= 360;
			
		this.angle = angle;
		return this;
	}
	
	public float angle() {
		return angle;
	}
	
	public float distanceFrom(Entity other) {
		return position.distanceFrom(other.position);
	}
	
	public Entity follow(Entity following) {
		this.following = following;
		return this;
	}
	
	public Entity setHitbox(Shape hitbox) {
		this.hitbox = hitbox;
		return this;
	}
	
	public boolean collides(Entity other) {
		if(hitbox == null) return false;
		Area a = new Area(hitbox);
		Area b = new Area(other.hitbox);
		a.intersect(b);
		
		return !a.isEmpty();
	}
	
	public void addSprite(String tag, String imagePath) {
		ResourceManager rm = ResourceManager.getInstance(null);
		addSprite(tag, rm.getImage(imagePath));
	}
	public void addSprite(String tag, Image img) {
		imageMap.put(tag, img);
	}
	
	public void switchSprite(String tag) {
		if(!imageMap.containsKey(tag)) return;
		setSprite(imageMap.get(tag));
	}
}
