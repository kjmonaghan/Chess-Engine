/*
 * @file: Grid.java
 * @author: Keith Monaghan
 * @date: 6-17-14
 * 
 * Purpose:  Designed to hold chessPieces orgnized by coordinate locations
 * 
 * Inspired by GridWorld
 * 
 */

package Grid;

import java.util.Iterator;

public class Grid<E> implements Iterable<E>{
	//Instance Variables

	private Object[][] pieces;
	int rows, cols;
	
	//Constructor
	public Grid(int row, int col){
		pieces = new Object[row][col];
		rows = row;
		cols = col;
	}
	
	//  Get + Set Methods
	public E get(Location loc){
		if(!validLocation(loc)){
			throw new IllegalArgumentException("Invalid Location: " + loc.toString());
		}
		return (E) pieces[loc.getRow()][loc.getCol()];
	}
	
	public int getRows(){
		return rows;
	}
	
	public int getCols(){
		return cols;
	}
	
	//Adds obj to the grid at loc
	public void add(Location loc, E obj){
		if(obj == null){
			throw new IllegalArgumentException("Null Object"); 
		}
		if(!validLocation(loc)){
			throw new IllegalArgumentException("Invalid Location: " + loc.toString());
		}
		
		pieces[loc.getRow()][loc.getCol()] = obj;
	}
	
	//Removes the object in the grid at Location loc and returns it
	public E remove(Location loc){
		if(!validLocation(loc)){
			throw new IllegalArgumentException("Invalid Location: " + loc.toString());
		}
		
		E obj = get(loc);
		pieces[loc.getRow()][loc.getCol()] = null;
		
		return obj;
	}
	
	
	//Returns true if the given location is within the boundaries of the grid
	public boolean validLocation(Location loc){
		if(loc.getRow() < 0 || loc.getRow() > (rows-1) || loc.getCol() < 0 || loc.getCol() > (cols-1))
			return false;
		else
			return true;
	}
	
	//Implements iterable
	@Override
    public Iterator<E> iterator() {
        Iterator<E> it = new Iterator<E>() {

            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < 64;
            }

            @SuppressWarnings("unchecked")
			@Override
            public E next() {
            	currentIndex++;
                return (E) pieces[(currentIndex-1)/8][(currentIndex-1)%8];
            }

            @Override
            public void remove() {
                // TODO Auto-generated method stub
            }
        };
        return it;
    }
	
}