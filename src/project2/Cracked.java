/**
 * Project for SWEN20003: Object Oriented Software Development 2017 
 * Author: William Liandri
 * Student ID: 728710
 * Email: wliandri@student.unimelb.edu.au
 */

package project2;

import org.newdawn.slick.Input;

public class Cracked extends Sprite {
	
	private final static String CRACK_SRC = "res/cracked_wall.png";
	private final static String EXPLOSION_SRC = "res/explosion.png";
	
	// The time limit to render the explosion
	private final static int LIMIT = 400;
	private int time;
	private boolean isDestroyed = false;
	
	public Cracked(float x, float y) {
		super(CRACK_SRC, x, y);
	}
	
	@Override
	public void update(Input input, int delta) {

		// The cracked wall is destroyed by the TNT, show the explosion.
		if(Loader.getTypes(super.getX(), super.getY()).equals("tnt")) {
			Loader.updateTypes("floor", super.getX(), super.getY(), 
					super.getX(), super.getY() );
			super.setImage(EXPLOSION_SRC);
			time += delta;
			isDestroyed = true;
		}
		
		// Calculate the time until 0.4 seconds or 400 miliseconds
		// and then unrender the explosion.
		else if (isDestroyed) {
			time += delta;
			
			if (time > LIMIT) {
				time -= LIMIT;
				super.setRender(false);
			}
		}
	}
}
