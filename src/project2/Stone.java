/**
 * Project for SWEN20003: Object Oriented Software Development 2017 
 * Author: William Liandri
 * Student ID: 728710
 * Email: wliandri@student.unimelb.edu.au
 * This code is cited from Project 1 Solutions by Eleanor McMurtry
 */

package project2;

import org.newdawn.slick.Input;

public class Stone extends Pushable {
	
	private static final String STONE_SRC = "res/stone.png";
	
	public Stone(float x, float y) {
		super(STONE_SRC, x, y);
	}
	
	@Override
	public void update(Input input, int delta) {	
		
		// The stone will move if there is either player or
		// rogue who pushes the stone.
		if (isPlayerRogue(super.getX(), super.getY())) {
			super.objectMove("stone");
		}
		
		// Otherwise, record the coordinates 
		// everytime the player moves.
		else if (Loader.isPlayerMove()) {
			super.setxRecord(super.getX());
			super.setyRecord(super.getY());
		}
		
		// Always updates the current position.
		super.updateTypes("stone", super.getX(), super.getY());
	}
}
