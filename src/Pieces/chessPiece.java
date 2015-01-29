package Pieces;
import java.util.ArrayList;

import Grid.*;


public abstract class chessPiece{
	
	//Instance Variables
	private boolean isAlive;
	private Location loc;
	private boolean bottomTeam;
	private Grid<chessPiece> grid;
	
	
	//Standard constructor
	public chessPiece(boolean team){
		isAlive = false;
		bottomTeam = team;
	}
	
	//Copy constructor
	public chessPiece(chessPiece clone){
		isAlive = clone.alive();
		loc = new Location(clone.getLocation());
		bottomTeam = clone.getTeam();
	}
	
	//Get Methods
	public boolean alive(){
		return isAlive;
	}
	
	public boolean getTeam(){
		return bottomTeam;
	}
	
	public Location getLocation(){
		return loc;
	}
	
	public Grid<chessPiece> getGrid(){
		return grid;
	}
	
	//Returns the simple name of the class
	//in this case, toString() would return "chessPiece"
	//for the subclass Pawn, toString() would return "Pawn"
	public String toString(){
		return this.getClass().getSimpleName();
	}
	
	//Returns an ArrayList with all valid Locations in a given direction
	//Used to find valid move locations for pieces like queen, bishop, etc
	public ArrayList<Location> getLineTowards(String[] directions){
		ArrayList<Location> temp = new ArrayList<Location>();
		Location next = this.getLocation();
		
		for(String element : directions){
			do{
				next = next.getAdjacentLocation(element);
				if(grid.validLocation(next))
					if(grid.get(next) == null)
						temp.add(next);
					else if(grid.get(next) != null && !grid.get(next).isFriendly(this))
						temp.add(next);
					else
						break;
				else
					break;
				temp.add(next);
			}while(grid.get(next) == null);
			next = this.getLocation();
		}
		return temp;
	}
	
	//Returns the path of the piece's corresponding icon file
	public String getIconLocation(){
		String result = "sprites/";
		
		//Get team code
		if(bottomTeam)
			result += "0_";
		else
			result += "1_";
		
		//Get piece name
		result += this.getClass().getSimpleName().toLowerCase();
		
		//add file type
		result += ".png";
		
		return result;
	}
	
	
	public void putSelfInGrid(Grid<chessPiece> gr, Location newLoc){
		//Clear Location
		chessPiece temp = gr.get(newLoc);
		if(temp != null)
			temp.remove();
		
		//Add to grid
		gr.add(newLoc, this);
		grid = gr;
		loc = newLoc;
	}
	
	//Postcondition #1: The grid will no longer contain this chessPiece
	//Postcondition #2: This piece will no longer be "alive" and will no longer have a location
	public void remove(){
		//Exceptions
		if(grid == null)
			throw new IllegalArgumentException("This piece is not in a grid");
		if(grid.get(loc) != this)
			throw new IllegalArgumentException("The grid contains another piece at location " + loc.toString());
		
		//Remove
		grid.remove(loc);
		isAlive = false;
		loc = null;
	}
	
	//
	public void moveTo(Location newLoc){
		//Exceptions
		if(grid == null)
			throw new IllegalArgumentException("Piece is not in a grid");
		if(!isValidMoveLocation(newLoc)){
			System.out.println(this.getLocation());
			System.out.println(this.getClass().getName());
			System.out.println(newLoc);
			throw new IllegalArgumentException("not a valid move location");
		}
		if(this.loc.equals(newLoc))
			return;
		
		//Remove from original position
		grid.remove(loc);
		
		//Clear new position
		chessPiece old = grid.get(newLoc);
		if(old != null)
			old.remove();
		
		//Place in new position
		loc = newLoc;
		grid.add(newLoc, this);	
	}
	
	public void quietMove(Location newLoc){
		if(grid == null)
			throw new IllegalArgumentException("Piece is not in a grid");
		
	}
	public void  undoMove(Location oldLoc){
		grid.remove(loc);
		loc = oldLoc;
		grid.add(oldLoc, this);
	}
	public void changeTeam(){
		bottomTeam = !bottomTeam;
	}
	//Check Methods
	public boolean isValidMoveLocation(Location loc){
		boolean result = false;
		for(Location element : this.getValidMoveLocations())
			if(element.equals(loc)){
				result = true;
				return result;
			}
		return result;
	}
	
	public boolean isFriendly(chessPiece other){
		return this.bottomTeam == other.bottomTeam;
	}
	
	//Abstract Methods
	public abstract ArrayList<Location> getValidMoveLocations();
}
