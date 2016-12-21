package maze.logic;

import java.io.Serializable;
/** 
 * class responsible for "Dardo" game element
 * @author Pedro Arnaldo, Francisco Pinho
 *
 */
public class Dardo extends GameElements implements Serializable{
	/**
	 * Dard constructor with no parameters
	 */
	public Dardo() {
		Status='Y';
	}
	/**
	 * Dart construtor with line and columns as parameters
	 * @param l lin
	 * @param c column
	 */
	public Dardo(int l, int c) {
		Status='Y';
		lin=l;
		col=c;
	}

}
