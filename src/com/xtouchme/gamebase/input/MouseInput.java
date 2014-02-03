package com.xtouchme.gamebase.input;

import java.awt.event.MouseEvent;

public interface MouseInput {

	public void onMouseClicked(MouseEvent e);
	public void onMouseEntered(MouseEvent e);
	public void onMouseExited(MouseEvent e);
	public void onMousePressed(MouseEvent e);
	public void onMouseReleased(MouseEvent e);
	
}
