package com.xtouchme.gamebase.managers;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.xtouchme.gamebase.Vector;
import com.xtouchme.gamebase.input.KeyboardInput;
import com.xtouchme.gamebase.input.MouseInput;

public class InputManager implements KeyboardInput, MouseInput {

	private boolean[] keypressed				= new boolean[1024];
	private boolean[] mousepressed				= new boolean[256];
	private HashMap<String, List<Integer>> keys	= new HashMap<>();
	private Vector lastPosition					= null;
	private Vector position						= null;
	
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
		if(keypressed[code]) {
			keypressed[code] = false;
			return true;
		}
		
		return false;
	}
	
	private boolean keyDown(int code) {
		return keypressed[code];
	}
	
	public boolean isMouseClicked(int button) {
		if(mousepressed[button]) {
			mousepressed[button] = false;
			return true;
		}
		
		return false;
	}
	
	public boolean isMouseDown(int button) {
		return mousepressed[button];
	}
	
	public Vector getMousePosition() {
		return position;
	}
	
	public float getMouseX() {
		return position.x();
	}
	
	public float getMouseY() {
		return position.y();
	}
	
	@Override
	public void onKeyPress(KeyEvent e) {
		keypressed[e.getKeyCode()] = true;
	}

	@Override
	public void onKeyRelease(KeyEvent e) {
		keypressed[e.getKeyCode()] = false;
	}

	@Override
	public void onKeyType(KeyEvent e) {}
	
	@Override
	public void onMouseClicked(MouseEvent e) {}

	@Override
	public void onMouseEntered(MouseEvent e) {}

	@Override
	public void onMouseExited(MouseEvent e) {}

	@Override
	public void onMousePressed(MouseEvent e) {
		mousepressed[e.getButton()] = true;
		lastPosition = position;
		position = new Vector(e.getX(), e.getY());
	}

	@Override
	public void onMouseReleased(MouseEvent e) {
		mousepressed[e.getButton()] = false;
		lastPosition = position;
		position = new Vector(e.getX(), e.getY());
	}
	
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
