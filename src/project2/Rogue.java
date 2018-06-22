/**
 * Project for SWEN20003: Object Oriented Software Development 2017 
 * Author: William Liandri
 * Student ID: 728710
 * Email: wliandri@student.unimelb.edu.au
 */

package project2;

import org.newdawn.slick.Input;

public class Rogue extends Movable {
	
	private static final String ROGUE_SRC = "res/rogue.png";
	private int speed = -App.TILE_SIZE;
	
	public Rogue(float x, float y) {
		super(ROGUE_SRC, x, y);
	}
	
	@Override
	public void update(Input input, int delta) {
		
		
		// If the Rogue hits the player, restart the current level.
		if (isHitPlayer(super.getX(), super.getY())) {
			super.setRestared(true);
		}
		
		// Moves everytime the player makes a movement.
		if (Loader.isPlayerMove()) {
			
			// If the movement is blocked by the wall, change the
			// direction of the movement. 
			if (Loader.isBlocked("wall", super.getX() + speed, super.getY()) || 
					(Loader.isBlocked("wall", super.getX() + 2*speed, super.getY()) 
							&& isMovableObject(super.getX() + speed, super.getY()))) {
				speed = -speed;
			}
			
			else {
				
				// Deadlock condition where there is Rogue, Stone, Player, and Wall, so
				// the Rogue cannot move.
				if (isMovableObject(super.getX() + speed, super.getY()) && 
						Loader.isBlocked("player", super.getX() + 2*speed, super.getY()) && 
						Loader.isBlocked("wall", super.getX() + 3*speed, super.getY())) {
					
					super.updateTypes("rogue", super.getX(), super.getY());
				}
				
				// Check if there is stone and check
				// whether the stone can moved or not. If it can be moved, then the 
				// rogue will also move. 
				else if (isMovableObject(super.getX() + speed, super.getY()) && 
						canMove(Loader.getTypes(super.getX() + speed, super.getY()),
								super.getX() + 2*speed, super.getY()) && 
						!Loader.isBlocked("player", super.getX() + 2*speed, super.getY())) {

					// Get the direction for the stone.
					int dir = DIR_LEFT;
					if (speed == App.TILE_SIZE) {
						dir = DIR_RIGHT;
					}
					Loader.setMoveDir(dir);	
					super.moveToDest("rogue", speed);
				}
				
				else {
					super.moveToDest("rogue", speed);
				}
			
			}
			
			super.updateTypes("rogue", super.getX(), super.getY());
		}
	}
	
	/**
	 * The enemy cannot be undone, so we need to override and leave it blank.
	 */
	@Override
	public void undoMoves() {

	}
	
	/**
	 * Check whether there is movable object (either TNT or ice or stone)
	 * that can be moved by the player.
	 * @param x
	 * @param y
	 * @return
	 */
	private boolean isMovableObject(float x, float y) {
		
		return Loader.getTypes(x, y).equals("stone") || 
				Loader.getTypes(x, y).equals("ice") ||
				Loader.getTypes(x, y).equals("tnt");
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
