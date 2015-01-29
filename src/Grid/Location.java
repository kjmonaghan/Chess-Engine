/*
 * @file: Location.java
 * @author: Keith Monaghan
 * @date: 6-17-14
 * 
 * Purpose:  Simple class that creates a coordinate system for chessPieces and Grids
 * 
 * Inspired by GridWorld
 */
package Grid;

public class Location implements Comparable{
	private int row, col;
	
	//Constructor
	public Location(int x, int y){
		row = x;
		col = y;
	}
	
	//Copy constructor
	public Location(Location loc){
		row = loc.getRow();
		col = loc.getCol();
	}
	
	//Get + set methods
	
	public int getRow(){
		return row;
	}
	
	public int getCol(){
		return col;
	}
	
	//Returns the location in format "(row,col)"
	public String toString(){
		return "(" + getRow() + "," + getCol() + ")";
	}
	
	//Returns Location that is adjacent to this location in a given direction
	public Location getAdjacentLocation(String direction){
		int rowMod, colMod;
		
		switch (direction) {
			default:
				colMod = 0;
				rowMod = 0;
				break;
			case "upleft":
				colMod = -1;
				rowMod = -1;
				break;
			case "up":
				colMod = 0;
				rowMod = -1;
				break;
			case "upright":
				colMod = 1;
				rowMod = -1;
				break;
			case "right":
				colMod = 1;
				rowMod = 0;
				break;
			case "downright":
				colMod = 1;
				rowMod = 1;
				break;
			case "down":
				colMod = 0;
				rowMod = 1;
				break;
			case "downleft":
				colMod = -1;
				rowMod = 1;
				break;
			case "left":
				colMod = -1;
				rowMod = 0;
				break;
		}
		
		Location newLoc = new Location(getRow() + rowMod, getCol() + colMod);
		return newLoc;
	}
	
	//Ranks locations by row and then column, with (0,0) being the lowest value
    public int compareTo(Object other){
        Location otherLoc = (Location) other;
        if (getRow() < otherLoc.getRow())
            return -1;
        if (getRow() > otherLoc.getRow())
            return 1;
        if (getCol() < otherLoc.getCol())
            return -1;
        if (getCol() > otherLoc.getCol())
            return 1;
        return 0;
    }
    
    //Returns true if row and col are equal
    public boolean equals(Object other){
    	return this.compareTo(other) == 0;
    }
}
