package com.xtouchme.gamebase.entities;

import java.awt.Color;
import java.awt.Graphics2D;

import com.xtouchme.gamebase.Dimension;

public class PhysicsEntity extends Entity {

	protected Dimension limit			= new Dimension(-1, -1);
	protected Dimension acceleration	= new Dimension(0, 0);
	protected Dimension drag			= new Dimension(0, 0);
	
	public PhysicsEntity(float x, float y) {
		super(x, y);
	}

	@Override
	public void update(int delta) {
		lastPosition.setX(position.x()).setY(position.y());
		
		/* Drag physics lol
		 * Basically, apply drag until speed is 0*/
		speed.add(acceleration);
		if(acceleration.x() == 0) {
			if(speed.x() > 0) {
				if(speed.x() - drag.x() <= 0) speed.setX(0);
				else speed.setX(speed.x() - drag.x());
			} else {
				if(speed.x() + drag.x() >= 0) speed.setX(0);
				else speed.setX(speed.x() + drag.x());
			}
			
			if(speed.y() > 0) {
				if(speed.y() - drag.y() <= 0) speed.setY(0);
				else speed.setY(speed.y() - drag.y());
			} else {
				if(speed.y() + drag.y() >= 0) speed.setY(0);
				else speed.setY(speed.y() + drag.y());
			}
		}
		
		/* Speed limiters: They're enabled if they aren't -1
		 * And are only applied if speed is != 0 */
		if(limit.x() != -1) {
			if(speed.x() != 0) {
				if(speed.x() > limit.x()) speed.setX(limit.x());
				else if(-speed.x() > limit.x()) speed.setX(-limit.x());
			}
			if(speed.y() != 0) {
				if(speed.y() > limit.y()) speed.setY(limit.y());
				else if(-speed.y() > limit.y()) speed.setY(-limit.y());
			}
		}
		
		if(following != null) position = following.position;
		else position.add(speed);
	}
	
	@Override
	public void render(Graphics2D g) {
		super.render(g);
		
		/* Acceleration Lines? */
		Color def = g.getColor();
		g.setColor(Color.red);
		g.drawLine((int)position.x(), (int)position.y(), (int)(position.x() + (acceleration.x() * acceleration.x())), (int)(position.y() + (acceleration.y() * acceleration.y())));
		g.setColor(def);
	}
	
	public PhysicsEntity setDrag(Dimension drag) {
		this.drag = drag;
		return this;
	}
	
	public PhysicsEntity setDrag(float x, float y) {
		return setDrag(new Dimension(x, y));
	}
	
	public PhysicsEntity limitSpeed(Dimension limit) {
		this.limit = limit;
		return this;
	}
	
	public PhysicsEntity limitSpeed(float x, float y) {
		return limitSpeed(new Dimension(x, y));
	}
	
	public PhysicsEntity setAcceleration(Dimension acceleration) {
		this.acceleration = acceleration;
		return this;
	}
	
	public PhysicsEntity setAcceleration(float x, float y) {
		return setAcceleration(new Dimension(x, y));
	}
	
}
