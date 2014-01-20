package com.xtouchme.gamebase.input;

import java.awt.event.KeyEvent;

public interface KeyboardInput {

	public void onKeyPress(KeyEvent e);
	public void onKeyRelease(KeyEvent e);
	public void onKeyType(KeyEvent e);
	
}
