package project2;

public abstract class Pushable extends Movable {

	public Pushable(String image_src, float x, float y) {
		super(image_src, x, y);
	}
	
	/** 
	 * Move the object.
	 * @param type
	 */
	public void objectMove(String type) {
		
		// Get the direction of the movements.
		float[] deltaDir = super.getDeltaDir(Loader.getMoveDir());
		float delta_x = deltaDir[0];
		float delta_y = deltaDir[1];
		
		// If the type is TNT, check whether it destroys the cracked wall or not.
		if (type.equals("tnt") && Loader.getTypes(super.getX() + delta_x, 
				super.getY() + delta_y).equals("cracked")) {
			super.setRender(false);
		}
		
		// Move the object.
		super.moveToDest(type, delta_x, delta_y);
	}
	
	/** 
	 * Check whether the object is pushed by the player or the rogue.
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean isPlayerRogue(float x, float y) {
		
		return Loader.getTypes(x, y).equals("player") ||
				Loader.getTypes(x, y).equals("rogue");
	}

}
