package com.xtouchme.gamebasegdx.entities;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.xtouchme.gamebasegdx.GB;
import com.xtouchme.gamebasegdx.entities.ai.EntityAI;
import com.xtouchme.gamebasegdx.helpers.Renderer;
import com.xtouchme.gamebasegdx.managers.AssetManager;

public class Entity {

	public static final float WORLD_TO_BOX			= 0.01f;
	public static final float BOX_TO_WORLD			= 100f;
	public enum HitboxShape {
		BOX, CIRCLE
	}
	
	protected Vector2 position						= new Vector2(0, 0);
	protected Vector2 lastPosition					= new Vector2(0, 0);
	protected Vector2 speed							= new Vector2(0, 0);
	private int width								= 0;
	private int height								= 0;
	private boolean centered						= false;
	private boolean collidable						= false;
	private float angle								= 0;
	protected Entity following						= null;
	
	private EntityAI ai								= null;
	
	private HashMap<String, TextureRegion> imageMap	= new HashMap<>();
	protected TextureRegion sprite					= null;
	protected Animation animation					= null;
	private float elapsedTime						= 0;
	private boolean animLoop						= true;
	
	public Entity(float x, float y) {
		setPosition(x, y);
	}
	
	/** Blank Override-able method **/
	public void updateHitbox() {}
	/** Blank Override-able method **/
	public void collisionResponse(Entity other) {}
	/** Blank Override-able method **/
	public void onPreAdd() {}
	/** Blank Override-able method **/
	public void onPostAdd() {}
	/** Blank Override-able method **/
	public void onPreRemove() {}
	/** Blank Override-able method **/
	public void onPostRemove() {}
	
	public void render(Renderer r) {
		if(sprite != null) {
			r.sprite.begin();
			if(centered)
				r.sprite.draw(sprite, position.x - width/2, position.y - height/2, 0, 0, width, height, 1, 1, angle);
			else
				r.sprite.draw(sprite, position.x, position.y, 0, 0, width, height, 1, 1, angle);
			r.sprite.end();
		}
		
		/* "Hitbox" */
		if(GB.debug) {
			r.shape.begin(ShapeType.Line);
			if(centered)
				r.shape.rect(position.x - width/2, position.y - height/2, width, height, Color.MAGENTA, Color.MAGENTA, Color.MAGENTA, Color.MAGENTA);
			else
				r.shape.rect(position.x, position.y, width, height, Color.MAGENTA, Color.MAGENTA, Color.MAGENTA, Color.MAGENTA);
			r.shape.end();
		}
		
//		/* Speed Lines? */
//		g.setColor(Color.blue);
//		g.drawLine((int)position.x(), (int)position.y(), (int)(position.x() + speed.x()), (int)(position.y() + speed.y()));
//		g.setColor(def);
	}
	
	public void update(float delta) {
//		if(animation != null) animation.update(delta);
		if(animation != null) sprite = animation.getKeyFrame(elapsedTime += delta, animLoop);
		
		lastPosition.set(position);
		if(following != null) position.set(following.position);
		else position.add(speed);
		updateHitbox();
	}
	
//	public Entity setAnimation(boolean looping, AnimationFrame... frames) {
//		Animation a = new Animation().loop(looping);
//		for(AnimationFrame f : frames) {
//			a.addFrame(f);
//		}
//		return setAnimation(a);
//	}
//	
//	public Entity setAnimation(Animation animation) {
//		this.animation = animation;
//		return this;
//	}
	
	public Entity setSprite(String pathToTexture) {
		AssetManager am = AssetManager.getInstance();
		return setSprite(am.getImage(pathToTexture));
	}
	
	public Entity setSprite(String pathToTexture, int x, int y, int width, int height) {
		AssetManager am = AssetManager.getInstance();
		return setSprite(am.getImage(pathToTexture, x, y, width, height));
	}
	
	public Entity setSprite(TextureRegion sprite) {
		this.sprite = sprite;
		width = sprite.getRegionWidth();
		height = sprite.getRegionHeight();
		return this;
	}
	
	public float x() {
		return position.x;
	}
	
	public float y() {
		return position.y;
	}
	
	public int width() {
		return width;
	}
	
	public int height() {
		return height;
	}
	
	public Entity setPosition(float x, float y) {
		lastPosition.set(position);
		position.set(x, y);
		return this;
	}
	
	public Entity setSpeed(Vector2 speed) {
		this.speed = speed;
		return this;
	}
	
	public Entity setSpeed(float x, float y) {
		return setSpeed(new Vector2(x, y));
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
	
	public boolean isVisible() {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		if(centered) return position.x + (width/2) >= 0 && position.x - (width/2) <= w && position.y + (height/2) >= 0 && position.y - (height/2) <= h;
		else return position.x + width >= 0 && position.x <= w && position.y + height >= 0 && position.y <= h;
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
		return position.dst(other.position);
	}
	
	public Entity follow(Entity following) {
		this.following = following;
		return this;
	}
	
//	public Entity setHitbox(float width, float height) {
//		
//	}
	
	public boolean collides(Entity other) {
//		if(hitbox == null) return false;
//		Area a = new Area(hitbox);
//		Area b = new Area(other.hitbox);
//		a.intersect(b);
//		
//		return !a.isEmpty();
		return false;
	}
	
	public Entity addSprite(String tag, String imagePath) {
		AssetManager am = AssetManager.getInstance();
		return addSprite(tag, am.getImage(imagePath));
	}
	
	public Entity addSprite(String tag, String imagePath, int x, int y, int width, int height) {
		AssetManager am = AssetManager.getInstance();
		return addSprite(tag, am.getImage(imagePath, x, y, width, height));
	}
	
	public Entity addSprite(String tag, TextureRegion img) {
		imageMap.put(tag, img);
		return this;
	}
	
	public void switchSprite(String tag) {
		if(!imageMap.containsKey(tag)) return;
		setSprite(imageMap.get(tag));
	}
	
	public Entity setInitialAnimationFrame(int frame) {
//		this.animation.setInitialFrame(frame);
		return this;
	}
	
	public Entity setWidth(int width) {
		this.width = width;
		return this;
	}
	
	public Entity setHeight(int height) {
		this.height = height;
		return this;
	}
	
	public Entity setAI(EntityAI ai) {
		this.ai = ai;
		return this;
	}
	
	public EntityAI getAI() {
		return ai;
	}
}
