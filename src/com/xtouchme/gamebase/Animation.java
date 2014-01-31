package com.xtouchme.gamebase;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import com.xtouchme.gamebase.managers.ResourceManager;

public class Animation {

	private List<AnimationFrame> frames;
	private int currentFrame;
	private long currentDuration;
	private long totalDuration;
	
	private boolean looping		= true;
	
	public Animation() {
		this.frames = new ArrayList<>();
		this.totalDuration = 0;
		
		synchronized (this) {
			currentDuration = 0;
			currentFrame = 0;
		}
	}
	
	public synchronized void addFrame(AnimationFrame frame) {
		addFrame(frame.getFrameImage(), frame.getFrameDuration());
	}
	public synchronized void addFrame(String imagePath, long duration) {
		ResourceManager rm = ResourceManager.getInstance(null);
		addFrame(rm.getImage(imagePath), duration);
	}
	public synchronized void addFrame(Image image, long duration) {
		totalDuration += duration;
		frames.add(new AnimationFrame(image, totalDuration));
	}
	
	public synchronized void update(int delta) {
		if(frames.size() > 1) {
			currentDuration += delta;
			
			if(looping) {
				if(currentDuration >= totalDuration) {
					currentDuration %= totalDuration;
					currentFrame = 0;
				}
			}
			
			while(currentDuration > frames.get(currentFrame).getFrameDuration())
				currentFrame++;
		}
	}
	
	public synchronized Image getImage() {
		if(frames.size() != 0) return frames.get(currentFrame).getFrameImage();
		else return null;
	}
	
	public void loop(boolean looping) {
		this.looping = looping;
	}
	
	public boolean isLooping() {
		return looping;
	}
	
	public boolean isFinished() {
		return (looping)?false:(currentDuration >= totalDuration);
	}
	
	public void setInitialFrame(int frame) {
		for(int i = 0; i < frame; i++, currentFrame++) {
			currentDuration += frames.get(i).getFrameDuration();
		}
	}
	
	public int length() {
		return frames.size();
	}
}
