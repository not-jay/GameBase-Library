package com.xtouchme.gamebasegdx.entities.ai;

import com.xtouchme.gamebasegdx.entities.Entity;

public class EntityAI {

	public enum HorizontalMovement {
		LEFT, RIGHT, STOP
	}
	public enum VerticalMovement {
		UP, DOWN, STOP
	}
	
	protected Entity entity;
//	protected List<Vector2> points;
	
	public void move(HorizontalMovement x, VerticalMovement y) {}
//	public void followPath() {}
	
}
