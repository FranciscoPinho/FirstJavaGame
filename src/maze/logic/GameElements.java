package maze.logic;

import java.io.Serializable;
/** 
 * Class responsible for all game elements (hero, sword ...)
 * @author Pedro Arnaldo, Francisco Pinho
 *
 */
public abstract class GameElements implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int col, lin;
	char Status;
	
	/**
	 * equals operator for gameElements
	 * @param x object
	 * @return true if equal
	 */
	public boolean equal(Object x){
		if(x == null &&! (x instanceof GameElements))
			return false;
		if(this.col==((GameElements)x).col && this.lin == ((GameElements)x).lin)
			return true;
		return false;
	}
	/**
	 * gets column of the element
	 * @return column
	 */
	public int getCol(){
		return col;
	}
	/**
	 * gets line of the element
	 * @return line
	 */
	public int getLin(){
		return lin;
	}
	public char getStatus(){
		return Status;
	}
	public void setStatus(char s){
		Status=s;
	}
}


