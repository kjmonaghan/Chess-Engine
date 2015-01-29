/*
 * @file: Queen.java
 * @author: Keith Monaghan
 * @date: 6-19-14
 * 
 */

package Pieces;

import java.util.ArrayList;

import Grid.Location;

public class Queen extends chessPiece{

	public Queen(boolean team) {
		super(team);
	}
	
	public Queen(chessPiece clone){
		super(clone);
	}
	@Override
	public ArrayList<Location> getValidMoveLocations() {
		return this.getLineTowards(new String[]{"upleft", "up", "upright", "right", "downright", "down", "downleft", "left"});
	}

}
