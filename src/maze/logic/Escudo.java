package maze.logic;

import java.io.Serializable;
/** 
 * class responsible for "Escudo" game element
 * @author Pedro Arnaldo, Francisco Pinho
 *
 */
public class Escudo extends GameElements implements Serializable{
	/**
	 * Shield constuctor with no parameters
	 */
	public Escudo(){
		Status='P';
	}
	/**
	 * Shield constructor with line and column as parameters
	 * @param l line 	
	 * @param c column
	 */
	public Escudo(int l, int c){
		Status='P';
		lin=l;
		col=c;
	}
}
