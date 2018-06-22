/**
 * Project for SWEN20003: Object Oriented Software Development 2017 
 * Author: William Liandri
 * Student ID: 728710
 * Email: wliandri@student.unimelb.edu.au
 * This code is cited from Project 1 Solutions by Eleanor McMurtry
 */

package project2;

import org.newdawn.slick.Input;

public class Player extends Movable {
	
	private static final String PLAYER_SRC = "res/player_left.png";
	
	public Player(float x, float y) {
		super(PLAYER_SRC, x, y);
		setTotal_moves(0);
	}

	@Override
	public void update(Input input, int delta) {
		
		// Determine the movements of the player.
		int dir = DIR_NONE;
				
		if (input.isKeyPressed(Input.KEY_LEFT)) {
			dir = DIR_LEFT;
		}
		
		else if (input.isKeyPressed(Input.KEY_RIGHT)) {
			dir = DIR_RIGHT;
		}
		
		else if (input.isKeyPressed(Input.KEY_UP)) {
			dir = DIR_UP;
		}
				
		else if (input.isKeyPressed(Input.KEY_DOWN)) {
			dir = DIR_DOWN;
		}
						
		// If the player does not move, set the flag to false.
		if (dir == DIR_NONE && Loader.isPlayerMove()) {
			Loader.setPlayerMove(false);
		}
				
		// Get the delta coordinates of x and y.
		float[] deltaDir = super.getDeltaDir(dir);
		float delta_x = deltaDir[0];
		float delta_y = deltaDir[1];
		float block_deltax = deltaDir[0] + deltaDir[0];
		float block_deltay = deltaDir[1] + deltaDir[1];
				
		// The player hits the enemies, restart the game.
		if (isHitEnemies(super.getX() + delta_x, super.getY() + delta_y)) {
			setRestared(true);
		}
	
		// Check if there is movable object (either stone or ice or TNT) and check
		// whether the movable object can moved or not. If it can be moved, then the 
		// player will also move. 
		else if (isMovableObject(super.getX() + delta_x, super.getY() + delta_y) && 
				canMove(Loader.getTypes(super.getX() + delta_x, super.getY() + delta_y),
				super.getX() + block_deltax, super.getY() + block_deltay)
				&& dir != DIR_NONE) {
			
			// Update the player data and its coordinates.
			Loader.setPlayerXCoord(getX());
			Loader.setPlayerYCoord(getY());	
			Loader.setMoveDir(dir);
			Loader.setPlayerMove(true);
					
			super.moveToDest("player", delta_x, delta_y);
			super.setTotal_moves(getTotal_moves() + 1);			
		}
				
		// Make sure the position is not blocked!
		else if (super.canMove(Loader.getTypes(super.getX() + delta_x, super.getY() + delta_y),
				super.getX() + delta_x, super.getY() + delta_y) && dir != DIR_NONE) {
					
			// Update the player data and its coordinates.
			Loader.setPlayerXCoord(getX());
			Loader.setPlayerYCoord(getY());	
			Loader.setPlayerMove(true);
					
			super.moveToDest("player", delta_x, delta_y);
			super.setTotal_moves(getTotal_moves() + 1);
		}
				
		// The position is blocked, so the player cannot move, but we need
		// to keep track of the movements and the total movements that have been done.
		else if (!super.canMove(Loader.getTypes(super.getX() + delta_x, super.getY() + delta_y),
				super.getX() + delta_x, super.getY() + delta_y) && dir != DIR_NONE) {
					
			// Update the player coordinates
			Loader.setPlayerXCoord(getX());
			Loader.setPlayerYCoord(getY());
			Loader.setPlayerMove(true);
					
			super.setxRecord(super.getX());
			super.setyRecord(super.getY());
			super.setTotal_moves(getTotal_moves() + 1);
			
		}
		
		super.updateTypes("player", super.getX(), super.getY());
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
	 * Check if the player hits the enemies.
	 * @param x
	 * @param y
	 * @return
	 */
	private boolean isHitEnemies(float x, float y) {
		
		return Loader.getTypes(x, y).equals("skeleton") ||
				Loader.getTypes(x, y).equals("rogue") || 
				Loader.getTypes(x, y).equals("mage");
	}
}
