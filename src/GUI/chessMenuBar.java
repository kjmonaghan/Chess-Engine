/*
 * @file: chessMenuBar.java
 * @author: Keith Monaghan
 * @date: 7-3-14
 * 
 * Purpose:  Handles the top menu bar for the chess game
 * 
 */

package GUI;

import java.awt.event.*;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;


public class chessMenuBar extends JMenuBar implements ActionListener{
	private ChessWindow window;
	private JMenuItem newGame;
	
	//Constructs the menu bar and adds it to the chessWindow
	public chessMenuBar(ChessWindow window){
		super();
		this.window = window;
		
		JMenu file = new JMenu("File");
		this.add(file);
		newGame = new JMenuItem("New game");
		newGame.addActionListener(this);
		file.add(newGame);
		
		JMenu help = new JMenu("Help");
		this.add(help);
		JMenuItem about = new JMenuItem("About");
		help.add(about);
	}
	
	public void actionPerformed(ActionEvent e){
		if(e.getSource().equals(newGame))
			window.newGame();
	}
}
