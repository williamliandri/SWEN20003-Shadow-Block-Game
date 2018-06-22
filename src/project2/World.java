/**
 * Project for SWEN20003: Object Oriented Software Development 2017 
 * Author: William Liandri
 * Student ID: 728710
 * Email: wliandri@student.unimelb.edu.au
 * This code is cited from Project 1 Solutions by Eleanor McMurtry
 */

package project2;

import java.util.ArrayList;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class World {
	
	private static final String LEVELDIR = "res/levels/";
	private static final String LEVELFILE = ".lvl";
	private static final String MOVES = "Moves: ";
	private static final int TOTAL_LVL = 6;
	private static final int WIN_LVL = 5;
	private boolean isRestared = false;
	private ArrayList<Sprite> sprites;
	
	private int level = 0;

	private int total_moves = 0;
	
	public World() throws SlickException {
		levelGame(level);
	}
	
	public void update(Input input, int delta) throws SlickException {
						
		// Exit from the Game.
		if (input.isKeyPressed(Input.KEY_ESCAPE)) {
			System.exit(0);
		}
		
		else if (input.isKeyPressed(Input.KEY_N)) {
			this.total_moves = 0;
			setLevel();
			levelGame(getLevel());
		}
		
		// Restart the game.
		else if (input.isKeyPressed(Input.KEY_R)) {
			this.total_moves = 0;
			levelGame(getLevel());
		}
		
		// Undo the movements 
		else if (input.isKeyPressed(Input.KEY_Z)) {
			
			for (Sprite sprite : sprites) {
				if (sprite != null) {
					sprite.undoMoves();
				}
			}
		}
		
		else {
			
			for (Sprite sprite : sprites) {
				if (sprite != null) {
					sprite.update(input, delta);
				}
				
				// Restart the game because the player hits the enemies.
				if (sprite.isRestared()) {
					this.total_moves = 0;
					this.isRestared = true;
					break;
				}
			}
		}
		
		// Player hits enemies, restart the game.
		if (isRestared) {
			this.isRestared = false;
			levelGame(getLevel());
		}
		
		// The current level is complete, needs to
		// go to the next level.
		// It also needs to show level 5 
		// if the player has completed the game.
		if(isLevelCompleted() && level != WIN_LVL) {
			this.total_moves = 0;
			setLevel();
			levelGame(getLevel());
		}
	}

	public void render(Graphics g) {
	
		for (Sprite sprite : sprites) {
			if (sprite != null && sprite.isRendered()) {
				sprite.render(g);
				
				// Record the total movements that have been made
				// It must be greater than 0 since we only
				// record the total movements in the player class.
				if (sprite.getTotal_moves() >= 0) {
					this.total_moves = sprite.getTotal_moves();
				}
			}
		}
		
		// Show the total movements that have been done. 
		g.drawString(MOVES + total_moves, 10, 10);
		
	}
	
	/**
	 * Start the game on specified level
	 * @param level
	 * @throws SlickException
	 */
	private void levelGame(int level) throws SlickException {
		// Load the 0.lvl and store the Sprite data 
		// in an Array List.
		sprites = Loader.loadSprites(LEVELDIR + level + LEVELFILE);
	}
	
	/**
	 * Check whether the current level has been completed or not.
	 * @return
	 */
	private boolean isLevelCompleted() {
		return Loader.getNumTargets() == Loader.getNumTargetsAchieved();
	}
	
	private int getLevel() {
		return level;
	}

	private void setLevel() {
		this.level = (this.level + 1) % (TOTAL_LVL);
	}
	
}
