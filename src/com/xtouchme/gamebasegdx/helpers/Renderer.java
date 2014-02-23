package com.xtouchme.gamebasegdx.helpers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Renderer {

	public SpriteBatch sprite;
	public ShapeRenderer shape;
	
	public Renderer(SpriteBatch sprite, ShapeRenderer shape) {
		this.sprite = sprite;
		this.shape = shape;
	}
	
}
