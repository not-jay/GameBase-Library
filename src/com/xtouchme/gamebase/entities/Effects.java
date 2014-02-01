package com.xtouchme.gamebase.entities;

import com.xtouchme.gamebase.Vector;
import com.xtouchme.gamebase.managers.EntityManager;

public class Effects extends Entity {

	private int loopMax;
	private int loops;
	
	public Effects(Vector position) {
		this(position.x(), position.y());
	}
	public Effects(float x, float y) {
		super(x, y);
		
		loopMax = 1;
		loops = 0;
		
		setCentered(true);
	}
	
	public Effects loops(int loops) {
		this.loopMax = loops;
		return this;
	}
	
	@Override
	public void update(int delta) {
		super.update(delta);
		
		if(animation.isFinished()) {
			if((loops + 1) < loopMax) animation.setInitialFrame(0);
			else EntityManager.getInstance().remove(this);
		}
	}
}
