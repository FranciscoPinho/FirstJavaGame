package maze.logic;

import java.awt.font.ShapeGraphicAttribute;
import java.io.Serializable;
import java.util.Random;
import java.util.Vector;
/** 
 * class responsible for the generation of the random maze
 * @author Pedro Arnaldo, Francisco Pinho
 *
 */
public class MazeGenerator implements Serializable {

	char[][] tabrand;
	int nlin, ncol;

	public MazeGenerator() {
		char[][] tabuleiroRand = { // 15x15
				{ 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X',
						'X', 'X', 'X' },
				{ 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X',
						'X', 'X', 'X' },
				{ 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X',
						'X', 'X', 'X' },
				{ 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X',
						'X', 'X', 'X' },
				{ 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X',
						'X', 'X', 'X' },
				{ 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X',
						'X', 'X', 'X' },
				{ 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X',
						'X', 'X', 'X' },
				{ 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X',
						'X', 'X', 'X' },
				{ 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X',
						'X', 'X', 'X' },
				{ 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X',
						'X', 'X', 'X' },
				{ 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X',
						'X', 'X', 'X' },
				{ 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X',
						'X', 'X', 'X' },
				{ 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X',
						'X', 'X', 'X' },
				{ 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X',
						'X', 'X', 'X' },
				{ 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X',
						'X', 'X', 'X' } };
		setTabrand(tabuleiroRand);
		nlin = tabuleiroRand.length;
		ncol = tabuleiroRand[0].length;
	}

	public MazeGenerator(int nrlin, int nrcol, boolean empty) {
		char[][] maze = new char[nrlin][nrcol];
		if (!empty)
			for (int l = 0; l < nrlin; l++) {
				for (int c = 0; c < nrcol; c++) {
					maze[l][c] = 'X';
				}
			}
		else
			for (int l = 0; l < nrlin; l++) {
				for (int c = 0; c < nrcol; c++) {
					maze[l][c] = ' ';
				}
			}
		setTabrand(maze);
		nlin = maze.length;
		ncol = maze[0].length;
	}

	public MazeGenerator(char[][] maze) {
		setTabrand(maze);
		nlin = maze.length;
		ncol = maze[0].length;
	}
/**
 * adds a line
 * @return the new maze
 */
	public char[][] addrow() {
		char[][] newmaze = new char[nlin + 1][ncol];
		for (int l = 0; l < nlin; l++) {
			for (int c = 0; c < ncol; c++) {
				newmaze[l][c] = tabrand[l][c];
			}
		}
		for (int c = 0; c < ncol; c++) {
			newmaze[nlin + 1][c] = ' ';
		}
		return newmaze;
	}
/**
* delets a line
 * @return the new maze
 */
	public char[][] delrow() {
		char[][] newmaze = new char[nlin - 1][ncol];
		for (int l = 0; l < nlin - 1; l++) {
			for (int c = 0; c < ncol; c++) {
				newmaze[l][c] = tabrand[l][c];
			}
		}
		return newmaze;
	}
/**
* adds a column
 * @return the new maze
 */
	public char[][] addcol() {
		char[][] newmaze = new char[nlin][ncol + 1];
		for (int l = 0; l < nlin; l++) {
			for (int c = 0; c < ncol; c++) {
				newmaze[l][c] = tabrand[l][c];
			}
		}
		for (int l = 0; l < nlin; l++) {
			newmaze[l][ncol + 1] = ' ';
		}
		return newmaze;
	}
/**
* delets a column
 * @return the new maze
 */
	public char[][] delcol() {
		char[][] newmaze = new char[nlin][ncol - 1];
		for (int l = 0; l < nlin; l++) {
			for (int c = 0; c < ncol - 1; c++) {
				newmaze[l][c] = tabrand[l][c];
			}
		}
		return newmaze;
	}

	/**
	 * maze generator
	 */
	public void generateMaze() {
		Random randomGenerator = new Random();
		int lin = randomGenerator.nextInt(nlin);
		int col = randomGenerator.nextInt(ncol);

		while (lin % 2 == 0 || col % 2 == 0) {
			lin = randomGenerator.nextInt(nlin);
			col = randomGenerator.nextInt(ncol);
		}
		tabrand[lin][col] = ' ';

		rec(lin, col);
		int linS = randomGenerator.nextInt(nlin);
		int colS = randomGenerator.nextInt(ncol);
		boolean done = false;
		while (!done) {
			if ((colS == 0 && linS != 0 && linS != nlin - 1)
					|| (colS == ncol - 1 && linS != 0 && linS != nlin - 1)
					|| (linS == nlin - 1 && colS != 0 && colS != ncol - 1)
					|| (linS == 0 && colS != 0 && colS != ncol - 1)) {
				tabrand[linS][colS] = 'S';
				// clear spaces around the exit
				if (colS == 0) {
					tabrand[linS][colS + 1] = ' ';
					if (linS + 1 < nlin - 1)
						tabrand[linS + 1][colS + 1] = ' ';
					if (linS - 1 > 0)
						tabrand[linS - 1][colS + 1] = ' ';
				}
				if (colS == ncol - 1) {
					tabrand[linS][colS - 1] = ' ';
					if (linS + 1 < nlin - 1)
						tabrand[linS + 1][colS - 1] = ' ';
					if (linS - 1 > 0)
						tabrand[linS - 1][colS - 1] = ' ';
				}
				if (linS == 0) {
					tabrand[linS + 1][colS] = ' ';
					if (colS + 1 < ncol - 1)
						tabrand[linS + 1][colS + 1] = ' ';
					if (colS - 1 > 0)
						tabrand[linS + 1][colS - 1] = ' ';
				}
				if (linS == nlin - 1) {
					tabrand[linS - 1][colS] = ' ';
					if (colS + 1 < ncol - 1)
						tabrand[linS - 1][colS + 1] = ' ';
					if (colS - 1 > 0)
						tabrand[linS - 1][colS - 1] = ' ';
				}
				done = true;
			} else {
				linS = randomGenerator.nextInt(nlin);
				colS = randomGenerator.nextInt(ncol);
			}
		}

	}

	/**
	 * receive an array and shuffles it
	 * 
	 * @param ar
	 *            array to shuffle
	 */
	void shuffleArray(int[] ar) {
		Random rnd = new Random();
		for (int i = ar.length - 1; i > 0; i--) {
			int index = rnd.nextInt(i + 1);
			// Simple swap
			int a = ar[index];
			ar[index] = ar[i];
			ar[i] = a;
		}
	}

	/**
	 * recursion function to construct the maze
	 * 
	 * @param lin
	 *            line
	 * @param col
	 *            column
	 */
	public void rec(int lin, int col) {
		int[] randomDir = generateRandomDirections();
		for (int i = 0; i < randomDir.length; i++) {
			switch (randomDir[i]) {
			case 0: // up
				if (lin - 2 <= 0)
					continue;
				if (tabrand[lin - 2][col] != ' ') {
					tabrand[lin - 2][col] = ' ';
					tabrand[lin - 1][col] = ' ';
					rec(lin - 2, col);
				}
				break;
			case 1: // down
				if (lin + 2 >= nlin - 1)
					continue;
				if (tabrand[lin + 2][col] != ' ') {
					tabrand[lin + 2][col] = ' ';
					tabrand[lin + 1][col] = ' ';
					rec(lin + 2, col);
				}
				break;
			case 2: // left
				if (col - 2 <= 0)
					continue;
				if (tabrand[lin][col - 2] != ' ') {
					tabrand[lin][col - 2] = ' ';
					tabrand[lin][col - 1] = ' ';
					rec(lin, col - 2);
				}
				break;
			case 3: // right
				if (col + 2 >= ncol - 1)
					continue;
				if (tabrand[lin][col + 2] != ' ') {
					tabrand[lin][col + 2] = ' ';
					tabrand[lin][col + 1] = ' ';
					rec(lin, col + 2);
				}
				break;
			}
		}
	}

	/**
	 * clear every game element of the maze
	 * 
	 * @param rmaze
	 *            maze to clear
	 */
	public void clearMaze(MazeGenerator rmaze) {
		for (int i = 0; i < rmaze.getncol(); i++)
			for (int j = 0; j < rmaze.getnlin(); j++)
				if (rmaze.getTabrand()[j][i] != 'X'
						&& rmaze.getTabrand()[j][i] != 'S')
					rmaze.getTabrand()[j][i] = ' ';
	}

	// generate a random direction array to help the construction of the maze

	/**
	 * places the game elements on the maze
	 * 
	 * @param H
	 *            Hero
	 * @param E
	 *            Sword
	 * @param nD
	 *            number of dragons to play against
	 * @param dardos
	 *            Dart vector
	 * @param P
	 *            Shield
	 * @param test
	 * @return vector of dragons existing in the maze
	 */
	public Vector<Dragao> placeGameElementsRandDartShield(Heroi H, Espada E,
			int nD, Vector<Dardo> dardos, Escudo P, int test) {
		Random randomGenerator = new Random();
		Vector<Dragao> D = new Vector<Dragao>();

		if (test == 0) {
			boolean available1 = false, available2 = false, available3 = false, available4 = false, available5 = false;
			for (int i = 0; i < nD; i++) {
				Dragao dra = new Dragao();
				D.add(dra);
			}
			while (!available1) {

				E.col = randomGenerator.nextInt(ncol);
				E.lin = randomGenerator.nextInt(nlin);

				if (tabrand[E.lin][E.col] == ' ')
					available1 = true;
			}

			tabrand[E.lin][E.col] = 'E';

			for (int c = 0; c < D.size(); c++) {
				while (!available2) {

					D.get(c).lin = randomGenerator.nextInt(nlin);
					D.get(c).col = randomGenerator.nextInt(ncol);

					if (tabrand[D.get(c).lin][D.get(c).col] == ' ')
						available2 = true;
				}
				tabrand[D.get(c).lin][D.get(c).col] = D.get(c).Status;
				available2 = false;
			}

			while (!available3) {

				H.col = randomGenerator.nextInt(ncol);
				H.lin = randomGenerator.nextInt(nlin);

				if (tabrand[H.lin][H.col] == ' ')
					available3 = true;
			}

			tabrand[H.lin][H.col] = 'H';

			int nrOfDarts = nD;

			for (int i = 0; i < nrOfDarts; i++) {
				Dardo dardo = new Dardo();
				dardos.add(dardo);
			}

			for (int c = 0; c < dardos.size(); c++) {
				while (!available4) {

					dardos.get(c).lin = randomGenerator.nextInt(nlin);
					dardos.get(c).col = randomGenerator.nextInt(ncol);

					if (tabrand[dardos.get(c).lin][dardos.get(c).col] == ' ')
						available4 = true;
				}

				tabrand[dardos.get(c).lin][dardos.get(c).col] = 'Y';
				available4 = false;
			}

			while (!available5) {

				P.col = randomGenerator.nextInt(ncol);
				P.lin = randomGenerator.nextInt(nlin);

				if (tabrand[P.lin][P.col] == ' ')
					available5 = true;
			}

			tabrand[P.lin][P.col] = 'P';
		}

		else if (test == 1) {
			boolean available3 = false;

			while (!available3) {

				H.col = randomGenerator.nextInt(ncol);
				H.lin = randomGenerator.nextInt(nlin);

				if (tabrand[H.lin][H.col] == ' ')
					available3 = true;
			}

			tabrand[H.lin][H.col] = 'H';
		}

		else if (test == 5) {
			boolean available3 = false;

			while (!available3) {

				H.col = randomGenerator.nextInt(ncol);
				H.lin = randomGenerator.nextInt(nlin);

				if (tabrand[H.lin][H.col] == ' ')
					available3 = true;
			}
			tabrand[H.lin][H.col] = 'H';

			if (tabrand[H.lin + 1][H.col] == ' ') {
				Dardo ds = new Dardo();
				ds.lin = H.lin + 1;
				ds.col = H.col;
				dardos.add(ds);
				tabrand[ds.lin][ds.col] = 'Y';

			}
			if (tabrand[H.lin - 1][H.col] == ' ') {
				Dardo ds = new Dardo();
				ds.lin = H.lin - 1;
				ds.col = H.col;
				dardos.add(ds);
				tabrand[ds.lin][ds.col] = 'Y';

			}
			if (tabrand[H.lin][H.col + 1] == ' ') {
				Dardo ds = new Dardo();
				ds.lin = H.lin;
				ds.col = H.col + 1;
				dardos.add(ds);
				tabrand[ds.lin][ds.col] = 'Y';

			}
			if (tabrand[H.lin][H.col - 1] == ' ') {
				Dardo ds = new Dardo();
				ds.lin = H.lin;
				ds.col = H.col - 1;
				dardos.add(ds);
				tabrand[ds.lin][ds.col] = 'Y';
			}
		}

		else if (test == 6) {
			boolean available3 = false;

			while (!available3) {

				H.col = randomGenerator.nextInt(ncol);
				H.lin = randomGenerator.nextInt(nlin);

				if (tabrand[H.lin][H.col] == ' ')
					available3 = true;
			}
			tabrand[H.lin][H.col] = 'H';

			if (tabrand[H.lin + 1][H.col] == ' ') {
				P.lin = H.lin + 1;
				P.col = H.col;
				tabrand[P.lin][P.col] = 'P';

			} else if (tabrand[H.lin - 1][H.col] == ' ') {
				P.lin = H.lin - 1;
				P.col = H.col;
				tabrand[P.lin][P.col] = 'P';

			} else if (tabrand[H.lin][H.col + 1] == ' ') {
				P.lin = H.lin;
				P.col = H.col + 1;
				tabrand[P.lin][P.col] = 'P';

			} else if (tabrand[H.lin][H.col - 1] == ' ') {
				P.lin = H.lin;
				P.col = H.col - 1;
				tabrand[P.lin][P.col] = 'P';
			}
		}

		else if (test == 7) {
			Dragao d = new Dragao();
			boolean available2 = false;
			while (!available2) {

				d.lin = randomGenerator.nextInt(nlin);
				d.col = randomGenerator.nextInt(ncol);

				if (tabrand[d.lin][d.col] == ' ')
					available2 = true;
			}
			tabrand[d.lin][d.col] = d.Status;
			D.add(d);
		}

		else if (test == 8) {
			Dragao d = new Dragao();
			boolean available2 = false, blank = false;
			while (!available2) {

				d.lin = randomGenerator.nextInt(nlin);
				d.col = randomGenerator.nextInt(ncol);

				if (tabrand[d.lin][d.col] == ' ')
					available2 = true;
			}
			tabrand[d.lin][d.col] = d.Status;

			D.add(d);

			if (tabrand[d.lin + 1][d.col] == ' ') {
				E.lin = d.lin + 1;
				E.col = d.col;
				tabrand[E.lin][E.col] = 'E';
				blank = true;
			}

			if (tabrand[d.lin - 1][d.col] == ' ') {
				if (blank)
					tabrand[d.lin - 1][d.col] = 'X';
				else {
					E.lin = d.lin - 1;
					E.col = d.col;
					tabrand[E.lin][E.col] = 'E';
					blank = true;
				}
			}

			if (tabrand[d.lin][d.col + 1] == ' ') {
				if (blank)
					tabrand[d.lin][d.col + 1] = 'X';
				else {
					E.lin = d.lin;
					E.col = d.col + 1;
					tabrand[E.lin][E.col] = 'E';
					blank = true;
				}
			}

			if (tabrand[d.lin][d.col - 1] == ' ') {
				if (blank)
					tabrand[d.lin][d.col - 1] = 'X';
				else {
					E.lin = d.lin;
					E.col = d.col - 1;
					tabrand[E.lin][E.col] = 'E';
					blank = true;
				}
			}
		}

		else if (test == 9) {
			Dragao d = new Dragao();
			boolean available2 = false, blank = false;
			while (!available2) {

				d.lin = randomGenerator.nextInt(nlin);
				d.col = randomGenerator.nextInt(ncol);

				if (tabrand[d.lin][d.col] == ' ')
					available2 = true;
			}
			tabrand[d.lin][d.col] = d.Status;

			D.add(d);

			if (tabrand[d.lin + 1][d.col] == 'P') {
				P.lin = d.lin + 1;
				P.col = d.col;
				tabrand[P.lin][P.col] = 'P';
				blank = true;
			}

			if (tabrand[d.lin - 1][d.col] == ' ') {
				if (blank)
					tabrand[d.lin - 1][d.col] = 'X';
				else {
					P.lin = d.lin - 1;
					P.col = d.col;
					tabrand[P.lin][P.col] = 'P';
					blank = true;
				}
			}

			if (tabrand[d.lin][d.col + 1] == ' ') {
				if (blank)
					tabrand[d.lin][d.col + 1] = 'X';
				else {
					P.lin = d.lin;
					P.col = d.col + 1;
					tabrand[P.lin][P.col] = 'P';
					blank = true;
				}
			}

			if (tabrand[d.lin][d.col - 1] == ' ') {
				if (blank)
					tabrand[d.lin][d.col - 1] = 'X';
				else {
					P.lin = d.lin;
					P.col = d.col - 1;
					tabrand[P.lin][P.col] = 'P';
					blank = true;
				}
			}
		}

//		else if (test == 10) {
//			Dragao d = new Dragao();
//			Dardo y = new Dardo();
//			boolean available2 = false, blank = false;
//			while (!available2) {
//
//				d.lin = randomGenerator.nextInt(nlin);
//				d.col = randomGenerator.nextInt(ncol);
//
//				if (tabrand[d.lin][d.col] == ' ')
//					available2 = true;
//			}
//			tabrand[d.lin][d.col] = d.Status;
//
//			D.add(d);
//
//			if (tabrand[d.lin + 1][d.col] == ' ') {
//				y.lin = d.lin + 1;
//				y.col = d.col;
//				tabrand[y.lin][y.col] = 'Y';
//				blank = true;
//			}
//
//			if (tabrand[d.lin - 1][d.col] == ' ') {
//				if (blank)
//					tabrand[d.lin - 1][d.col] = 'X';
//				else {
//					y.lin = d.lin - 1;
//					y.col = d.col;
//					tabrand[y.lin][y.col] = 'Y';
//					blank = true;
//				}
//			}
//
//			if (tabrand[d.lin][d.col + 1] == ' ') {
//				if (blank)
//					tabrand[d.lin][d.col + 1] = 'X';
//				else {
//					y.lin = d.lin;
//					y.col = d.col + 1;
//					tabrand[y.lin][y.col] = 'Y';
//					blank = true;
//				}
//			}
//
//			if (tabrand[d.lin][d.col - 1] == ' ') {
//				if (blank)
//					tabrand[d.lin][d.col - 1] = 'X';
//				else {
//					y.lin = d.lin;
//					y.col = d.col - 1;
//					tabrand[y.lin][y.col] = 'Y';
//					blank = true;
//				}
//			}
//			dardos.add(y);
//		}
//
//		else if (test == 11) {
//			Dragao d = new Dragao();
//			boolean available2 = false, blank = false;
//			while (!available2) {
//
//				d.lin = randomGenerator.nextInt(nlin);
//				d.col = randomGenerator.nextInt(ncol);
//
//				if (tabrand[d.lin][d.col] == ' ')
//					available2 = true;
//			}
//			tabrand[d.lin][d.col] = d.Status;
//
//			D.add(d);
//
//			if (tabrand[d.lin + 1][d.col] == ' ') {
//				H.lin = d.lin + 1;
//				H.col = d.col;
//				tabrand[H.lin][H.col] = 'H';
//				blank = true;
//			}
//
//			if (tabrand[d.lin - 1][d.col] == ' ') {
//				if (blank)
//					tabrand[d.lin - 1][d.col] = 'X';
//				else {
//					H.lin = d.lin - 1;
//					H.col = d.col;
//					tabrand[H.lin][H.col] = 'H';
//					blank = true;
//				}
//			}
//
//			if (tabrand[d.lin][d.col + 1] == ' ') {
//				if (blank)
//					tabrand[d.lin][d.col + 1] = 'X';
//				else {
//					H.lin = d.lin;
//					H.col = d.col + 1;
//					tabrand[H.lin][H.col] = 'H';
//					blank = true;
//				}
//			}
//
//			if (tabrand[d.lin][d.col - 1] == ' ') {
//				if (blank)
//					tabrand[d.lin][d.col - 1] = 'X';
//				else {
//					H.lin = d.lin;
//					H.col = d.col - 1;
//					tabrand[H.lin][H.col] = 'H';
//					blank = true;
//				}
//			}
//		}
//
//		else if (test == 12) {
//			Dragao d = new Dragao();
//			boolean available2 = false, blank = false;
//			while (!available2) {
//
//				H.lin = randomGenerator.nextInt(nlin);
//				H.col = randomGenerator.nextInt(ncol);
//
//				if (tabrand[d.lin][d.col] == ' ')
//					available2 = true;
//			}
//			tabrand[H.lin][H.col] = H.Status;
//
//			D.add(d);
//		}
		return D;
	}

	/**
	 * places the dragon below the hero
	 * 
	 * @param H
	 *            Hero
	 * @param D
	 *            Drag
	 * @return vector of dragons existing in the maze
	 */
	public Vector<Dragao> placeHeroDragDown(Heroi H, Vector<Dragao> D) {
		Random randomGenerator = new Random();
		boolean available = false, blank = false;
		Dragao d = new Dragao();
		while (!available) {

			H.lin = randomGenerator.nextInt(nlin-4)+2;
			H.col = randomGenerator.nextInt(ncol-4)+2;

			if (tabrand[H.lin][H.col] == ' ')
				available = true;
		}
		tabrand[H.lin][H.col] = H.Status;
		d.lin = H.lin + 2;
		d.col = H.col;
		tabrand[d.lin][d.col] = d.Status;
		if (d.lin != getnlin() - 1)
			tabrand[d.lin + 1][H.col] = 'X';
		if (d.col != getncol() - 1)
			tabrand[d.lin][d.col + 1] = 'X';
		if (d.col != 0)
			tabrand[d.lin][d.col - 1] = 'X';
		D.add(d);
		return D;
	}

	/**
	 * places the dragon above the hero
	 * 
	 * @param H
	 *            Hero
	 * @param D
	 *            Drag
	 * @return vector of dragons existing in the maze
	 */
	public Vector<Dragao> placeHeroDragUp(Heroi H, Vector<Dragao> D) {
		Random randomGenerator = new Random();
		boolean available = false, blank = false;
		Dragao d = new Dragao();
		while (!available) {

			H.lin = randomGenerator.nextInt(nlin-4)+2;
			H.col = randomGenerator.nextInt(ncol-4)+2;

			if (tabrand[H.lin][H.col] == ' ')
				available = true;
		}
		tabrand[H.lin][H.col] = H.Status;

		d.lin = H.lin - 2;
		d.col = H.col;
		tabrand[d.lin][d.col] = d.Status;
		if (d.lin != 0)
			tabrand[d.lin - 1][d.col] = 'X';
		if (d.col != getncol() - 1)
			tabrand[d.lin][d.col + 1] = 'X';
		if (d.col != 0)
			tabrand[d.lin][d.col - 1] = 'X';
		D.add(d);
		return D;
	}

	/**
	 * places the dragon on the left of the hero
	 * 
	 * @param H
	 *            Hero
	 * @param D
	 *            Drag
	 * @return vector of dragons existing in the maze
	 */
	public Vector<Dragao> placeHeroDragLeft(Heroi H, Vector<Dragao> D) {
		Random randomGenerator = new Random();
		boolean available = false, blank = false;
		Dragao d = new Dragao();
		while (!available) {

			H.lin = randomGenerator.nextInt(nlin-4)+2;
			H.col = randomGenerator.nextInt(ncol-4)+2;

			if (tabrand[H.lin][H.col] == ' ')
				available = true;
		}
		tabrand[H.lin][H.col] = H.Status;

		d.lin = H.lin;
		d.col = H.col - 2;
		tabrand[d.lin][d.col] = d.Status;
		if (d.lin != getnlin() - 1)
			tabrand[d.lin + 1][H.col] = 'X';
		if (d.lin != 0)
			tabrand[d.lin - 1][d.col] = 'X';
		if (d.col != 0)
			tabrand[d.lin][d.col - 1] = 'X';
		D.add(d);
		return D;
	}

	/**
	 * places the dragon on the right of the hero
	 * 
	 * @param H
	 *            Hero
	 * @param D
	 *            Drag
	 * @return vector of dragons existing in the maze
	 */
	public Vector<Dragao> placeHeroDragRight(Heroi H, Vector<Dragao> D) {
		Random randomGenerator = new Random();
		boolean available = false, blank = false;
		Dragao d = new Dragao();
		while (!available) {

			H.lin = randomGenerator.nextInt(nlin-4)+2;
			H.col = randomGenerator.nextInt(ncol-4)+2;

			if (tabrand[H.lin][H.col] == ' ')
				available = true;
		}
		tabrand[H.lin][H.col] = H.Status;

		d.lin = H.lin;
		d.col = H.col + 2;
		tabrand[d.lin][d.col] = d.Status;
		if (d.lin != getnlin() - 1)
			tabrand[d.lin + 1][H.col] = 'X';
		if (d.lin != 0)
			tabrand[d.lin - 1][d.col] = 'X';
		if (d.col != getncol() - 1)
			tabrand[d.lin][d.col + 1] = 'X';
		D.add(d);
		return D;
	}

	/**
	 * places the Dragon (right), Sword(left), Shield(Down) and a Dart(Up)
	 * around the hero
	 * 
	 * @param H
	 *            Hero
	 * @param D
	 *            Dragon vector
	 * @param E
	 *            Sword
	 * @param P
	 *            Shield
	 * @param dardos
	 *            Darts vector
	 * @return
	 */
	public Vector<Dragao> placeHeroRDrag(Heroi H, Vector<Dragao> D, Espada E,
			Escudo P, Vector<Dardo> dardos) {
		Random randomGenerator = new Random();
		boolean available = false, blank = false;
		Dragao d = new Dragao();
		Dardo y = new Dardo();
		while (!available) {

			H.lin = randomGenerator.nextInt(nlin);
			H.col = randomGenerator.nextInt(ncol);

			if (tabrand[H.lin][H.col] == ' ')
				available = true;
		}
		tabrand[H.lin][H.col] = H.Status;
		d.col = H.col + 1;
		d.lin = H.lin;
		tabrand[d.lin][d.col] = d.Status;
		D.add(d);
		E.col = H.col - 1;
		E.lin = H.lin;
		tabrand[E.lin][E.col] = E.Status;
		P.col = H.col;
		P.lin = H.lin + 1;
		tabrand[P.lin][P.col] = P.Status;
		y.col = H.col;
		y.lin = H.lin - 1;
		tabrand[y.lin][y.col] = y.Status;
		dardos.add(y);

		return D;
	}

	/**
	 * places the Dragon (left), Sword(right), Shield(Up) and a Dart(Down)
	 * around the hero
	 * 
	 * @param H
	 *            Hero
	 * @param D
	 *            Dragon vector
	 * @param E
	 *            Sword
	 * @param P
	 *            Shield
	 * @param dardos
	 *            Darts vector
	 * @return
	 */
	public Vector<Dragao> placeHeroLDrag(Heroi H, Vector<Dragao> D, Espada E,
			Escudo P, Vector<Dardo> dardos) {
		Random randomGenerator = new Random();
		boolean available = false, blank = false;
		Dragao d = new Dragao();
		Dardo y = new Dardo();
		while (!available) {

			H.lin = randomGenerator.nextInt(nlin);
			H.col = randomGenerator.nextInt(ncol);

			if (tabrand[H.lin][H.col] == ' ')
				available = true;
		}
		tabrand[H.lin][H.col] = H.Status;
		d.col = H.col - 1;
		d.lin = H.lin;
		tabrand[d.lin][d.col] = d.Status;
		D.add(d);
		E.col = H.col + 1;
		E.lin = H.lin;
		tabrand[E.lin][E.col] = E.Status;
		P.col = H.col;
		P.lin = H.lin - 1;
		tabrand[P.lin][P.col] = P.Status;
		y.col = H.col;
		y.lin = H.lin + 1;
		tabrand[y.lin][y.col] = y.Status;
		dardos.add(y);

		return D;
	}

	/**
	 * places the Dragon (Up), Sword(Down), Shield(Right) and a Dart(Left)
	 * around the hero
	 * 
	 * @param H
	 *            Hero
	 * @param D
	 *            Dragon vector
	 * @param E
	 *            Sword
	 * @param P
	 *            Shield
	 * @param dardos
	 *            Darts vector
	 * @return
	 */
	public Vector<Dragao> placeHeroUDrag(Heroi H, Vector<Dragao> D, Espada E,
			Escudo P, Vector<Dardo> dardos) {
		Random randomGenerator = new Random();
		boolean available = false, blank = false;
		Dragao d = new Dragao();
		Dardo y = new Dardo();
		while (!available) {

			H.lin = randomGenerator.nextInt(nlin);
			H.col = randomGenerator.nextInt(ncol);

			if (tabrand[H.lin][H.col] == ' ')
				available = true;
		}
		tabrand[H.lin][H.col] = H.Status;
		d.col = H.col;
		d.lin = H.lin - 1;
		tabrand[d.lin][d.col] = d.Status;
		D.add(d);
		E.col = H.col;
		E.lin = H.lin + 1;
		tabrand[E.lin][E.col] = E.Status;
		P.col = H.col + 1;
		P.lin = H.lin;
		tabrand[P.lin][P.col] = P.Status;
		y.col = H.col - 1;
		y.lin = H.lin;
		tabrand[y.lin][y.col] = y.Status;
		dardos.add(y);

		return D;
	}

	/**
	 * places the Dragon (Down), Sword(Up), Shield(Left) and a Dart(Right)
	 * around the hero
	 * 
	 * @param H
	 *            Hero
	 * @param D
	 *            Dragon vector
	 * @param E
	 *            Sword
	 * @param P
	 *            Shield
	 * @param dardos
	 *            Darts vector
	 * @return
	 */
	public Vector<Dragao> placeHeroDDrag(Heroi H, Vector<Dragao> D, Espada E,
			Escudo P, Vector<Dardo> dardos) {
		Random randomGenerator = new Random();
		boolean available = false, blank = false;
		Dragao d = new Dragao();
		Dardo y = new Dardo();
		while (!available) {

			H.lin = randomGenerator.nextInt(nlin);
			H.col = randomGenerator.nextInt(ncol);

			if (tabrand[H.lin][H.col] == ' ')
				available = true;
		}
		tabrand[H.lin][H.col] = H.Status;
		d.col = H.col;
		d.lin = H.lin + 1;
		tabrand[d.lin][d.col] = d.Status;
		D.add(d);
		E.col = H.col;
		E.lin = H.lin - 1;
		tabrand[E.lin][E.col] = E.Status;
		P.col = H.col - 1;
		P.lin = H.lin;
		tabrand[P.lin][P.col] = P.Status;
		y.col = H.col + 1;
		y.lin = H.lin;
		tabrand[y.lin][y.col] = y.Status;
		dardos.add(y);

		return D;
	}
	
	

	/**
	 * gets the number of blank spaces in a maze
	 * 
	 * @return nr of blank spaces(int)
	 */
	public int nrOfBlankSpaces() {
		int lin = 0, col = 0, total = 0;
		for (; lin < nlin; lin++) {
			col = 0;
			for (; col < ncol; col++) {
				if (tabrand[lin][col] == ' ') {
					total++;
				}
			}
		}
		return total;
	}

	/**
	 * 
	 * @return a shuffled array with the number from 0 to 3
	 */
	public int[] generateRandomDirections() {
		int[] randoms = { 0, 1, 2, 3 };
		shuffleArray(randoms);
		return randoms;
	}

	/**
	 * 
	 * @return the maze
	 */
	public char[][] getTabrand() {
		return tabrand;
	}

	/**
	 * sets a random maze
	 * 
	 * @param tabrand
	 */
	public void setTabrand(char[][] tabrand) {
		this.tabrand = tabrand;
	}

	/**
	 * 
	 * @return the number of lines of a maze
	 */
	public int getnlin() {
		return nlin;
	}

	/**
	 * 
	 * @return the number of columns of a maze
	 */
	public int getncol() {
		return ncol;
	}

	/**
	 * puts a char in a certain position of a maze
	 * 
	 * @param lin
	 *            line
	 * @param col
	 *            column
	 * @param c
	 *            char to appy
	 */
	public void setPosition(int lin, int col, char c) {
		tabrand[lin][col] = c;
	}
}
