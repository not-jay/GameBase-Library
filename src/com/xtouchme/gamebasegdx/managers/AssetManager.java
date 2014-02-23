package com.xtouchme.gamebasegdx.managers;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetManager {

	private Map<String, Texture> loadedTextures;
	
	private static AssetManager instance	= null;
	
	public TextureRegion getImage(String pathToTexture) {
		Texture texture = loadTexture(pathToTexture);
		TextureRegion image = new TextureRegion(texture);
		image.flip(false, true);
		
		return image;
	}
	
	public TextureRegion getImage(String pathToTexture, int x, int y, int width, int height) {
		Texture texture = loadTexture(pathToTexture);
		TextureRegion image = new TextureRegion(texture, x, y, width, height);
		image.flip(false, true);
		
		return image;
	}
	
	public void dispose() {
		for(Texture t : loadedTextures.values()) t.dispose();
		loadedTextures.clear();
	}
	
	private Texture loadTexture(String path) {
		Texture texture = new Texture(path);
		loadedTextures.put(path, texture);
		return texture;
	}
	
	/* TODO: change to a JSON parser? */
	/* TODO: return a map of all resources in a .json file? */
	//-- Singleton methods --//
	private AssetManager() {
		loadedTextures = new HashMap<>();
	}
	
	public static AssetManager getInstance() {
		if(instance == null) instance = new AssetManager();
		return instance;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}
}
