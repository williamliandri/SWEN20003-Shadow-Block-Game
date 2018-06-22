/**
 * Project for SWEN20003: Object Oriented Software Development 2017 
 * Author: William Liandri
 * Student ID: 728710
 * Email: wliandri@student.unimelb.edu.au
 * This code is cited from Project 1 Solutions by Eleanor McMurtry
 */

package project2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Loader {
	
	// Used to record the current position of each sprite in the game.
	private static String[][] types; 
	
	// Used to record the position of wall, floor, target, switch only.
	private static String[][] unmovedTypes; 

	private static int world_width;
	private static int world_height;
	private static int numTargets;
	private static int numTargetsAchieved;
	private static int offset_x;
	private static int offset_y;
	
	private static float[] switchCoord;
	private static float playerXCoord;
	private static float playerYCoord;
	private static int moveDir;
	private static boolean playerMove;

	/**
	 * Create the appropriate sprite given a name and location.
	 * @param name	the name of the sprite
	 * @param x		the x position
	 * @param y		the y position
	 * @return		the sprite object
	 */
	private static Sprite createSprite(String name, float x, float y) {
		switch (name) {
			case "wall":
				return new Wall(x, y);
			case "floor":
				return new Floor(x, y);
			case "stone":
				return new Stone(x, y);
			case "target":
				return new Target(x, y);
			case "player":
				Loader.playerXCoord = x;
				Loader.playerYCoord = y;
				return new Player(x, y);
			case "skeleton":
				return new Skull(x, y);
			case "rogue":
				return new Rogue(x, y);
			case "mage":
				return new Mage(x, y);
			case "tnt":
				return new Tnt(x, y);
			case "cracked":
				return new Cracked(x, y);
			case "switch":
				return new Switch(x, y);
			case "door":
				return new Door(x, y);
			case "ice":
				return new Ice(x, y);
		}
		return null;
	}
	
	// Converts a world coordinate to a tile coordinate,
	// and returns if that location is a blocked tile
	public static boolean isBlocked(String target, float x, float y) {
		
		x -= offset_x;
		x /= App.TILE_SIZE;
		y -= offset_y;
		y /= App.TILE_SIZE;
		
		// Do bounds checking!
		if (x >= 0 && x < world_width && y >= 0 && y < world_height) {
			return types[(int)x][(int)y].equals(target);
		}
		// Default to blocked
		return true;
	}

	/**
	 * Loads the sprites from a given file.
	 * @param filename
	 * @return
	 */
	public static ArrayList<Sprite> loadSprites(String filename) {
		
		// Initialised the arrays.
		ArrayList<Sprite> list = new ArrayList<>();
		
		// Set the initial values.
		numTargets = 0;
		numTargetsAchieved = 0;
		moveDir = 0;
		playerMove = false;
		
		// Open the file
		try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
			String line;
			
			// Find the world size
			line = reader.readLine();
			String[] parts = line.split(",");
			world_width = Integer.parseInt(parts[0]);
			world_height = Integer.parseInt(parts[1]);
			
			// Create the array of object types
			types = new String[world_width][world_height];
			unmovedTypes = new String[world_width][world_height];
			
			// Calculate the top left of the tiles so that the level is
			// centred
			offset_x = (App.SCREEN_WIDTH - world_width * App.TILE_SIZE) / 2;
			offset_y = (App.SCREEN_HEIGHT - world_height * App.TILE_SIZE) / 2;

			// Loop over every line of the file
			while ((line = reader.readLine()) != null) {
				String name;
				float x, y;
				
				// Split the line into parts
				parts = line.split(",");
				name = parts[0];
				x = Integer.parseInt(parts[1]);
				y = Integer.parseInt(parts[2]);
				
				// Record the coordinates of the targets.
				if (name.equals("target")) {
					numTargets += 1;
				}
							
				// Record unmovable sprites in an array
				if (name.equals("wall") || name.equals("floor") 
						|| name.equals("target") ||
						name.equals("switch")) {
					
					unmovedTypes[(int)x][(int)y] = name;
				}
				
				types[(int)x][(int)y] = name;
				
				// Adjust for the grid
				x = offset_x + x * App.TILE_SIZE;
				y = offset_y + y * App.TILE_SIZE;
				
				// Record the coordinates of the switch
				if (name.equals("switch")) {
					switchCoord = new float[2];
					switchCoord[0] = x;
					switchCoord[1] = y;
				}
				
				// Create the sprite
				list.add(createSprite(name, x, y));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return list;
	}

	/**
	 * Get the types of the specified coordinates.
	 * @param x
	 * @param y
	 * @return
	 */
	public static String getTypes(float x, float y) {
		
		x -= offset_x;
		x /= App.TILE_SIZE;
		y -= offset_y;
		y /= App.TILE_SIZE;
		
		return types[(int)x][(int)y];
	}
	
	/**
	 * Update the position of sprite everytime it moves.
	 * @param type
	 * @param xOld
	 * @param yOld
	 * @param x
	 * @param y
	 */
	public static void updateTypes(String type, float xOld, float yOld, float x, float y) {
		x -= offset_x;
		x /= App.TILE_SIZE;
		y -= offset_y;
		y /= App.TILE_SIZE;
		
		xOld -= offset_x;
		xOld /= App.TILE_SIZE;
		yOld -= offset_y;
		yOld /= App.TILE_SIZE;
		
		types[(int)xOld][(int)yOld] = unmovedTypes[(int)xOld][(int)yOld];
		types[(int)x][(int)y] = type;
	}
	
	public static int getMoveDir() {
		return moveDir;
	}

	public static void setMoveDir(int moveDir) {
		Loader.moveDir = moveDir;
	}
	
	public static boolean isPlayerMove() {
		return playerMove;
	}

	public static void setPlayerMove(boolean playerMove) {
		Loader.playerMove = playerMove;
	}
	
	public static float getPlayerXCoord() {
		return playerXCoord;
	}

	public static void setPlayerXCoord(float playerXCoord) {
		Loader.playerXCoord = playerXCoord;
	}

	public static float getPlayerYCoord() {
		return playerYCoord;
	}

	public static void setPlayerYCoord(float playerYCoord) {
		Loader.playerYCoord = playerYCoord;
	}
	
	public static float[] getSwitchCoord() {
		return switchCoord;
	}

	public static void setSwitchCoord(float[] switchCoord) {
		Loader.switchCoord = switchCoord;
	}
	
	public static int getNumTargets() {
		return numTargets;
	}

	public static void setNumTargets(int numTargets) {
		Loader.numTargets = numTargets;
	}

	public static int getNumTargetsAchieved() {
		return numTargetsAchieved;
	}

	public static void setNumTargetsAchieved(int numTargetsAchieved) {
		Loader.numTargetsAchieved = numTargetsAchieved;
	}
}
