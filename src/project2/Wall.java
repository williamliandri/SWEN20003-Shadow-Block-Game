/**
 * Project for SWEN20003: Object Oriented Software Development 2017 
 * Author: William Liandri
 * Student ID: 728710
 * Email: wliandri@student.unimelb.edu.au
 * This code is cited from Project 1 Solutions by Eleanor McMurtry
 */

package project2;

public class Wall extends Sprite {
	
	private static final String WALL_SRC = "res/wall.png";
	
	public Wall(float x, float y) {
		super(WALL_SRC, x, y);
	}
}
