/*
 * @file: Board.java
 * @author: Keith Monaghan
 * @date: 6-18-14
 * 
 * Purpose:  Handles the bulk of the chess game and connects it to the GUI
 */

/*
 * ~~~~~~~~TO DO~~~~~~~~~~~~~
 * Check for check
 * Check for check-mate
 * Implement castling
 * Implement pawn replacement
 */

package Game;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Stack;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import Grid.Grid;
import Grid.Location;
import Pieces.Bishop;
import Pieces.King;
import Pieces.Knight;
import Pieces.Pawn;
import Pieces.Queen;
import Pieces.Rook;
import Pieces.chessPiece;


public class Board {
	private HashMap<JLabel, chessPiece> map;
	private Grid<chessPiece> gr;
	private ArrayList<chessPiece> graveyard;
	private Stack<Grid<chessPiece>> oldMoves;
	private boolean bottomTurn;
	
	//Constructor
	public Board(){
		map = new HashMap<JLabel, chessPiece>();
		gr = new Grid<chessPiece>(8,8);
		graveyard = new ArrayList<chessPiece>();
		oldMoves = new Stack<Grid<chessPiece>>();
		bottomTurn = true;
	}
	
	//~~~~~~~~~Get methods~~~~~~~~~~~~//
	
	//Returns the hashmap that links the icons to chessPieces
	public HashMap<JLabel, chessPiece> getMap(){
		return map;
	}
	
	//Returns the grid
	public Grid<chessPiece> getGrid(){
		return gr;
	}
	
	//Returns an ArrayList with all dead pieces for a given team
	public ArrayList<chessPiece> getGraveyard(boolean team){
		ArrayList<chessPiece> temp = new ArrayList<chessPiece>();
		for(chessPiece piece : graveyard)
			if(piece.getTeam() == team)
				temp.add(piece);
		return temp;
	}
	
	public ArrayList<Integer> getValidMoveLocations(JLabel grabbedLabel){
			chessPiece grabbedPiece = map.get(grabbedLabel);
			ArrayList<Integer> result = new ArrayList<>();
			
	/*		if(grabbedPiece.getTeam() == bottomTurn && checkForCheck(bottomTurn)){
				chessPiece temp;
				Location oldLoc = grabbedPiece.getLocation();
				for(Location loc : grabbedPiece.getValidMoveLocations()){
					temp = gr.get(loc);
					grabbedPiece.moveTo(loc);
					
					if(!checkForCheck(bottomTurn))
						result.add(toIndex(loc));
					
					//Undo moves
					grabbedPiece.undoMove(oldLoc);
					if(temp != null)
						temp.putSelfInGrid(gr, loc);
				}
			}*/
			if(grabbedPiece.getTeam() == bottomTurn){
				for(Location element : grabbedPiece.getValidMoveLocations()){
						result.add(toIndex(element));
				}
			}		
			return result;
		}
	
	//~~~~~~~~Helper methods~~~~~~~~//
	
	//Converts 1-64 indexes from the grid into Locations
	private Location toLocation(int index){
		int rows = (index/8);
		int cols = (index%8);
		
		return new Location(rows, cols);
	}
	
	//Converts Locations into numerical indexes (1-64) relative to the board
	private int toIndex(Location loc){
		return (loc.getRow() * 8 + loc.getCol());
	}
	
	//Reassigns pieces to icons in the hashmap
	private void updateMap(){
		for(chessPiece piece : map.values()){
			if(piece != null)
				piece = gr.get(piece.getLocation());
		}
	}
	
	//~~~~~~~~Board Controls~~~~~~~///
	
	public void newGame(){
		if(oldMoves.isEmpty())
			buildBoard();
		else{
			gr=oldMoves.get(0);
			oldMoves.clear();
			oldMoves.push(gr);
			graveyard.clear();
			bottomTurn=true;
		}
	}

	//Clears the hashmap
	//Creates all chessPieces and puts them in the default location
	//Generates icons for each piece and places them in the hashmap
	public void buildBoard(){
		map.clear();
		for(int i = 0; i<64; i++){
			boolean team;
			if(i < 16)
				team = false;
			else if(i > 47)
				team = true;
			else
				continue;
			chessPiece piece = null;
			switch(i){
				case 0:
				case 7:
				case 56:
				case 63:
					piece = new Rook(team);
					break;
				case 1:
				case 6:
				case 57:
				case 62:
					piece = new Knight(team);
					break;
				case 2:
				case 5:
				case 58:
				case 61:
					piece = new Bishop(team);
					break;
				case 3:
				case 59:
					piece = new Queen(team);
					break;
				case 4:
				case 60:
					piece = new King(team);
					break;
				case 8:
				case 9:
				case 10:
				case 11:
				case 12:
				case 13:
				case 14:
				case 15:
				case 48:
				case 49:
				case 50:
				case 51:
				case 52:
				case 53:
				case 54:
				case 55:
					piece = new Pawn(team);
					break;
			}
			if(piece != null){
				piece.putSelfInGrid(gr,new Location((i/8),i%8));
				map.put(new JLabel(new ImageIcon(piece.getIconLocation())), piece);
			}
		}
		saveGrid();
	}

	public void saveGrid(){
		Grid<chessPiece> lastMove = new Grid<chessPiece>(gr.getRows(), gr.getCols());
		for(Iterator<chessPiece> i = gr.iterator(); i.hasNext();){
			chessPiece clone = i.next();
			if(clone != null){
				chessPiece nextPiece;		
				switch(clone.toString()){
					default : 
						nextPiece = null;
						break;
					case "Bishop" :
						nextPiece = new Bishop(clone);
						break;
					case "King" :
						nextPiece = new King(clone);
						break;
					case "Knight" : 
						nextPiece = new Knight(clone);
						break;
					case "Pawn" : 
						nextPiece = new Pawn(clone);
						break;
					case "Queen" : 
						nextPiece = new Queen(clone);
						break;
					case "Rook" :
						nextPiece = new Rook(clone);
						break;
				}
				if(nextPiece != null){
					nextPiece.putSelfInGrid(lastMove, new Location(nextPiece.getLocation()));
				}				
			}
		}
		oldMoves.push(lastMove);
	}

	public void move(JLabel a, int index){
		//save configuration
		if(oldMoves.size() != 0)
			this.saveGrid();
	
		//move the piece
		chessPiece temp = map.get(a);
		if(temp != null){
			temp.moveTo(toLocation(index));
			if((temp instanceof Pawn) && ((Pawn) temp).touchDown()){
				
			}
		//change the turn	
		bottomTurn = !bottomTurn;
		}
	}

	/*	public boolean causesCheck(chessPiece piece, Location newLoc){
			if(piece == null || newLoc == null){
				throw new IllegalArgumentException("Piece or Location is null");
			}
			saveGrid();
			piece.moveTo(newLoc);
			boolean result = this.checkForCheck(piece.getTeam());
			undoMove();
			return result;
		}*/
		
	//	public boolean checkForCheck(boolean team){
	//		chessPiece piece;
	//		for(Iterator<chessPiece> i = gr.iterator(); i.hasNext();){
	//			piece = i.next();
	//			if(piece != null && piece.getTeam() != team){
	//				ArrayList<Location> locs = piece.getValidMoveLocations();
	//				for(Location loc : locs){
	//					if (gr.get(loc) instanceof King && (gr.get(loc).getTeam()==team))
	//					{
	//						return true;
	//					}
	//				}
	//			}
	//		}
	//		return false;
	//	}
		
		public void undoMove(){
			if(oldMoves.isEmpty()){
				throw new IllegalArgumentException("The game hasn't started");
			}
			if(oldMoves.size()==1){
				gr=oldMoves.peek();
				bottomTurn = true;
			}
			else{
				gr=oldMoves.pop();
				bottomTurn = !bottomTurn;
			}
			
			updateMap();
		}

	//Check Methods
	public boolean captured(JLabel a, JLabel b){
		if(!(map.containsKey(a) && map.containsKey(b)))
			throw new IllegalArgumentException("One or more of the pieces is not a member of the board");
		
		chessPiece temp1 = map.get(a);
		chessPiece temp2 = map.get(b);
		
		if(temp1.isFriendly(temp2))
			return false;
		else{
			return true;
		}
	}

	public void remove(JLabel a){
		chessPiece temp = map.get(a);
		if(temp != null){
			graveyard.add(temp);
			temp.remove();
		}
	}

	
	
/*	public boolean causesCheck(chessPiece piece, Location newLoc){
		if(piece == null || newLoc == null){
			throw new IllegalArgumentException("Piece or Location is null");
		}
		saveGrid();
		piece.moveTo(newLoc);
		boolean result = this.checkForCheck(piece.getTeam());
		undoMove();
		return result;
	}*/
	
//	public boolean checkForCheck(boolean team){
//		chessPiece piece;
//		for(Iterator<chessPiece> i = gr.iterator(); i.hasNext();){
//			piece = i.next();
//			if(piece != null && piece.getTeam() != team){
//				ArrayList<Location> locs = piece.getValidMoveLocations();
//				for(Location loc : locs){
//					if (gr.get(loc) instanceof King && (gr.get(loc).getTeam()==team))
//					{
//						return true;
//					}
//				}
//			}
//		}
//		return false;
//	}
	
	public Location getKingLocation(boolean team){
		chessPiece piece;
		for(Iterator<chessPiece> i = gr.iterator(); i.hasNext();){
			piece = i.next();
			
			if(piece != null && piece instanceof King && piece.getTeam() == team)
				return piece.getLocation();
		}
		return null;
	}

	public boolean checkForCheck(boolean team){
		Location kingLoc = this.getKingLocation(team);
		chessPiece piece;
		
		for(Iterator<chessPiece> i = gr.iterator(); i.hasNext();){
			piece = i.next();
			if(piece.getTeam() != team && piece.getValidMoveLocations().contains(kingLoc))
				return true;
		}
		
		return false;
	}
	
	public boolean causesCheck(chessPiece piece, Location newLoc){
		try{
			boolean pawnFirstMove = false;
			if(piece instanceof Pawn)
				pawnFirstMove = ((Pawn) piece).isFirstMove();
			
			chessPiece temp = gr.get(newLoc);
			Location oldLoc = piece.getLocation();
			piece.moveTo(newLoc);
			boolean result = checkForCheck(piece.getTeam());
			piece.undoMove(oldLoc);
			if(piece instanceof Pawn && pawnFirstMove)
				((Pawn) piece).setFirstMove();
			if(temp != null)
				temp.putSelfInGrid(gr, newLoc);
			
			System.out.println(result);
			return result;
		}
		catch(Exception e){
			System.out.println("Invalid move");
			return false;
		}
	}
	
	public boolean gameOver(){
		if(checkForCheck(bottomTurn)){
			chessPiece piece;
			for(Iterator<chessPiece> i = gr.iterator(); i.hasNext();){
				piece = i.next();
				if(piece.getTeam() == bottomTurn){
					ArrayList<Location> locs = piece.getValidMoveLocations();
					Location oldLoc = piece.getLocation();
					for(Location loc : locs){
						chessPiece temp = gr.get(loc);
						piece.moveTo(loc);
						if(!checkForCheck(bottomTurn)){
							piece.moveTo(oldLoc);
							if(temp != null){
								temp.putSelfInGrid(gr, loc);
								return false;
							}
						}
						else{
							piece.moveTo(oldLoc);
							if(temp != null)
								temp.putSelfInGrid(gr, loc);
						}
					}
				}
			}
			return true;
		}
		return false;
	}
}