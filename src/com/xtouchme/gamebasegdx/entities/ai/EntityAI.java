package com.xtouchme.gamebasegdx.entities.ai;

import com.badlogic.gdx.math.Vector2;
import com.xtouchme.gamebasegdx.entities.Entity;

public class EntityAI {

	public enum X {
		LEFT, RIGHT, NONE
	}
	public enum Y {
		UP, DOWN, NONE
	}
	
	protected Entity entity		= null;
	protected float maxSpeed	= 0;
//	protected List<Vector2> points;
	
	public EntityAI setEntity(Entity entity) {
		this.entity = entity;
		return this;
	}
	
	public EntityAI move(X x, Y y) {
		int xDirection, yDirection;
		switch(x) {
		case LEFT:	xDirection = -1;	break;
		case RIGHT:	xDirection = 1;		break;
		default:
		case NONE:	xDirection = 0;		break;
		}
		
		switch(y) {
		case UP:	yDirection = -1;	break;
		case DOWN:	yDirection = 1;		break;
		default:
		case NONE:	yDirection = 0;		break;
		}
		
		entity.setSpeed(new Vector2(xDirection * maxSpeed, yDirection * maxSpeed).limit(maxSpeed));
		
		return this;
	}
	
	public EntityAI setMaxSpeed(float maxSpeed) {
		this.maxSpeed = maxSpeed;
		return this;
	}
//	public void followPath() {}
	
}
