/**
 * Project for SWEN20003: Object Oriented Software Development 2017 
 * Author: William Liandri
 * Student ID: 728710
 * Email: wliandri@student.unimelb.edu.au
 */

package project2;

import org.newdawn.slick.Input;

public class Mage extends Movable {

	private static final String MAGE_SRC = "res/mage.png";
	private static final int SPEED = App.TILE_SIZE;
	
	public Mage(float x, float y) {
		super(MAGE_SRC, x, y);
	}
	
	@Override
	public void update(Input input, int delta) {
		
		
		// If the Mage hits the player, restart the current level.
		if (isHitPlayer(super.getX(), super.getY())) {
			super.setRestared(true);
		}
		
		if (Loader.isPlayerMove()) {
			
			float delta_x = 0;
			float delta_y = 0;
			
			// Calculate the distance between the player and the mage.
			float distX = Loader.getPlayerXCoord() - super.getX();
			float distY = Loader.getPlayerYCoord() - super.getY();
			
			if (Math.abs(distX) > Math.abs(distY)) {
				delta_x = SPEED * sign(distX);
			}

			else  {
				delta_y = SPEED * sign(distY);
			}
			
			// Ensure the movement is not blocked.
			if (canMove(Loader.getTypes(super.getX() + delta_x, super.getY() + delta_y),
					super.getX() + delta_x, super.getY() + delta_y)) {
				super.moveToDest("mage", delta_x, delta_y);
			}
		}
	}
	
	/**
	 * The enemy cannot be undone, so we need to override and leave it blank.
	 */
	@Override
	public void undoMoves() {
		
	}
	
	/**
	 * Determine the sign of the movements.
	 * @param distance
	 * @return
	 */
	private float sign(float distance) {
		
		if (distance < 0 ) {
			return -1;
		}
		
		return 1;
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
