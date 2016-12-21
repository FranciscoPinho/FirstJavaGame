package maze.logic;

import java.io.Serializable;
/** 
 * class responsible for "Espada" game element
 * @author Pedro Arnaldo, Francisco Pinho
 *
 */
public class Espada extends GameElements implements Serializable{ 
	/**
	 * Sword constuctor with no parameters
	 */
	public Espada(){
		Status = 'E';
	}
	/**
	 * Sword constructor with line and column as parameters
	 * @param l line 	
	 * @param c column
	 */
	public Espada(int l, int c){
		Status = 'E';
		lin=l;
		col=c;
	}
}