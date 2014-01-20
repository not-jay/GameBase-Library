package com.xtouchme.gamebase.managers;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.xtouchme.gamebase.input.KeyboardInput;

public class InputManager implements KeyboardInput {

	private boolean[] pressed					= new boolean[1024];
	private HashMap<String, List<Integer>> keys	= new HashMap<>();
	
	private static InputManager instance		= null;
	
	public void define(String command, int... keys) {
		List<Integer> keyArray = new ArrayList<>();
		for(int x : keys) keyArray.add(x);
		
		this.keys.put(command, keyArray);
	}
	
	public void undefine(String command) {
		keys.remove(command);
	}
	
	public boolean isKeyDown(String command) {
		if(!keys.containsKey(command)) return false;
		for(int x : keys.get(command)) {
			if(keyDown(x)) return true;
		}
		
		return false;
	}
	
	public boolean isKeyPressed(String command) {
		if(!keys.containsKey(command)) return false;
		for(int x : keys.get(command)) {
			if(keyPressed(x)) return true;
		}
		
		return false;
	}
	
	private boolean keyPressed(int code) {
		if(pressed[code]) {
			pressed[code] = false;
			return true;
		}
		
		return false;
	}
	
	private boolean keyDown(int code) {
		return pressed[code];
	}
	
	@Override
	public void onKeyPress(KeyEvent e) {
		pressed[e.getKeyCode()] = true;
	}

	@Override
	public void onKeyRelease(KeyEvent e) {
		pressed[e.getKeyCode()] = false;
	}

	@Override
	public void onKeyType(KeyEvent e) {}
	
	//-- Singleton methods --//
	private InputManager() {}
	public static InputManager getInstance() {
		if(instance == null) instance = new InputManager();
		return instance;
	}
	@Override
	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}
}
