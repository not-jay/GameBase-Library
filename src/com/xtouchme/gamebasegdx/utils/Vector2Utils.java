package com.xtouchme.gamebasegdx.utils;

import com.badlogic.gdx.math.Vector2;

public class Vector2Utils {

	public static Vector2 fromPolar(float r, float theta) {
		return new Vector2((float)(r * Math.cos(Math.toRadians(theta))), (float)(r * Math.sin(Math.toRadians(theta))));
	}
	
}
