package com.xtouchme.gamebase;

import java.awt.Image;

import com.xtouchme.gamebase.managers.ResourceManager;

public class AnimationFrame {

	private Image frameImage;
	private long frameDuration;
	
	public AnimationFrame(String frameImage, long frameDuration) {
		this(ResourceManager.getInstance(null).getImage(frameImage), frameDuration);
	}
	public AnimationFrame(Image frameImage, long frameDuration) {
		this.frameImage = frameImage;
		this.frameDuration = frameDuration;
	}
	
	public Image getFrameImage() {
		return frameImage;
	}
	
	public long getFrameDuration() {
		return frameDuration;
	}
	
}
