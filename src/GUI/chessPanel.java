/*
 * @file: chessPanel.java
 * @author: Keith Monaghan
 * @date: 6-25-14
 * 
 * Purpose:  Class for chessBoard tiles.  
 */


/*~~~~~TO DO~~~~~
 * Implement new game
 * Implement undo move
 * Create about section
 */

package GUI;

import java.awt.Color;
import java.awt.LayoutManager;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class chessPanel extends JPanel{
	private boolean highlighted;
	private Border empty;
	private Border highlight;
	
	//Constructor
	//Creates a new chessPanel that is unhighlighted
	public chessPanel(LayoutManager layout, Color color){
		super(layout);
		
		highlighted = false;
		
		this.setBackground(color);
		
		empty = BorderFactory.createLineBorder(Color.BLACK);
		highlight = BorderFactory.createLineBorder(Color.YELLOW, 4);
		
		this.setBorder(empty);
		
	}
	
	//Highlights the chessPanel
	public void highlight(){
		this.setBorder(highlight);
		highlighted = true;
	}
	
	//Unhighlights the chessPanel
	public void unhighlight(){
		this.setBorder(empty);
		highlighted = false;
	}
	
	//Returns true if the chessPanel is highlighted
	public boolean isHighlighted(){
		return highlighted;
	}
}
