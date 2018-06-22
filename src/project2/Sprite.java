/**
 * Project for SWEN20003: Object Oriented Software Development 2017 
 * Author: William Liandri
 * Student ID: 728710
 * Email: wliandri@student.unimelb.edu.au
 * This code is cited from Project 1 Solutions by Eleanor McMurtry
 */

package project2;

import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;

public abstract class Sprite {
	
	// Used to decide what direction an object is moving
	// Look up enums to find a more elegant solution!
	public static final int DIR_NONE = 0;
	public static final int DIR_LEFT = 1;
	public static final int DIR_RIGHT = 2;
	public static final int DIR_UP = 3;
	public static final int DIR_DOWN = 4;
	
	private int total_moves;
	
	private Image image = null;
	private float x;
	private float y;
	private boolean isRendered;
	private boolean isRestared;
	
	// Used to record the coordinates of each movement.
	private ArrayList<Float> xRecord;
	private ArrayList<Float> yRecord;
	
	public Sprite(String image_src, float x, float y) {
		try {
			image = new Image(image_src);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
		xRecord = new ArrayList<Float>();
		yRecord = new ArrayList<Float>();
		
		this.x = x;
		this.y = y;
		this.isRendered = true;
		this.isRestared = false;
		this.total_moves = -1;
		snapToGrid();
		xRecord.add(x);
		yRecord.add(y);
	}

	public void update(Input input, int delta) {
		
	}
	
	public void undoMoves() {
		
	}
	
	public void render(Graphics g) {
		image.drawCentered(x, y);
	}
	
	// Forces this sprite to align to the grid
	public void snapToGrid() {
		x /= App.TILE_SIZE;
		y /= App.TILE_SIZE;
		x = Math.round(x);
		y = Math.round(y);
		x *= App.TILE_SIZE;
		y *= App.TILE_SIZE;
	}
	
	/**
	 * Update the type's current position. 
	 * @param type
	 * @param x
	 * @param y
	 */
	public void updateTypes(String type, float x, float y) {
		
		Loader.updateTypes(type, this.x, this.y, x, y);
	}
	
	public boolean isRendered() {
		return isRendered;
	}

	public void setRender(boolean isRender) {
		this.isRendered = isRender;
	}
	
	public boolean isRestared() {
		return isRestared;
	}

	public void setRestared(boolean isRestared) {
		this.isRestared = isRestared;
	}
	
	public int getTotal_moves() {
		return total_moves;
	}

	public void setTotal_moves(int total_moves) {
		this.total_moves = total_moves;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
	
	public int getsizexRecord() {
		return xRecord.size();
	}
	
	public float getxRecord(int index) {
		return xRecord.get(index);
	}

	public void setxRecord(float xRecord) {
		this.xRecord.add(xRecord);
	}
	
	public int getsizeyRecord() {
		return yRecord.size();
	}
	
	public float getyRecord(int index) {
		return this.yRecord.get(index);
	}

	public void setyRecord(float yRecord) {
		this.yRecord.add(yRecord);
	}
	
	public void removexRecord() {
		this.xRecord.remove(xRecord.size() - 1);
	}
	
	public void removeyRecord() {
		this.yRecord.remove(yRecord.size() - 1);
	}
	
	public Image getImage() {
		return image;
	}

	public void setImage(String img_src) {
		try {
			this.image = new Image(img_src);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
}
