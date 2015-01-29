/*
 * @file: Bishop.java
 * @author: Keith Monaghan
 * @date: 6-19-14
 * 
 */

package Pieces;

import java.util.ArrayList;

import Grid.Location;

public class Bishop extends chessPiece {
	public Bishop(Boolean team){
		super(team);
	}
	
	public Bishop(chessPiece clone){
		super(clone);
	}

	@Override
	public ArrayList<Location> getValidMoveLocations() {
		return this.getLineTowards(new String[]{"upright", "upleft", "downright", "downleft"});
	}
}
