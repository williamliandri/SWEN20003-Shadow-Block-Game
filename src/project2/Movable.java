package project2;

public abstract class Movable extends Sprite {
	
	public Movable(String image_src, float x, float y) {
		super(image_src, x, y);
	}
	
	/**
	 * Undo the movements.
	 */
	@Override
	public void undoMoves() {

		String type = Loader.getTypes(getX(), getY());
		if (super.getsizexRecord() > 1 && super.getsizeyRecord() > 1) {
			
			// Update the coordinates with the previous recorded coordinates
			// and remove the last coordinates. 
			Loader.updateTypes(type, super.getX(), super.getY(), 
					super.getxRecord(super.getsizexRecord() - 2), 
					super.getyRecord(super.getsizeyRecord() - 2));
			
			
			super.removexRecord();
			super.removeyRecord();
			
			super.setX(getxRecord(super.getsizexRecord() - 1));
			super.setY(getyRecord(super.getsizeyRecord() - 1));
			super.snapToGrid();
			super.setTotal_moves(getTotal_moves() - 1);
		}
	}
	
	/**
	 * Get the direction of the movement.
	 * @param dir
	 * @return
	 */
	public float[] getDeltaDir(int dir) {
			
		float speed = App.TILE_SIZE;
		float[] delta = new float[2];
			
		// Translate the direction to an x and y displacement
		float delta_x = 0,
				delta_y = 0;
		switch (dir) {
			case DIR_LEFT:
				delta_x = -speed;
				break;
			case DIR_RIGHT:
				delta_x = speed;
				break;
			case DIR_UP:
				delta_y = -speed;
				break;
			case DIR_DOWN:
				delta_y = speed;
				break;
		}
			
		delta[0] = delta_x;			
		delta[1] = delta_y;
			
		return delta;	
	}
	
	/**
	 * Move the object to the new coordinates.
	 * @param type
	 * @param speed
	 */
	public void moveToDest(String type, int speed) {
		
		float delta_x = 0;
		float delta_y = 0;
		
		switch (type) {
			
			case "skeleton": 
				delta_y += speed;
				break;
			case "rogue":
				delta_x += speed;
				break;
		}
		
		super.updateTypes(type, super.getX() + delta_x, super.getY() + delta_y);
		super.setX(super.getX() + delta_x);
		super.setY(super.getY() + delta_y);
	}
	
	/**
	 * Move the object to the new coordinates.
	 * @param type
	 * @param delta_x
	 * @param delta_y
	 */
	public void moveToDest(String type, float delta_x, float delta_y) {
		
		super.updateTypes(type, super.getX() + delta_x, super.getY() + delta_y);
		super.setX(super.getX() + delta_x);
		super.setY(super.getY() + delta_y);
		
		if (type.equals("ice")) {
			super.setxRecord(super.getX() - delta_x);
			super.setyRecord(super.getY() - delta_y);
		}
		
		else {
			super.setxRecord(super.getX());
			super.setyRecord(super.getY());
		}

	}
	
	/**
	 * Check whether the movement is possible.
	 * @param type
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean canMove(String type, float x, float y) {
		
		if (type.equals("tnt")) {
			return Loader.isBlocked("floor", x, y) ||
				Loader.isBlocked("target", x, y) ||
				Loader.isBlocked("switch", x, y) ||
				Loader.isBlocked("mage", x, y) ||
				Loader.isBlocked("rogue", x, y) ||
				Loader.isBlocked("skeleton", x, y) ||
				Loader.isBlocked("cracked", x, y);
		}
		
		return Loader.isBlocked("floor", x, y) ||
				Loader.isBlocked("target", x, y) ||
				Loader.isBlocked("switch", x, y) ||
				Loader.isBlocked("mage", x, y) ||
				Loader.isBlocked("rogue", x, y) ||
				Loader.isBlocked("skeleton", x, y);
	}
}
