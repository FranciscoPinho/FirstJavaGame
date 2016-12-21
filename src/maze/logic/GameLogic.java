package maze.logic;

import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

import maze.cli.consola;
/** 
 * Class responsible for the game logic itself, moving the hero, throwing darts and dragon behaviour. Core game logic.
 * @author Pedro Arnaldo, Francisco Pinho
 *
 */
public class GameLogic {

	
/**
 * checks if the hero can move to the desired destination
 * @param rmaze maze
 * @param H Hero
 * @return true, if he can move right, false if he can't
 */
	public boolean canGoRightRand(MazeGenerator rmaze, Heroi H) {
		if (H.col + 1 == rmaze.getncol())
			return false;
		if (rmaze.getTabrand()[H.lin][H.col + 1] == 'X')
			return false;
		if (rmaze.getTabrand()[H.lin][H.col + 1] == 'd' && !H.hasSword)
			return false;
		return true;
	}
/**
 * checks if the hero can move to the desired destination
 * @param rmaze maze
 * @param H Hero
 * @return true, if he can move Left, false if he can't
 */
	public boolean canGoLeftRand(MazeGenerator rmaze, Heroi H) {
		if (H.col - 1 < 0)
			return false;
		if (rmaze.getTabrand()[H.lin][H.col - 1] == 'X')
			return false;
		if (rmaze.getTabrand()[H.lin][H.col - 1] == 'd' && !H.hasSword)
			return false;
		return true;
	}
/**
 * checks if the hero can move to the desired destination
 * @param rmaze maze
 * @param H Hero
 * @return true, if he can move Up, false if he can't
 */
	public boolean canGoUpRand(MazeGenerator rmaze, Heroi H) {
		if (H.lin - 1 < 0)
			return false;
		if (rmaze.getTabrand()[H.lin - 1][H.col] == 'X')
			return false;
		if (rmaze.getTabrand()[H.lin - 1][H.col] == 'd' && !H.hasSword)
			return false;
		return true;
	}
/**
 * checks if the hero can move to the desired destination
 * @param rmaze maze
 * @param H Hero
 * @return true, if he can move Down, false if he can't
 */
	public boolean canGoDownRand(MazeGenerator rmaze, Heroi H) {
		if (H.lin + 1 == rmaze.getnlin())
			return false;
		if (rmaze.getTabrand()[H.lin + 1][H.col] == 'X')
			return false;
		if (rmaze.getTabrand()[H.lin + 1][H.col] == 'd' && !H.hasSword)
			return false;
		return true;
	}

	// Move hero functions for random maze
	/**
	 * moves hero to the Right
	 * @param rmaze maze
	 * @param H Hero
	 * @param E Sword
	 * @param D Dragon Vector
	 * @param Y Dart Vector
	 * @param P Shield
	 * @return true if the game ends, false if it continues
	 */
	public boolean moveHeroRight(MazeGenerator rmaze, Heroi H, Espada E,
			Vector<Dragao> D, Vector<Dardo> Y, Escudo P) {
		int newcol, newlin;
		boolean specialMov = false;
		if (canGoRightRand(rmaze, H)) {
			newcol = H.col + 1;
			H.col = newcol;
			newlin = H.lin;

			// this led to the exit, thus the player wins
			if (rmaze.getTabrand()[newlin][newcol] == 'S') {
				rmaze.getTabrand()[newlin][newcol - 1] = ' ';
				return true;
			}
			// the hero has the sword and thus leads to the death of the
			// dragon
			if (findDrag(D, newlin, newcol) != -1 && H.hasSword) {
				D.remove(findDrag(D, newlin, newcol));
				rmaze.getTabrand()[newlin][newcol - 1] = ' ';
				rmaze.getTabrand()[H.lin][H.col] = H.Status;
				specialMov = true;
			}
			// the hero doesn't have the sword and goes against the dragon
			if (findDrag(D, newlin, newcol) != -1 && !H.hasSword) {
				rmaze.getTabrand()[newlin][newcol - 1] = ' ';
				rmaze.getTabrand()[H.lin][H.col] = D.get(findDrag(D, newlin,
						newcol)).Status;
				specialMov = true;
				H.isDead = true;
				return true;
			}
			// the hero catches the sword
			if (newlin == E.lin && newcol == E.col && !H.hasSword) {
				rmaze.getTabrand()[newlin][newcol - 1] = ' ';
				H.hasSword = true;
				E.lin = -1;
				E.col = -1;
				rmaze.getTabrand()[H.lin][H.col] = H.Status;
				specialMov = true;
			}
			// the hero catches the shield
			if (newlin == P.lin && newcol == P.col) {
				rmaze.getTabrand()[newlin][newcol - 1] = ' ';
				H.hasShield = true;
				P.lin = -1;
				P.col = -1;
				rmaze.getTabrand()[H.lin][H.col] = H.Status;
				specialMov = true;
			}
			// the hero gets a dart
			if (findDardo(Y, newlin, newcol) != -1) {
				H.nrOfDarts++;
				Y.remove(findDardo(Y, newlin, newcol));
				rmaze.getTabrand()[newlin][newcol - 1] = ' ';
				rmaze.getTabrand()[H.lin][H.col] = H.Status;
				specialMov = true;
			}
			// empty space move
			if (specialMov == false) {
				rmaze.getTabrand()[newlin][newcol - 1] = ' ';
				rmaze.getTabrand()[H.lin][H.col] = H.Status;
			}
			specialMov = false;
		}
		return false;
	}
/**
 * 
	 * moves hero to the Up
	 * @param rmaze maze
	 * @param H Hero
	 * @param E Sword
	 * @param D Dragon Vector
	 * @param Y Dart Vector
	 * @param P Shield
	 * @return true if the game ends, false if it continues
 */
	public boolean moveHeroUp(MazeGenerator rmaze, Heroi H, Espada E,
			Vector<Dragao> D, Vector<Dardo> Y, Escudo P) {
		int newcol, newlin;
		boolean specialMov = false;
		if (canGoUpRand(rmaze, H)) {
			newlin = H.lin - 1;
			H.lin = newlin;
			newcol = H.col;

			// this led to the exit, thus the player wins
			if (rmaze.getTabrand()[newlin][newcol] == 'S') {
				rmaze.getTabrand()[newlin + 1][newcol] = ' ';
				return true;
			}
			// the hero has the sword and thus leads to the death of the
			// dragon
			if (findDrag(D, newlin, newcol) != -1 && H.hasSword) {
				D.remove(findDrag(D, newlin, newcol));
				rmaze.getTabrand()[newlin + 1][newcol] = ' ';
				rmaze.getTabrand()[H.lin][H.col] = H.Status;
				specialMov = true;
			}
			// the hero doesn't have the sword and goes against the dragon
			if (findDrag(D, newlin, newcol) != -1 && !H.hasSword) {
				rmaze.getTabrand()[newlin + 1][newcol] = ' ';
				rmaze.getTabrand()[H.lin][H.col] = D.get(findDrag(D, newlin,
						newcol)).Status;
				specialMov = true;
				H.isDead = true;
				return true;
			}
			// the hero catches the sword
			if (newlin == E.lin && newcol == E.col && !H.hasSword) {
				rmaze.getTabrand()[newlin + 1][newcol] = ' ';
				H.hasSword = true;
				E.lin = -1;
				E.col = -1;
				rmaze.getTabrand()[H.lin][H.col] = H.Status;
				specialMov = true;
			}
			// the hero catches the shield
			if (newlin == P.lin && newcol == P.col) {
				rmaze.getTabrand()[newlin + 1][newcol] = ' ';
				H.hasShield = true;
				P.lin = -1;
				P.col = -1;
				rmaze.getTabrand()[H.lin][H.col] = H.Status;
				specialMov = true;
				;
			}
			// the hero gets a dart
			if (findDardo(Y, newlin, newcol) != -1) {
				H.nrOfDarts++;
				Y.remove(findDardo(Y, newlin, newcol));
				rmaze.getTabrand()[newlin + 1][newcol] = ' ';
				rmaze.getTabrand()[H.lin][H.col] = H.Status;
				specialMov = true;
			}
			// empty space move
			if (specialMov == false) {
				rmaze.getTabrand()[newlin + 1][newcol] = ' ';
				rmaze.getTabrand()[H.lin][H.col] = H.Status;
			}
			specialMov = false;
		}
		return false;
	}
/**
	 * moves hero to the Down
	 * @param rmaze maze
	 * @param H Hero
	 * @param E Sword
	 * @param D Dragon Vector
	 * @param Y Dart Vector
	 * @param P Shield
	 * @return true if the game ends, false if it continues
 */
	public boolean moveHeroDown(MazeGenerator rmaze, Heroi H, Espada E,
			Vector<Dragao> D, Vector<Dardo> Y, Escudo P) {
		int newcol, newlin;
		boolean specialMov = false;
		if (canGoDownRand(rmaze, H)) {
			newlin = H.lin + 1;
			H.lin = newlin;
			newcol = H.col;

			// this led to the exit, thus the player wins
			if (rmaze.getTabrand()[newlin][newcol] == 'S') {
				rmaze.getTabrand()[newlin - 1][newcol] = ' ';
				return true;
			}
			// the hero has the sword and thus leads to the death of the
			// dragon
			if (findDrag(D, newlin, newcol) != -1 && H.hasSword) {
				D.remove(findDrag(D, newlin, newcol));
				rmaze.getTabrand()[newlin - 1][newcol] = ' ';
				rmaze.getTabrand()[H.lin][H.col] = H.Status;
				specialMov = true;
			}
			// the hero doesn't have the sword and goes against the dragon
			if (findDrag(D, newlin, newcol) != -1 && !H.hasSword) {
				rmaze.getTabrand()[newlin - 1][newcol] = ' ';
				rmaze.getTabrand()[H.lin][H.col] = D.get(findDrag(D, newlin,
						newcol)).Status;
				specialMov = true;
				H.isDead = true;
				return true;
			}
			// the hero catches the sword
			if (newlin == E.lin && newcol == E.col && !H.hasSword) {
				rmaze.getTabrand()[newlin - 1][newcol] = ' ';
				H.hasSword = true;
				E.lin = -1;
				E.col = -1;
				rmaze.getTabrand()[H.lin][H.col] = H.Status;
				specialMov = true;
			}
			// the hero catches the shield
			if (newlin == P.lin && newcol == P.col) {
				rmaze.getTabrand()[newlin - 1][newcol] = ' ';
				H.hasShield = true;
				P.lin = -1;
				P.col = -1;
				rmaze.getTabrand()[H.lin][H.col] = H.Status;
				specialMov = true;
				;
			}
			// the hero gets a dart
			if (findDardo(Y, newlin, newcol) != -1) {
				H.nrOfDarts++;
				Y.remove(findDardo(Y, newlin, newcol));
				rmaze.getTabrand()[newlin - 1][newcol] = ' ';
				rmaze.getTabrand()[H.lin][H.col] = H.Status;
				specialMov = true;
			}
			// empty space move
			if (specialMov == false) {
				rmaze.getTabrand()[newlin - 1][newcol] = ' ';
				rmaze.getTabrand()[H.lin][H.col] = H.Status;
			}
			specialMov = false;
		}
		return false;
	}
/**
	 * moves hero to the Left
	 * @param rmaze maze
	 * @param H Hero
	 * @param E Sword
	 * @param D Dragon Vector
	 * @param Y Dart Vector
	 * @param P Shield
	 * @return true if the game ends, false if it continues
 */
	public boolean moveHeroLeft(MazeGenerator rmaze, Heroi H, Espada E,
			Vector<Dragao> D, Vector<Dardo> Y, Escudo P) {
		int newcol, newlin;
		boolean specialMov = false;
		if (canGoLeftRand(rmaze, H)) {
			newcol = H.col - 1;
			H.col = newcol;
			newlin = H.lin;

			// this led to the exit, thus the player wins
			if (rmaze.getTabrand()[newlin][newcol] == 'S') {
				rmaze.getTabrand()[newlin][newcol + 1] = ' ';
				return true;
			}
			// the hero has the sword and thus leads to the death of the
			// dragon
			if (findDrag(D, newlin, newcol) != -1 && H.hasSword) {
				D.remove(findDrag(D, newlin, newcol));
				rmaze.getTabrand()[newlin][newcol + 1] = ' ';
				rmaze.getTabrand()[H.lin][H.col] = H.Status;
				specialMov = true;
			}
			// the hero doesn't have the sword and goes against the dragon
			if (findDrag(D, newlin, newcol) != -1 && !H.hasSword) {
				rmaze.getTabrand()[newlin][newcol + 1] = ' ';
				rmaze.getTabrand()[H.lin][H.col] = D.get(findDrag(D, newlin,
						newcol)).Status;
				specialMov = true;
				H.isDead = true;
				return true;
			}
			// the hero catches the sword
			if (newlin == E.lin && newcol == E.col && !H.hasSword) {
				rmaze.getTabrand()[newlin][newcol + 1] = ' ';
				H.hasSword = true;
				E.lin = -1;
				E.col = -1;
				rmaze.getTabrand()[H.lin][H.col] = H.Status;
				specialMov = true;
			}
			// the hero catches the shield
			if (newlin == P.lin && newcol == P.col) {
				rmaze.getTabrand()[newlin][newcol + 1] = ' ';
				H.hasShield = true;
				P.lin = -1;
				P.col = -1;
				rmaze.getTabrand()[H.lin][H.col] = H.Status;
				specialMov = true;
			}
			// the hero gets a dart
			if (findDardo(Y, newlin, newcol) != -1) {
				H.nrOfDarts++;
				Y.remove(findDardo(Y, newlin, newcol));
				rmaze.getTabrand()[newlin][newcol + 1] = ' ';
				rmaze.getTabrand()[H.lin][H.col] = H.Status;
				specialMov = true;
			}
			// empty space move
			if (specialMov == false) {
				rmaze.getTabrand()[newlin][newcol + 1] = ' ';
				rmaze.getTabrand()[H.lin][H.col] = H.Status;
			}
			specialMov = false;
		}
		return false;
	}

	// finds the dragon of location tab[lin,col] in the dragon vector, returns
	// the index of said dragon or returns -1 if not found
	/**
	 * find a Dragon in the given location
	 * @param D Dragon
	 * @param lin line
	 * @param col column
	 * @return the index of the dragon found in that position or -1 of there isn't any dragon there
	 */
	public int findDrag(Vector<Dragao> D, int lin, int col) {
		for (int i = 0; i < D.size(); i++) {
			if (D.get(i).lin == lin && D.get(i).col == col)
				return i;
		}
		return -1;
	}
/**
 	* find a Dragon in the given location
 * @param D Dart
 * @param lin line
 * @param col column
 * @return the index of the Dart found in that position or -1 of there isn't any Dart there
 */
	public int findDardo(Vector<Dardo> D, int lin, int col) {
		for (int i = 0; i < D.size(); i++) {
			if (D.get(i).lin == lin && D.get(i).col == col)
				return i;
		}
		return -1;
	}

	// if sleeprounds are 0, the dragon has a chance of going to sleep and it
	// refreshes the status as 'D' if it isn't asleep,
	// if sleeprounds>0, the dragon has 'd' status and the rounds of sleep are
	// reduced once per turn
	/**
	 * function that determines when and how long will the Dragon sleeps
	 * @param D Dragon
	 * @param rmaze maze
	 */
	public void updateDragSleepRand(Vector<Dragao> D, MazeGenerator rmaze) {
		Random randomGenerator = new Random();
		for (int i = 0; i < D.size(); i++) {
			if (D.get(i).sleepRounds > 0) {
				D.get(i).Status = 'd';
				D.get(i).sleepRounds--;
			}
			if (D.get(i).sleepRounds == 0) {
				D.get(i).Status = 'D';
				rmaze.getTabrand()[D.get(i).lin][D.get(i).col] = D.get(i).Status;
				int isSleep = randomGenerator.nextInt(5);
				if (isSleep == 3) {
					D.get(i).sleepRounds = randomGenerator.nextInt(5);
					if (D.get(i).sleepRounds > 0)
						D.get(i).Status = 'd';
					rmaze.getTabrand()[D.get(i).lin][D.get(i).col] = D.get(i).Status;
				}
			}
		}
	}
/**
 * Responsible for the firebreath funcionality of the Dragon
 * @param H Hero
 * @param D Dragon
 * @param rmaze maze
 * @return true if the game ends
 */
	public boolean DragonFireBreath(Heroi H, Vector<Dragao> D,
			MazeGenerator rmaze) {
		int newlin, newcol;
		int firecount = 2;
		for (int i = 0; i < D.size(); i++) {
			// if dragon is asleep we dont do anything
			if (D.get(i).Status != 'd') {
				// is hero within 3 squares north
				if (H.lin - D.get(i).lin < 4 && H.lin - D.get(i).lin >= 0
						&& H.col - D.get(i).col == 0) {
					newlin = D.get(i).lin + 1;
					newcol = D.get(i).col;
					while (newlin <= rmaze.nlin - 1 && firecount > 0) {
						if (rmaze.getTabrand()[newlin][newcol] == 'X')
							rmaze.getTabrand()[newlin][newcol] = ' ';
						if (rmaze.getTabrand()[newlin][newcol] == H.Status) {
							if (H.hasShield) {
							} else {
								rmaze.getTabrand()[H.lin][H.col] = 'F';
								return true;
							}
						}

						newlin++;
						firecount--;
					}

				}
				// is hero within 3 squares south
				if (D.get(i).lin - H.lin < 4 && D.get(i).lin - H.lin >= 0
						&& H.col - D.get(i).col == 0) {
					newlin = D.get(i).lin - 1;
					newcol = D.get(i).col;
					while (newlin >= 0 && firecount > 0) {
						if (rmaze.getTabrand()[newlin][newcol] == 'X')
							rmaze.getTabrand()[newlin][newcol] = ' ';
						if (rmaze.getTabrand()[newlin][newcol] == H.Status) {
							if (H.hasShield) {
							} else {
								rmaze.getTabrand()[H.lin][H.col] = 'F';
								return true;
							}
						}
						newlin--;
						firecount--;
					}
				}
				// is hero within 3 squares west
				if (D.get(i).col - H.col < 4 && D.get(i).col - H.col >= 0
						&& H.lin - D.get(i).lin == 0) {
					newlin = D.get(i).lin;
					newcol = D.get(i).col - 1;
					while (newlin >= 0 && firecount > 0) {
						if (rmaze.getTabrand()[newlin][newcol] == 'X')
							rmaze.getTabrand()[newlin][newcol] = ' ';
						if (rmaze.getTabrand()[newlin][newcol] == H.Status) {
							if (H.hasShield) {
							} else {
								rmaze.getTabrand()[H.lin][H.col] = 'F';
								return true;
							}
						}
						newcol--;
						firecount--;
					}
				}
				// is hero within 3 squares east
				if (H.col - D.get(i).col < 4 && H.col - D.get(i).col >= 0
						&& H.lin - D.get(i).lin == 0) {
					newlin = D.get(i).lin;
					newcol = D.get(i).col + 1;
					while (newlin >= 0 && firecount > 0) {
						if (rmaze.getTabrand()[newlin][newcol] == 'X')
							rmaze.getTabrand()[newlin][newcol] = ' ';
						if (rmaze.getTabrand()[newlin][newcol] == H.Status) {
							if (H.hasShield) {
							} else {
								rmaze.getTabrand()[H.lin][H.col] = 'F';
								return true;
							}
						}
						newcol++;
						firecount--;
					}
				}

			}
		}
		return false;
	}
/**
 * responsible for all dragon actions: movement, killing the hero, dying ...
 * @param rmaze
 * @param H Hero
 * @param E Sword
 * @param D Dragon Vector
 * @param Y Dart Vector
 * @param P Shield Vector
 * @param fire firebreath
 * @return if the game ends
 */
	public boolean dragonTurn(MazeGenerator rmaze, Heroi H, Espada E,
			Vector<Dragao> D, Vector<Dardo> Y, Escudo P, boolean fire) {
		int oldDcol, oldDlin;
		if (fire == true)
			if (DragonFireBreath(H, D, rmaze) == true) {
				return true;
			}
		outer_loop: for (int i = 0; i < D.size(); i++) {
			if (D.get(i).Status != 'd') {
				oldDlin = D.get(i).lin;
				oldDcol = D.get(i).col;
				// this will update D.lin and D.col
				canMoveDragaoRand(rmaze, H, E, D.get(i));
				// move dragon to where hero is
				if (rmaze.getTabrand()[D.get(i).lin][D.get(i).col] == H.Status) {
					if (!H.hasSword) {
						rmaze.getTabrand()[oldDlin][oldDcol] = ' ';
						rmaze.getTabrand()[D.get(i).lin][D.get(i).col] = D
								.get(i).Status;
						return true;
					}
					if (H.hasSword) {
						rmaze.getTabrand()[oldDlin][oldDcol] = ' ';
						rmaze.getTabrand()[D.get(i).lin][D.get(i).col] = H.Status;
						D.remove(i);
						if (D.isEmpty())
							break outer_loop;
					}
				}
				// move dragon to where sword is
				if (rmaze.getTabrand()[D.get(i).lin][D.get(i).col] == 'E') {
					E.Status = 'G';
					rmaze.getTabrand()[oldDlin][oldDcol] = ' ';
					rmaze.getTabrand()[D.get(i).lin][D.get(i).col] = E.Status;
				}
				// move dragon to where sword is
				if (rmaze.getTabrand()[D.get(i).lin][D.get(i).col] == 'P') {
					P.Status = 'N';
					rmaze.getTabrand()[oldDlin][oldDcol] = ' ';
					rmaze.getTabrand()[D.get(i).lin][D.get(i).col] = P.Status;
				}
				// move dragon to where dart is
				if (rmaze.getTabrand()[D.get(i).lin][D.get(i).col] == 'Y') {
					Y.get(findDardo(Y, D.get(i).lin, D.get(i).col)).Status = 'M';
					rmaze.getTabrand()[oldDlin][oldDcol] = ' ';
					rmaze.getTabrand()[D.get(i).lin][D.get(i).col] = Y
							.get(findDardo(Y, D.get(i).lin, D.get(i).col)).Status;
				}

				// move dragon to empty square
				if (rmaze.getTabrand()[D.get(i).lin][D.get(i).col] == ' ') {
					rmaze.getTabrand()[oldDlin][oldDcol] = ' ';
					rmaze.getTabrand()[D.get(i).lin][D.get(i).col] = D.get(i).Status;
				}
			}
		}

		// if there are no dragon in the same spot as the sword/shield/dart
		if (E.Status == 'G' && findDrag(D, E.lin, E.col) == -1) {
			E.Status = 'E';
			rmaze.getTabrand()[E.lin][E.col] = E.Status;
		}
		if (P.Status == 'N' && findDrag(D, P.lin, P.col) == -1) {
			P.Status = 'P';
			rmaze.getTabrand()[P.lin][P.col] = P.Status;
		}
		for (int i = 0; i < Y.size(); i++) {
			if (Y.get(i).Status == 'M'
					&& findDrag(D, Y.get(i).lin, Y.get(i).col) == -1) {
				Y.get(i).Status = 'Y';
				rmaze.getTabrand()[Y.get(i).lin][Y.get(i).col] = Y.get(i).Status;
			}
		}
		if (fire == true)
			if (DragonFireBreath(H, D, rmaze) == true) {
				return true;
			}
		return false;
	}
/**
 * throws the dart Left
 * @param rmaze maze
 * @param H Hero
 * @param D Dragon
 * @return true if he kills a dragon
 */
	public boolean throwDartLeft(MazeGenerator rmaze, Heroi H, Vector<Dragao> D) {
		int newlin, newcol;
		newlin = H.lin;
		newcol = H.col - 1;
		H.nrOfDarts--;
		outer_loop: while (newcol > -1) {
			if (rmaze.getTabrand()[newlin][newcol] == 'X') {
				break outer_loop;
			}
			if (findDrag(D, newlin, newcol) != -1) {
				rmaze.getTabrand()[newlin][newcol] = ' ';
				D.remove(findDrag(D, newlin, newcol));
				return true;
			}
			newcol--;
		}
		return false;
	}
/**
 * throws the dart Right
 * @param rmaze maze
 * @param H Hero
 * @param D Dragon
 * @return true if he kills a dragon
 */
	public boolean throwDartRight(MazeGenerator rmaze, Heroi H, Vector<Dragao> D) {
		int newlin, newcol;
		newlin = H.lin;
		newcol = H.col + 1;
		H.nrOfDarts--;
		outer_loop: while (newcol < rmaze.nlin) {
			if (rmaze.getTabrand()[newlin][newcol] == 'X') {
				break outer_loop;
			}
			if (findDrag(D, newlin, newcol) != -1) {
				rmaze.getTabrand()[newlin][newcol] = ' ';
				D.remove(findDrag(D, newlin, newcol));
				return true;
			}
			newcol++;
		}
		return false;
	}
/**
 * throws the dart Up
 * @param rmaze maze
 * @param H Hero
 * @param D Dragon
 * @return true if he kills a dragon
 */
	public boolean throwDartUp(MazeGenerator rmaze, Heroi H, Vector<Dragao> D) {
		int newlin, newcol;
		newlin = H.lin - 1;
		newcol = H.col;
		H.nrOfDarts--;
		outer_loop: while (newlin > -1) {
			if (rmaze.getTabrand()[newlin][newcol] == 'X') {
				break outer_loop;
			}
			if (findDrag(D, newlin, newcol) != -1) {
				rmaze.getTabrand()[newlin][newcol] = ' ';
				D.remove(findDrag(D, newlin, newcol));
				return true;
			}
			newlin--;
		}
		return false;
	}
/**
 * throws the dart Down
 * @param rmaze maze
 * @param H Hero
 * @param D Dragon
 * @return true if he kills a dragon
 */
	public boolean throwDartDown(MazeGenerator rmaze, Heroi H, Vector<Dragao> D) {
		int newlin, newcol;
		newlin = H.lin + 1;
		newcol = H.col;
		H.nrOfDarts--;
		outer_loop: while (newlin < rmaze.nlin) {
			if (rmaze.getTabrand()[newlin][newcol] == 'X') {
				break outer_loop;
			}
			if (findDrag(D, newlin, newcol) != -1) {
				rmaze.getTabrand()[newlin][newcol] = ' ';
				D.remove(findDrag(D, newlin, newcol));
				return true;
			}
			newlin++;
		}
		return false;
	}

	/**
	 * checks which position the dragon can move to
	 * 
	 * @param rmaze
	 *            maze
	 * @param H
	 *            Hero
	 * @param E
	 *            Sword
	 * @param D
	 *            Dragon
	 */
	public void canMoveDragaoRand(MazeGenerator rmaze, Heroi H, Espada E,
			Dragao D) {

		Random randomGenerator = new Random();
		int mov = randomGenerator.nextInt(4);

		boolean good = false;
		while (!good) {
			switch (mov) {
			case 0:// north
				if (rmaze.getTabrand()[D.lin - 1][D.col] == ' '
						|| rmaze.getTabrand()[D.lin - 1][D.col] == 'H'
						|| rmaze.getTabrand()[D.lin - 1][D.col] == 'P'
						|| rmaze.getTabrand()[D.lin - 1][D.col] == 'E'
						|| rmaze.getTabrand()[D.lin - 1][D.col] == 'Y') {
					D.lin--;
					good = true;
					break;
				}
				break;
			case 1:// south
				if (rmaze.getTabrand()[D.lin + 1][D.col] == ' '
						|| rmaze.getTabrand()[D.lin + 1][D.col] == 'H'
						|| rmaze.getTabrand()[D.lin + 1][D.col] == 'P'
						|| rmaze.getTabrand()[D.lin + 1][D.col] == 'E'
						|| rmaze.getTabrand()[D.lin + 1][D.col] == 'Y') {
					D.lin++;
					good = true;
					break;
				}
				break;
			case 2:// east
				if (rmaze.getTabrand()[D.lin][D.col + 1] == ' '
						|| rmaze.getTabrand()[D.lin][D.col + 1] == 'H'
						|| rmaze.getTabrand()[D.lin][D.col + 1] == 'P'
						|| rmaze.getTabrand()[D.lin][D.col + 1] == 'E'
						|| rmaze.getTabrand()[D.lin][D.col + 1] == 'Y') {
					D.col++;
					good = true;
					break;
				}
				break;
			case 3:// west
				if (rmaze.getTabrand()[D.lin][D.col - 1] == ' '
						|| rmaze.getTabrand()[D.lin][D.col - 1] == 'H'
						|| rmaze.getTabrand()[D.lin][D.col - 1] == 'P'
						|| rmaze.getTabrand()[D.lin][D.col - 1] == 'E'
						|| rmaze.getTabrand()[D.lin][D.col - 1] == 'Y') {
					D.col--;
					good = true;
					break;
				}
				break;
			case 4:// stay
				good = true;
				break;
			}

			if (!good)
				mov = randomGenerator.nextInt(4);
		}

	}

}