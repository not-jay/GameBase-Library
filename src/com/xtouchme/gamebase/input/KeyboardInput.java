package com.xtouchme.gamebase.input;

import java.awt.event.KeyEvent;

public interface KeyboardInput {

	public enum EventType {
		KEY_PRESS, KEY_RELEASE, KEY_TYPE
	}
	
	public void onKeyPress(KeyEvent e);
	public void onKeyRelease(KeyEvent e);
	public void onKeyType(KeyEvent e);
	
}
