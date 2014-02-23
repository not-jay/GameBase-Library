package com.xtouchme.gamebasegdx.managers;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.xtouchme.gamebasegdx.entities.Entity;
import com.xtouchme.gamebasegdx.helpers.Renderer;

public class EntityManager {
	
	List<Entity> entities = new ArrayList<>();
	List<Entity> toAdd	  = new ArrayList<>();
	List<Entity> toRemove = new ArrayList<>();
	
	Renderer r;
	OrthographicCamera camera;
	
//	List<Entity> collidables = new ArrayList<>();
//	Quadtree tree		  	 = null;
	
	private float lastDelta = 0;
	
	private static EntityManager instance = null;
	
	/** Gets all entities */
	public List<Entity> getEntities() {
		return entities;
	}
	
	public void add(Entity e) {
		if(e == null) return;
		toAdd.add(e);
	}
	
	public void remove(Entity e) {
		if(e == null) return;
		toRemove.add(e);
	}
	
	public void update(float delta) {
		//Clean up
		synchronized (toRemove) {
			for(Entity e : toRemove) {
				e.onPreRemove();
				entities.remove(e);
				e.onPostRemove();
			}
			toRemove.clear();
		}
		
		synchronized (toAdd) {
			for(Entity e : toAdd) {
				e.onPreAdd();
				entities.add(e);
				e.onPostAdd();
			}
			toAdd.clear();
		}
		
		synchronized (entities) {
//			//Update quadtree
//			tree.clear();
//			for(Entity e : entities) {
//				tree.insert(e);
//			}
//			
//			//check collision
//			for(Entity e : entities) {
//				collidables.clear();
//				tree.retreive(collidables, e);
//				
//				for(Entity other : collidables) {
//					if(e == other || !e.isCollidable() || !other.isCollidable()) continue;
//					if(e.collides(other)) e.collisionResponse(other);
//				}
//			}
			
			//update all entities (including ones to be removed due to collision)
			List<Entity> toUpdate = new ArrayList<>(entities);
			for(Entity e : toUpdate) {
				e.update(delta);
			}
			
			lastDelta = delta;
		}
	}
	
	public void render() {
		int rendered = 0;
		
		Gdx.gl.glClearColor(100/255f, 149/255f, 237/255f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		synchronized (entities) {
			List<Entity> toRender = new ArrayList<>(entities);
			for(Entity e : toRender) {
				if(e.isVisible()) {
					e.render(r);
					rendered++;
				}
			}
		}
		
//		Color def = g.getColor();
//		g.setColor(Color.black);
//		g.drawString(String.format("E: %d", entities.size()), 0, 10);
//		g.drawString(String.format("QA: %d", toAdd.size()), 50, 10);
//		g.drawString(String.format("R: %d", rendered), 0, 20);
//		g.drawString(String.format("QR: %d", toRemove.size()), 50, 20);
//		g.drawString(String.format("D: %d", lastDelta), 0, 30);
//		g.setColor(def);
//		
//		tree.render(g);
	}
	
	//-- Singleton methods --//
	private EntityManager() {
//		SettingsManager sm = SettingsManager.getInstance();
//		tree = new Quadtree(0, 0, 0, sm.gameWidth(), sm.gameHeight(), 10, 3);
		
		camera = new OrthographicCamera();
		camera.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		SpriteBatch batch = new SpriteBatch();
		batch.setProjectionMatrix(camera.combined);
		ShapeRenderer shape = new ShapeRenderer();
		shape.setProjectionMatrix(camera.combined);
		
		r = new Renderer(batch, shape);
	}
	public static EntityManager getInstance() {
		if(instance == null) instance = new EntityManager();
		return instance;
	}
	@Override
	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}
}
