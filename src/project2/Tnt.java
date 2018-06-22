/**
 * Project for SWEN20003: Object Oriented Software Development 2017 
 * Author: William Liandri
 * Student ID: 728710
 * Email: wliandri@student.unimelb.edu.au
 */

package project2;

import org.newdawn.slick.Input;

public class Tnt extends Pushable {
	
	private static final String TNT_SRC = "res/tnt.png";
	private boolean hasExploded;
	
	public Tnt(float x, float y) {
		super(TNT_SRC, x, y);
		hasExploded = false;
	}
	
	@Override
	public void update(Input input, int delta) {
		
		if (!hasExploded) {
			
			// The player pushes the TNT.
			if (isPlayerRogue(super.getX(), super.getY())) {
				super.objectMove("tnt");
			}
			
			// Otherwise, record the coordinates 
			// everytime the player moves.
			else if (Loader.isPlayerMove()) {
				super.setxRecord(super.getX());
				super.setyRecord(super.getY());
			}
			
			// The TNT has been exploded, need to set the flag.
			if (!super.isRendered()) {
				hasExploded = true;
			}
			
			// Otherwise, it always updates the current position.
			else {
				super.updateTypes("tnt", super.getX(), super.getY());
			}
		}
	}
	
	@Override
	public void undoMoves() {
		
		if (!hasExploded) {
			super.undoMoves();
		}
	}
}

