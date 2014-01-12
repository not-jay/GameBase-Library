package com.xtouchme.gamebase.entities.collision;

import java.awt.geom.Rectangle2D;

import com.xtouchme.gamebase.entities.Entity;

/** Should change this to a static grid instead of converting dynamic code to static
 *  As it currently stands, this generates FPS drops instead */
public class StaticQuadtree extends Quadtree {
		public StaticQuadtree(int level, float x, float y, float width, float height) {
			this(level, new Rectangle2D.Float(x, y, width, height), 5);
		}
		public StaticQuadtree(int level, Rectangle2D.Float bounds) {
			this(level, bounds, 5);
		}
		public StaticQuadtree(int level, float x, float y, float width, float height, int maxLevels) {
			this(level, new Rectangle2D.Float(x, y, width, height), maxLevels);
		}
		public StaticQuadtree(int level, Rectangle2D.Float bounds, int maxLevels) {
			super(level, bounds, -1, maxLevels);
			if(level < maxLevels) split();
		}
		
		@Override
		public void clear() {
			objects.clear();
			
			for(int i = 0; i < nodes.length; i++) {
				if(nodes[i] != null) nodes[i].clear();
			}
		}
		
		@Override
		public void insert(Entity e) {
			if(nodes[0] != null) {
				int index = getIndex(e);
				
				if(index != -1) {
					nodes[index].insert(e);
					return;
				}
			}
			
			objects.add(e);
		}
		
		private void split() {
			int subWidth = (int)(bounds.getWidth()/2);
			int subHeight = (int)(bounds.getHeight()/2);
			int x = (int)bounds.getX();
			int y = (int)bounds.getY();
			
			nodes[0] = new StaticQuadtree(level+1, x + subWidth, y, subWidth, subHeight, maxLevels);
			nodes[1] = new StaticQuadtree(level+1, x, y, subWidth, subHeight, maxLevels);
			nodes[2] = new StaticQuadtree(level+1, x, y + subHeight, subWidth, subHeight, maxLevels);
			nodes[3] = new StaticQuadtree(level+1, x + subWidth, y + subHeight, subWidth, subHeight, maxLevels);
		}
}
