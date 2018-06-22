/**
 * Project for SWEN20003: Object Oriented Software Development 2017 
 * Author: William Liandri
 * Student ID: 728710
 * Email: wliandri@student.unimelb.edu.au
 */

package project2;

import org.newdawn.slick.Input;

public class Ice extends Pushable {
	
	private static final String ICE_SRC = "res/ice.png";
	
	// The ice will move one tile for every 0.25 seconds or 250 miliseconds.
	private static final int LIMIT = 250; 
	
	private int prevDir;
	private boolean hasMoved = false;
	private boolean isUndoDone = false;
	private int time = 0;
	
	public Ice(float x, float y) {
		super(ICE_SRC, x, y);
		prevDir = DIR_NONE;
	}
	
	@Override
	public void update(Input input, int delta) {
		
		// The stone will move if the player pushes the ice.
		if (isPlayerRogue(super.getX(), super.getY()) && !isUndoDone) {
			hasMoved = true;
			time += delta;

			// Record the direction for the passive movement.
			prevDir = Loader.getMoveDir();
			super.objectMove("ice");		
		}
		
		else if (Loader.isPlayerMove() && !hasMoved) {
			super.setxRecord(super.getX());
			super.setyRecord(super.getY());
		}
		
		else if (Loader.isPlayerMove()) {
			super.setxRecord(super.getxRecord(super.getsizexRecord() - 1));
			super.setyRecord(super.getyRecord(super.getsizeyRecord() - 1));
		}
		
		// Active the passive movement after it has been
		// pushed for every 0.25 seconds.
		if (time > LIMIT && hasMoved) {			
			time -= LIMIT;
			objectPassiveMove();
		}
		
		// It has been pushed, but it has not
		// reached 0.25 seconds, so we need to update the time.
		else if (hasMoved) {
			time += delta;
		}
		
		if (Loader.getTypes(super.getX(), super.getY()).equals("ice")) {
			isUndoDone = false;
		}
		
		super.updateTypes("ice", super.getX(), super.getY());
	}
	
	@Override
	public void undoMoves() {
		super.undoMoves();
		hasMoved = false;
		time = 0;
		isUndoDone = true;
	}
	
	/**
	 * The passive movement of the ice after it has been pushed.
	 * @param input
	 * @param delta
	 */
	private void objectPassiveMove() {
		
		// Get the direction of the movements.
		float[] deltaDir = super.getDeltaDir(prevDir);
		float delta_x = deltaDir[0];
		float delta_y = deltaDir[1];
		
		// Ensure the movement is not blocked.
		if (super.canMove(Loader.getTypes(super.getX() + delta_x, super.getY() + delta_y), 
				super.getX() + delta_x, super.getY() + delta_y)) {
			super.updateTypes("ice", super.getX() + delta_x, super.getY() + delta_y);
			super.setX(super.getX() + delta_x);
			super.setY(super.getY() + delta_y);
		}
		
		// If the movement is blocked, stop the movement.
		else {
			hasMoved = false;
			time = 0;
		}
	}
}
