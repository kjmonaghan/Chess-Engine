/*
 * @file: chessWindow.java
 * @author: Keith Monaghan
 * @date: 6/18/14
 * 
 * Purpose:  Main GUI handler
 * 
 */

/*
 * ~~~~~~~~TO DO~~~~~~~~~~~~~
 * Add new game function to menu bar
 * Add castling graphic
 * Add pawn replacement pop-up
 * Add Documentation
 */
package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import Game.Board;

public class ChessWindow extends JFrame implements MouseListener, MouseMotionListener{
	JLayeredPane main;
	JPanel chessBoard;
	JLabel grabbedPiece;
	Container oldSpot;
	private Board gameBoard;
	
	int xAdjust, yAdjust;
	
	//Default constructor
	//Initializes the main window
	//Creates a new game
	public ChessWindow(){
		gameBoard = new Board();
		Dimension windowSize = new Dimension(600, 600);
		this.setResizable(false);
		
		//Initialize main
		main = new JLayeredPane();
		getContentPane().add(main);
		main.setPreferredSize(windowSize);
		main.addMouseListener(this);
		main.addMouseMotionListener(this);
		
		//Add menubar
		
		chessMenuBar menubar = new chessMenuBar(this);
		this.setJMenuBar(menubar);
		
		//Initialize chessBoard
		chessBoard = new JPanel();
		chessBoard.setPreferredSize(windowSize);
		chessBoard.setLayout(new GridLayout(8, 8));
		chessBoard.setBounds(0, 0, windowSize.width, windowSize.height);
		main.add(chessBoard, JLayeredPane.DEFAULT_LAYER);
		
		//Add chessBoard tiles
		for(int i = 0; i<64; i++){
			JPanel square;
			
			if((i/8)%2 == 0){
				if(i%2 == 0){
					square = new chessPanel(new BorderLayout(), Color.LIGHT_GRAY);
					
				}
				else{
					square = new chessPanel(new BorderLayout(), Color.BLUE);
				}
			}
			else{
				if(i%2 == 0){
					square = new chessPanel(new BorderLayout(), Color.BLUE);
				}
				else{
					square = new chessPanel(new BorderLayout(), Color.LIGHT_GRAY);
				}
			}
			
			chessBoard.add(square);
		}
	}
	
	//Starts a new game
	//Generates and places chessPiece icons
	public final void newGame(){
		//Clear Board
		for(JLabel label : gameBoard.getMap().keySet()){
			JPanel panel = (JPanel)chessBoard.getComponent((gameBoard.getMap().get(label).getLocation().getRow() * 8) + gameBoard.getMap().get(label).getLocation().getCol());
			panel.remove(label);
		}
		
		//Generate board
		gameBoard.buildBoard();
		
		//Place icons
		for(JLabel label : gameBoard.getMap().keySet()){
			JPanel panel = (JPanel)chessBoard.getComponent((gameBoard.getMap().get(label).getLocation().getRow() * 8) + gameBoard.getMap().get(label).getLocation().getCol());
			panel.add(label);
		}
	}
	
	//Returns the location in the grid
	public static final int getComponentIndex(Component component) {
		if (component != null && component.getParent() != null) {
	      Container c = component.getParent();
	      for (int i = 0; i < c.getComponentCount(); i++) {
	        if (c.getComponent(i) == component)
	          return i;
	      }
	    }
	    return -1;
	  }
	
	//~~~~~~~~~MOUSE EVENTS~~~~~~~~~//
	
	//Moves grabbed chessPiece icon with mouse
	@Override
	public void mouseDragged(MouseEvent me) {
		if(grabbedPiece == null)
			return;
		
		grabbedPiece.setLocation(me.getX() + xAdjust, me.getY() + yAdjust);
	}
	
	
	//Selects the chessPiece at the mouse's location
	//Picks up chessPiece if there are available moves
	//Highlights valid move locations
	//Moves chessPiece to grabbed layer
	@Override
	public void mousePressed(MouseEvent e) {
		//Grab Piece
		grabbedPiece = null;
		Component c = chessBoard.findComponentAt(e.getPoint());
		
		//Check to see if a Piece was grabbed
		if(c instanceof JPanel)
			return;
		
		//Checks if the piece can move
		if(gameBoard.getValidMoveLocations((JLabel)c).isEmpty())
			return;
		
		//Stores old position
		oldSpot = c.getParent();
		
		//Highlights valid move locations
		for(Integer element : gameBoard.getValidMoveLocations((JLabel)c)){
				((chessPanel) chessBoard.getComponent(element)).highlight();
		}
				
		
		//Moves piece to drag layer
		Point parentLocation = c.getParent().getLocation();
		xAdjust = parentLocation.x - e.getX();
		yAdjust = parentLocation.y - e.getY();
		grabbedPiece = (JLabel)c;
		grabbedPiece.setLocation(e.getX() + xAdjust, e.getY() + yAdjust);
		grabbedPiece.setSize(grabbedPiece.getWidth(), grabbedPiece.getHeight());
		main.add(grabbedPiece, JLayeredPane.DRAG_LAYER);
	}
	
	//Places chessPiece in new location and inverts the turn
	@Override
	public void mouseReleased(MouseEvent t) {
		if(grabbedPiece == null)
			return;
		
		//Remove highlight from tiles 
		for(Integer element : gameBoard.getValidMoveLocations(grabbedPiece)){
				((chessPanel) chessBoard.getComponent(element)).unhighlight();
		}
		
		//Find new location
		grabbedPiece.setVisible(false);
		Component c = chessBoard.findComponentAt(t.getPoint());
		
		//Is a piece captured?
		if(c instanceof JLabel){
			Container parent = c.getParent();
			try{
				gameBoard.move(grabbedPiece, getComponentIndex(parent));
				parent.remove(0);
				parent.add(grabbedPiece);
			}
			catch(Exception e){
				oldSpot.add(grabbedPiece);
			}	
		}
		
		//Place piece
		else{
			try{
				Container parent = (Container)c;
				gameBoard.move(grabbedPiece, getComponentIndex(parent));
				parent.add(grabbedPiece);
			}
			catch(Exception e){
				oldSpot.add(grabbedPiece);
			}
		}
		
		grabbedPiece.setVisible(true);
	}
	
	//Do nothing for these things
	public void mouseMoved(MouseEvent arg0) {}
	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
	public void mouseClicked(MouseEvent arg0) {}
	
	
	//Main
	public static void main(String[] args){
		ChessWindow frame = new ChessWindow();
		frame.newGame();
		frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);	
	}
}