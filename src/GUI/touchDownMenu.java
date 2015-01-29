/*
 * @file: touchDownMenu.java
 * @author: Keith Monaghan
 * @date: 6-30-14
 * 
 * Purpose: Piece replacement selection for pawns that have reached the opposite end of the board
 */


package GUI;

import java.util.ArrayList;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import Pieces.Pawn;
import Pieces.chessPiece;

public class touchDownMenu extends JPopupMenu{
	private Pawn pawn;
	
	private class choice extends JMenuItem{
		private chessPiece piece;
		
		public choice(chessPiece piece){
			super(piece.getClass().getSimpleName());
			this.piece = piece;
		}
		
	}
	public touchDownMenu(ArrayList<chessPiece> graveyard, Pawn pawn){
		this.pawn = pawn;
		for(chessPiece piece : graveyard){
			this.add(new choice(piece));
		}
	}
} 