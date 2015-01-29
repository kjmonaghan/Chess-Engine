/*
 * @file: Rook.java
 * @author: Keith Monaghan
 * @date: 6-19-14
 * 
 */

package Pieces;

import java.util.ArrayList;

import Grid.Location;

public class Rook extends chessPiece{

	public Rook(boolean team) {
		super(team);
	}
	
	public Rook(chessPiece clone){
		super(clone);
	}
	@Override
	public ArrayList<Location> getValidMoveLocations() {
		return this.getLineTowards(new String[]{"up", "down", "left", "right"});
	}
	
}
