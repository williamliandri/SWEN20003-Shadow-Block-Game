/**
 * Project for SWEN20003: Object Oriented Software Development 2017 
 * Author: William Liandri
 * Student ID: 728710
 * Email: wliandri@student.unimelb.edu.au
 * This code is cited from Project 1 Solutions by Eleanor McMurtry
 */

package project2;

import org.newdawn.slick.Input;

public class Target extends Sprite {
	
	private static final String TARGET_SRC = "res/target.png";
	private boolean hasTargetRecorded = false;
	
	public Target(float x, float y) {
		super(TARGET_SRC, x, y);
	}
	
	@Override
	public void update (Input input, int delta) {
		checkTargetAchieved();
	}
	
	@Override
	public void undoMoves() {
		checkTargetAchieved();
	}
	
	private void checkTargetAchieved() {
		
		// The stone or ice reaches the target, record it.
		if ((Loader.getTypes(super.getX(), super.getY()).equals("stone") ||
				Loader.getTypes(super.getX(), super.getY()).equals("ice")) 
				&& !hasTargetRecorded) {
			hasTargetRecorded = true;
			Loader.setNumTargetsAchieved(Loader.getNumTargetsAchieved() + 1);
		}
				
		// The stone or ice is being moved from the target; therefore the target
		// is occupied by the player.
		else if ((Loader.getTypes(super.getX(), super.getY()).equals("player") 
				||Loader.getTypes(super.getX(), super.getY()).equals("target")) 
				&& hasTargetRecorded) {
					
			hasTargetRecorded = false;
			Loader.setNumTargetsAchieved(Loader.getNumTargetsAchieved() - 1);
		}
	}
}
