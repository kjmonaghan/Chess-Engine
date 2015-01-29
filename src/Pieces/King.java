/*
 * @file: King.java
 * @author: Keith Monaghan
 * @date: 6-19-14
 * 
 */

package Pieces;

import java.util.ArrayList;

import Grid.Grid;
import Grid.Location;

public class King extends chessPiece{

	public King(boolean team) {
		super(team);
	}
	
	public King(chessPiece clone){
		super(clone);
	}

	@Override
	public ArrayList<Location> getValidMoveLocations() {
		ArrayList<Location> result = new ArrayList<Location>();
		String[] directions = {"upleft", "up", "upright", "right", "downright", "down", "downleft", "left"};
		Grid<chessPiece> grid = this.getGrid();
		
		Location temp;
		for(String direction : directions){
			temp = this.getLocation().getAdjacentLocation(direction);
			if(grid.validLocation(temp))
				if(grid.get(temp) == null)
					result.add(temp);
				else if(grid.get(temp) != null && !grid.get(temp).isFriendly(this))
					result.add(temp);
				else
					continue;
			else
				continue;
			result.add(temp);
		}
		
		return result;
	}

}
