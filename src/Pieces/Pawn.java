/*
 * @file: Pawn.java
 * @author: Keith Monaghan
 * @date: 6-17-14
 * 
 */

package Pieces;

import java.util.ArrayList;

import Grid.*;

public class Pawn extends chessPiece {
	private boolean firstMove;
	
	public Pawn(boolean team) {
		super(team);
		firstMove = true;
	}
	
	public Pawn(chessPiece clone){
		super(clone);
		if(clone instanceof Pawn)
			firstMove = ((Pawn) clone).isFirstMove();
	}
	
	public ArrayList<Location> getValidMoveLocations() {
		
		ArrayList<Location> temp = new ArrayList<Location>();
		Grid<chessPiece> gr = this.getGrid();

		if(getTeam() == true){
			if(gr.validLocation(getLocation().getAdjacentLocation("up")) && gr.get(getLocation().getAdjacentLocation("up")) == null){
				temp.add(getLocation().getAdjacentLocation("up"));
				if(gr.validLocation(temp.get(0).getAdjacentLocation("up")) && firstMove && gr.get(temp.get(0).getAdjacentLocation("up")) == null)
					temp.add(temp.get(0).getAdjacentLocation("up"));
			}
			
			if(gr.validLocation(getLocation().getAdjacentLocation("upleft")) && gr.get(getLocation().getAdjacentLocation("upleft")) != null && !isFriendly(gr.get(getLocation().getAdjacentLocation("upleft")))){
				temp.add(getLocation().getAdjacentLocation("upleft"));
			}
			
			if(gr.validLocation(getLocation().getAdjacentLocation("upright")) && gr.get(getLocation().getAdjacentLocation("upright")) != null && !isFriendly(gr.get(getLocation().getAdjacentLocation("upright")))){
				temp.add(getLocation().getAdjacentLocation("upright"));
			}
		}
		else{
			if(gr.validLocation(getLocation().getAdjacentLocation("down")) && gr.get(getLocation().getAdjacentLocation("down")) == null){
				temp.add(getLocation().getAdjacentLocation("down"));
				if(firstMove && gr.get(temp.get(0).getAdjacentLocation("down")) == null)
					temp.add(temp.get(0).getAdjacentLocation("down"));
			}
			
			if(gr.validLocation(getLocation().getAdjacentLocation("downleft")) && gr.get(getLocation().getAdjacentLocation("downleft")) != null && !isFriendly(gr.get(getLocation().getAdjacentLocation("downleft")))){
				temp.add(getLocation().getAdjacentLocation("downleft"));
			}
			if(gr.validLocation(getLocation().getAdjacentLocation("downright")) && gr.get(getLocation().getAdjacentLocation("downright")) != null && !isFriendly(gr.get(getLocation().getAdjacentLocation("downright")))){
				temp.add(getLocation().getAdjacentLocation("downright"));
			}
		}
		
		for(Location loc : temp){
			if(!getGrid().validLocation(loc))
				temp.remove(loc);
		}	
		return temp;
		
	}
	
	public void setFirstMove(){
		firstMove = true;
	}
	
	@Override
	public void putSelfInGrid(Grid<chessPiece> gr, Location newLoc){
		super.putSelfInGrid(gr, newLoc);
		firstMove = true;
	}
	public void moveTo(Location loc){
		super.moveTo(loc);
		firstMove = false;
	}
	
	public boolean touchDown(){
		if(this.getTeam())
			return (this.getLocation().getRow() == 0);
		else
			return (this.getLocation().getRow() == 8);
	}
	
	public boolean isFirstMove(){
		return firstMove;
	}
}
