/*
 * @file: Knight.java
 * @author: Keith Monaghan
 * @date: 6-19-14
 * 
 */

package Pieces;

import java.util.ArrayList;

import Grid.Grid;
import Grid.Location;

public class Knight extends chessPiece{

	public Knight(boolean team) {
		super(team);
	}
	
	public Knight(chessPiece clone){
		super(clone);
	}

	@Override
	public ArrayList<Location> getValidMoveLocations() {
		Grid<chessPiece> gr = this.getGrid();
		ArrayList<Location> temp = new ArrayList<Location>();
		temp.add(new Location (getLocation().getRow() + 1, getLocation().getCol() + 2));
		temp.add(new Location (getLocation().getRow() + 1, getLocation().getCol() - 2));
		temp.add(new Location (getLocation().getRow() - 1, getLocation().getCol() + 2));
		temp.add(new Location (getLocation().getRow() - 1, getLocation().getCol() - 2));
		temp.add(new Location (getLocation().getRow() + 2, getLocation().getCol() + 1));
		temp.add(new Location (getLocation().getRow() + 2, getLocation().getCol() - 1));
		temp.add(new Location (getLocation().getRow() - 2, getLocation().getCol() + 1));
		temp.add(new Location (getLocation().getRow() - 2, getLocation().getCol() - 1));
		
		ArrayList<Location> result = new ArrayList<Location>();
		for(Location element : temp){
			if(!gr.validLocation(element))
				continue;	
			else if(gr.get(element) != null && this.isFriendly(gr.get(element)))
				continue;
			
			result.add(element);
		}
		return result;
	}
}
