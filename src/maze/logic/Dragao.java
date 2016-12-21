package maze.logic;

import java.io.Serializable;
/** 
 * class responsible for "Dragao" game element
 * @author Pedro Arnaldo, Francisco Pinho
 *
 */
public class Dragao extends GameElements implements Serializable{ 
	int sleepRounds;
	/**
	 * Dragon constructor with no parameters
	 */
	public Dragao(){
		Status = 'D';
		sleepRounds = 0;
	}
	/**
	 * Dragon construtor with line and columns as parameters
	 * @param l
	 * @param c
	 */
	public Dragao(int l, int c){
		Status = 'D';
		sleepRounds = 0;
		lin=l;
		col=c;
	}
	/**
	 * set the number o rounds asleep
	 * @param i number of rounds asleep
	 */
	public void setSleepRounds(int i){
		sleepRounds=i;
	}
	/**
	 * gets the number of round the Dragon will sleep
	 * @return number of rounds asleep
	 */
	 public int getSleepRounds(){ 
         return sleepRounds; 
    }; 
	
}
