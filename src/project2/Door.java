/**
 * Project for SWEN20003: Object Oriented Software Development 2017 
 * Author: William Liandri
 * Student ID: 728710
 * Email: wliandri@student.unimelb.edu.au
 */

package project2;

import org.newdawn.slick.Input;

public class Door extends Sprite {
	
	private static final String DOOR_SRC = "res/door.png";
	
	public Door(float x, float y) {
		super(DOOR_SRC, x, y);
	}
	
	@Override
	public void update(Input input, int delta) {
		
		// The switch is on, so unlock the door.
		if(isSwitchOn() && super.isRendered()) {
			super.setRender(false);
			super.updateTypes("floor", super.getX(), super.getY());
		}
		
		// The switch is off, lock the door.
		else if (!isSwitchOn() && !super.isRendered()) {
			super.setRender(true);
			super.updateTypes("door", super.getX(), super.getY());
		}
		
	}
	
	/**
	 * Check whether the switch is on or not.
	 * @return
	 */
	private boolean isSwitchOn() {
		
		float[] switchCoord = Loader.getSwitchCoord();
		
		if (Loader.getTypes(switchCoord[0], switchCoord[1]).equals("stone") || 
				Loader.getTypes(switchCoord[0], switchCoord[1]).equals("ice")) {
			return true;
		}
		
		return false;
	}
	
}
