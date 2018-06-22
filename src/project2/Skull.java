/**
 * Project for SWEN20003: Object Oriented Software Development 2017 
 * Author: William Liandri
 * Student ID: 728710
 * Email: wliandri@student.unimelb.edu.au
 */
package project2;

import org.newdawn.slick.Input;

public class Skull extends Movable {
	
	private static final String SKULL_SRC = "res/skull.png";

	// The skeleton needs to move every 1 second or 1000 miliseconds.
	private static final int LIMIT = 1000; 
	
	private int speed = -App.TILE_SIZE;
	private int time = 0;
	
	public Skull(float x, float y) {
		super(SKULL_SRC, x, y);
	}
	
	@Override
	public void update(Input input, int delta) {
		
		
		// If the Skeleton hits the player, restart the current level.
		if (isHitPlayer(super.getX(), super.getY())) {
			super.setRestared(true);
		}
		
		time += delta;
		
		if (time > LIMIT ) {			
			time -= LIMIT;
			
			// If the movement is blocked by the wall, change the
			// direction of the movement. 
			if (Loader.isBlocked("wall", super.getX(), super.getY() + speed) || 
					Loader.isBlocked("stone",super.getX(), super.getY() + speed)){
				speed = -speed;
			}
			
			// Move to our destination
			super.moveToDest("skeleton", speed);
		}
	}
	
	/**
	 * The enemy cannot be undone, so we need to override and leave it blank.
	 */
	@Override
	public void undoMoves() {

	}
	
	/**
	 * Check if the enemy hits the player.
	 * @param x
	 * @param y
	 * @return
	 */
	private boolean isHitPlayer(float x, float y) {
		
		return Loader.getTypes(x, y).equals("player"); 
		
	}
}
