package maze.logic;

import java.io.Serializable;
/** 
 * class responsible for "Heroi" game element
 * @author Pedro Arnaldo, Francisco Pinho
 *
 */
public class Heroi extends GameElements implements Serializable {
	int nrOfDarts;
	boolean hasShield, hasSword, isDead, isBurned;

	/**
	 * Hero constuctor with no parameters
	 */
	public Heroi() {
		Status = 'H';
		lin = 1;
		col = 1;
		hasShield = false;
		hasSword = false;
		isDead = false;
		isBurned = false;
	    nrOfDarts =0;
	}
/**
 * Hero constuctor with line and column parameters
 * @param l line 
 * @param c column
 */
	public Heroi(int l, int c){ 
        Status = 'H'; 
        lin=l; 
        col=c; 
    hasShield=false; 
    hasSword=false; 
    isDead=false; 
    isBurned=false; 
    nrOfDarts =0;
	} 
	/**
	 * set the number of darts on the Hero's inventory
	 * 
	 * @param nr
	 *            number o darts
	 */
	public void setNrDarts(int nr) {
		this.nrOfDarts = nr;
	}

	/**
	 * gets the number of darts on the Hero's inventory
	 * 
	 * @return number of darts
	 */
	public int getnrOfDarts() {
		return nrOfDarts;
	}

	/**
	 * gets isHeroDead boolean
	 * 
	 * @return isHeroDead boolean
	 */
	public boolean isHeroDead() {
		return isDead;
	}

	/**
	 * sets isDead to false
	 */
	public void resetHeroDead() {
		isDead = false;
	}

	/**
	 * gets the information about hero's possession of the sword
	 * 
	 * @return hasSword
	 */
	public boolean heroHasSword() {
		return hasSword;
	}

	/**
	 * sets hero's hasSword to true
	 */
	public void giveHeroSword() {
		hasSword = true;
	}

	/**
	 * sets hero's has sword to false;
	 */
	public void resetSword() {
		hasSword = false;
	}

	/**
	 * gets the information about hero's possession of the shield
	 * 
	 * @return hasShield
	 */
	public boolean heroHasShield() {
		return hasShield;
	}

	/**
	 * sets hero's hasShield to false
	 */
	public void resetHasShield() {
		hasShield = false;
	}

	/**
	 * sets hero's hasShield to true
	 */
	public void giveHeroShield() {
		hasShield = true;
	}

	/**
	 * gets the current line of the hero
	 * 
	 * @return hero's line
	 */
	public int getlin() {
		return lin;
	}

	/**
	 * gets the current line of the hero
	 * 
	 * @return hero's line
	 */
	public int getcol() {
		return col;
	}
}