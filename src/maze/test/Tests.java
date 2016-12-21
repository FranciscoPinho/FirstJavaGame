package maze.test;

import static org.junit.Assert.*;

import java.util.Random;
import java.util.Vector;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import maze.cli.consola;
import maze.logic.*;

import org.junit.Test;

public class Tests {

	Vector<Dragao> D = new Vector<Dragao>();
	Vector<Dardo> Y = new Vector<Dardo>();
	Dragao D1 = new Dragao();
	Heroi H = new Heroi();
	GameLogic play = new GameLogic();
	MazeGenerator rmaze = new MazeGenerator();
	Espada E = new Espada();
	Escudo P = new Escudo();
	consola C = new consola();

	// random hero move
	/**
	 * Tests the canGo...Rand which result determines if we can go that
	 * direction or not
	 */
	@Test
	public void heroMoveRand() {
		rmaze.generateMaze();
		D = rmaze.placeGameElementsRandDartShield(H, E, 0, Y, P, 1);
		boolean test = false, test_res = false;

		test = play.canGoRightRand(rmaze, H);
		if (rmaze.getTabrand()[H.getLin()][H.getCol() + 1] != 'X')
			test_res = true;
		assertEquals(test_res, test);

		test = play.canGoLeftRand(rmaze, H);
		test_res = false;
		if (rmaze.getTabrand()[H.getLin()][H.getCol() - 1] != 'X')
			test_res = true;
		assertEquals(test_res, test);

		test = play.canGoUpRand(rmaze, H);
		test_res = false;
		if (rmaze.getTabrand()[H.getLin() - 1][H.getCol()] != 'X')
			test_res = true;
		assertEquals(test_res, test);

		test = play.canGoDownRand(rmaze, H);
		test_res = false;
		if (rmaze.getTabrand()[H.getLin() + 1][H.getCol()] != 'X')
			test_res = true;
		assertEquals(test_res, test);

	}

	// random dragon move
	/**
	 * Tests the functions that see if the dragon is able to move, given a
	 * certain maze
	 */
//	@Test
//	public void dragonMoveRand() {
//		rmaze.generateMaze();
//		D = rmaze.placeGameElementsRandDartShield(H, E, 1, Y, P, 7);
//		Dragao d = D.get(0);
//		boolean ableToMove = false, test_res = false;
//		int oldlin = d.getLin(), oldcol = d.getCol();
//
//		if (rmaze.getTabrand()[d.getLin() + 1][d.getCol()] != 'X'
//				|| rmaze.getTabrand()[d.getLin() - 1][d.getCol()] != 'X'
//				|| rmaze.getTabrand()[d.getLin()][d.getCol() + 1] != 'X'
//				|| rmaze.getTabrand()[d.getLin()][d.getCol() - 1] != 'X')
////			ableToMove = true;
//		play.canMoveDragaoRand(rmaze, H, E, d);
//		if (d.getCol() != oldcol || d.getLin() != oldlin
//				|| (d.getCol() == oldcol && d.getLin() == oldlin))
//			test_res = true;
//		assertEquals(ableToMove, test_res);
//	}

	// elements test
	/**
	 * Tests the creation and the clearing of a maze
	 */
	@Test
	public void mazeConstructorAndClear() {
		MazeGenerator m = new MazeGenerator();
		boolean test_res = true;

		outer_loop: for (int i = 0; i < m.getnlin(); i++)
			for (int j = 0; j < m.getncol(); j++)
				if (m.getTabrand()[i][j] != 'X') {
					test_res = false;
					break outer_loop;
				}

		assertEquals(true, test_res);

		m.generateMaze();
		m.placeGameElementsRandDartShield(H, E, 3, Y, P, 0);
		m.clearMaze(m);

		test_res = true;
		outer_loop2: for (int i = 0; i < m.getnlin(); i++)
			for (int j = 0; j < m.getncol(); j++)
				if (m.getTabrand()[i][j] != 'X' && m.getTabrand()[i][j] != ' '
						&& m.getTabrand()[i][j] != 'S') {
					test_res = false;
					break outer_loop2;
				}
		assertEquals(true, test_res);

	}

	/**
	 * Test if the game can be ended
	 */
	@Test
	public void endGame() {
		rmaze.generateMaze();
		D = rmaze.placeGameElementsRandDartShield(H, E, 0, Y, P, 4);
		boolean exit_close = false, test = false;

		if (rmaze.getTabrand()[H.getLin()][H.getCol() + 1] == 'S')
			exit_close = true;
		test = play.moveHeroRight(rmaze, H, E, D, Y, P);
		assertEquals(exit_close, test);

		exit_close = false;
		if (rmaze.getTabrand()[H.getLin()][H.getCol() - 1] == 'S')
			exit_close = true;
		test = play.moveHeroLeft(rmaze, H, E, D, Y, P);
		assertEquals(exit_close, test);

		exit_close = false;
		if (rmaze.getTabrand()[H.getLin() + 1][H.getCol()] == 'S')
			exit_close = true;
		test = play.moveHeroDown(rmaze, H, E, D, Y, P);
		assertEquals(exit_close, test);

		exit_close = false;
		if (rmaze.getTabrand()[H.getLin() - 1][H.getCol()] == 'S')
			exit_close = true;
		test = play.moveHeroUp(rmaze, H, E, D, Y, P);
		assertEquals(exit_close, test);
	}

	/**
	 * Tests if picking up a dart actually works
	 */
	@Test
	public void getDart() {
		rmaze.generateMaze();
		D = rmaze.placeGameElementsRandDartShield(H, E, 0, Y, P, 5);
		boolean dart_close = false, test = false, test_res = false;

		if (rmaze.getTabrand()[H.getLin()][H.getCol() + 1] == 'Y')
			dart_close = true;
		test = play.moveHeroRight(rmaze, H, E, D, Y, P);
		if (H.getnrOfDarts() == 1)
			test_res = true;
		assertEquals(dart_close, test_res);
		play.moveHeroLeft(rmaze, H, E, D, Y, P);

		dart_close = false;
		test_res = false;
		H.setNrDarts(0);
		if (rmaze.getTabrand()[H.getLin()][H.getCol() - 1] == 'Y')
			dart_close = true;
		test = play.moveHeroLeft(rmaze, H, E, D, Y, P);
		if (H.getnrOfDarts() == 1)
			test_res = true;
		assertEquals(dart_close, test_res);
		play.moveHeroRight(rmaze, H, E, D, Y, P);

		dart_close = false;
		test_res = false;
		H.setNrDarts(0);
		if (rmaze.getTabrand()[H.getLin() + 1][H.getCol()] == 'Y')
			dart_close = true;
		test = play.moveHeroDown(rmaze, H, E, D, Y, P);
		if (H.getnrOfDarts() == 1)
			test_res = true;
		assertEquals(dart_close, test_res);
		play.moveHeroUp(rmaze, H, E, D, Y, P);

		dart_close = false;
		test_res = false;
		H.setNrDarts(0);
		if (rmaze.getTabrand()[H.getLin() - 1][H.getCol()] == 'Y')
			dart_close = true;
		test = play.moveHeroUp(rmaze, H, E, D, Y, P);
		if (H.getnrOfDarts() == 1)
			test_res = true;
		assertEquals(dart_close, test_res);
		play.moveHeroDown(rmaze, H, E, D, Y, P);
	}

	/**
	 * tests if a hero can shoot a darth and kill a dragon with it
	 */
	@Test
	public void shootDart() {
		rmaze.generateMaze();
		D = rmaze.placeGameElementsRandDartShield(H, E, 0, Y, P, 3);
		boolean dragon_close = false, test = false;

		H.setNrDarts(1);
		if (rmaze.getTabrand()[H.getLin()][H.getCol() + 1] == 'D')
			dragon_close = true;
		test = play.throwDartRight(rmaze, H, D);
		assertEquals(dragon_close, test);

		H.setNrDarts(1);
		dragon_close = false;
		H.setNrDarts(1);
		if (rmaze.getTabrand()[H.getLin()][H.getCol() - 1] == 'D')
			dragon_close = true;
		test = play.throwDartLeft(rmaze, H, D);
		assertEquals(dragon_close, test);

		H.setNrDarts(1);
		dragon_close = false;
		H.setNrDarts(1);
		if (rmaze.getTabrand()[H.getLin() + 1][H.getCol()] == 'D')
			dragon_close = true;
		test = play.throwDartDown(rmaze, H, D);
		assertEquals(dragon_close, test);

		H.setNrDarts(1);
		dragon_close = false;
		H.setNrDarts(1);
		if (rmaze.getTabrand()[H.getLin() - 1][H.getCol()] == 'D')
			dragon_close = true;
		test = play.throwDartUp(rmaze, H, D);
		assertEquals(dragon_close, test);
	}

	/**
	 * tests if the hero can grab the sword and be protected by it
	 */
	@Test
	public void getShield() {
		rmaze.generateMaze();
		D = rmaze.placeGameElementsRandDartShield(H, E, 0, Y, P, 6);
		boolean shield_close = false, test = false, test_res = false;

		if (rmaze.getTabrand()[H.getLin()][H.getCol() + 1] == 'P')
			shield_close = true;
		test = play.moveHeroRight(rmaze, H, E, D, Y, P);
		if (H.heroHasShield())
			test_res = true;
		assertEquals(shield_close, test_res);

		H.resetHasShield();
		if (rmaze.getTabrand()[H.getLin()][H.getCol() - 1] == 'P')
			shield_close = true;
		test = play.moveHeroLeft(rmaze, H, E, D, Y, P);
		if (H.heroHasShield())
			test_res = true;
		assertEquals(shield_close, test_res);

		H.resetHasShield();
		if (rmaze.getTabrand()[H.getLin() + 1][H.getCol()] == 'P')
			shield_close = true;
		test = play.moveHeroDown(rmaze, H, E, D, Y, P);
		if (H.heroHasShield())
			test_res = true;
		assertEquals(shield_close, test_res);

		H.resetHasShield();
		if (rmaze.getTabrand()[H.getLin() - 1][H.getCol()] == 'P')
			shield_close = true;
		test = play.moveHeroUp(rmaze, H, E, D, Y, P);
		if (H.heroHasShield())
			test_res = true;
		assertEquals(shield_close, test_res);

	}

	/**
	 * Tests the placements of all game elements
	 */
	@Test
	public void testFullPlacements() {
		rmaze.generateMaze();
		D = rmaze.placeGameElementsRandDartShield(H, E, 1, Y, P, 0);
		Dragao d = D.get(0);
		boolean test_res = false;
		if ((H.getLin() != d.getLin() || H.getCol() != d.getCol())
				&& (H.getLin() != E.getLin() || H.getCol() != E.getCol())
				&& (d.getLin() != E.getLin() || d.getCol() != E.getCol()))
			test_res = true;
		assertEquals(true, test_res);
	}

	/**
	 * tests the sleeping subfunction
	 */
	@Test
	public void SleepRand() {
		rmaze.generateMaze();
		D = rmaze.placeGameElementsRandDartShield(H, E, 1, Y, P, 0);
		Dragao d = D.get(0);
		d.setSleepRounds(2);
		boolean test_res = false, drag_asleep = false;

		play.updateDragSleepRand(D, rmaze);
		if (d.getSleepRounds() == 1 && d.getStatus() == 'd')
			test_res = true;
		assertEquals(true, test_res);

		test_res = true;
		drag_asleep = true;
		d.setSleepRounds(0);
		play.updateDragSleepRand(D, rmaze);
		if (d.getStatus() == 'D')
			drag_asleep = false;
		if (d.getSleepRounds() == 0)
			test_res = false;
		assertEquals(drag_asleep, test_res);

		test_res = false;
		drag_asleep = false;
		d.setSleepRounds(0);
		play.updateDragSleepRand(D, rmaze);
		if (d.getStatus() == 'd')
			drag_asleep = true;
		if (d.getSleepRounds() != 0)
			test_res = true;
		assertEquals(drag_asleep, test_res);
	}

	/**
	 * Checks if the dragon kills the hero when he is below him
	 */
	@Test
	public void dragKillsHeroFBDown() {
		rmaze.generateMaze();
		D = rmaze.placeHeroDragRight(H, D);
		boolean test_res = false, test = false;

		if (rmaze.getTabrand()[H.getLin() + 2][H.getCol()] == 'D'
				|| rmaze.getTabrand()[H.getLin() - 2][H.getCol()] == 'D'
				|| rmaze.getTabrand()[H.getLin()][H.getCol() + 2] == 'D'
				|| rmaze.getTabrand()[H.getLin()][H.getCol() - 2] == 'D')
			test_res = true;
		test = play.dragonTurn(rmaze, H, E, D, Y, P, true);
		assertEquals(test_res, test);

	}

	/**
	 * Checks if the dragon kills the hero when he is above him
	 */
	@Test
	public void dragKillsHeroFBUp() {
		rmaze.generateMaze();
		D = rmaze.placeHeroDragLeft(H, D);
		boolean test_res = false, test = false;

		if (rmaze.getTabrand()[H.getLin() + 2][H.getCol()] == 'D'
				|| rmaze.getTabrand()[H.getLin() - 2][H.getCol()] == 'D'
				|| rmaze.getTabrand()[H.getLin()][H.getCol() + 2] == 'D'
				|| rmaze.getTabrand()[H.getLin()][H.getCol() - 2] == 'D')
			test_res = true;
		test = play.dragonTurn(rmaze, H, E, D, Y, P, true);
		assertEquals(test_res, test);

	}

	/**
	 * Checks if the dragon kills the hero when he is on his right
	 */
	@Test
	public void dragKillsHeroFBRight() {
		rmaze.generateMaze();
		D = rmaze.placeHeroDragDown(H, D);
		boolean test_res = false, test = false;

		if (rmaze.getTabrand()[H.getLin() + 2][H.getCol()] == 'D'
				|| rmaze.getTabrand()[H.getLin() - 2][H.getCol()] == 'D'
				|| rmaze.getTabrand()[H.getLin()][H.getCol() + 2] == 'D'
				|| rmaze.getTabrand()[H.getLin()][H.getCol() - 2] == 'D')
			test_res = true;
		test = play.dragonTurn(rmaze, H, E, D, Y, P, true);
		assertEquals(test_res, test);

	}

	/**
	 * Checks if the dragon kills the hero when he is on his left
	 */
	@Test
	public void dragKillsHeroFBLeft() {
		rmaze.generateMaze();
		D = rmaze.placeHeroDragUp(H, D);
		boolean test_res = false, test = false;

		if (rmaze.getTabrand()[H.getLin() + 2][H.getCol()] == 'D'
				|| rmaze.getTabrand()[H.getLin() - 2][H.getCol()] == 'D'
				|| rmaze.getTabrand()[H.getLin()][H.getCol() + 2] == 'D'
				|| rmaze.getTabrand()[H.getLin()][H.getCol() - 2] == 'D')
			test_res = true;
		test = play.dragonTurn(rmaze, H, E, D, Y, P, true);
		assertEquals(test_res, test);

	}

	/**
	 * test everything when the hero moves right(sword,shield,dart,dragon
	 * actions...)
	 */
	@Test
	public void heroRight() {
		// hero dies
		MazeGenerator m1 = new MazeGenerator();
		m1.generateMaze();
		D = m1.placeHeroRDrag(H, D, E, P, Y);
		boolean test_res = false, test = false, dragon_close = false;

		if (m1.getTabrand()[H.getLin()][H.getCol() + 1] == 'D')
			dragon_close = true;
		test = play.moveHeroRight(m1, H, E, D, Y, P);
		if (H.isHeroDead())
			test_res = true;
		assertEquals(dragon_close, test_res);
		D.remove(0);

		// dragon dies
		test_res = false;
		dragon_close = false;
		MazeGenerator m2 = new MazeGenerator();
		m2.generateMaze();
		D = m2.placeHeroRDrag(H, D, E, P, Y);

		if (m2.getTabrand()[H.getLin()][H.getCol() + 1] == 'D')
			dragon_close = true;
		H.giveHeroSword();
		test = play.moveHeroRight(m2, H, E, D, Y, P);
		if (D.isEmpty())
			test_res = true;
		assertEquals(dragon_close, test_res);

		test_res = false;
		boolean sword_close = false;
		H.resetSword();
		MazeGenerator m3 = new MazeGenerator();
		m3.generateMaze();
		D = m3.placeHeroRDrag(H, D, E, P, Y);

		if (m3.getTabrand()[H.getLin()][H.getCol() - 1] == 'E')
			sword_close = true;
		test = play.moveHeroLeft(m3, H, E, D, Y, P);
		if (H.heroHasSword() && E.getLin() == -1 && E.getCol() == -1)
			test_res = true;
		assertEquals(sword_close, test_res);
		D.remove(0);

		// get shield
		test_res = false;
		boolean shield_close = false;
		H.resetSword();
		MazeGenerator m4 = new MazeGenerator();
		m4.generateMaze();
		D = m4.placeHeroRDrag(H, D, E, P, Y);

		if (m4.getTabrand()[H.getLin() + 1][H.getCol()] == 'P')
			shield_close = true;
		test = play.moveHeroDown(m4, H, E, D, Y, P);
		if (H.heroHasShield() && P.getLin() == -1 && P.getCol() == -1)
			test_res = true;
		assertEquals(shield_close, test_res);
		D.remove(0);

		// get dart
		test_res = false;
		boolean dart_close = false;
		int darts = 0;
		H.setNrDarts(0);
		MazeGenerator m5 = new MazeGenerator();
		m5.generateMaze();
		D = m5.placeHeroRDrag(H, D, E, P, Y);

		if (m5.getTabrand()[H.getLin() - 1][H.getCol()] == 'Y')
			dart_close = true;
		test = play.moveHeroUp(m5, H, E, D, Y, P);
		if (H.getnrOfDarts() == darts + 1)
			test_res = true;
		assertEquals(dart_close, test_res);
		D.remove(0);

		// throw dart
		test_res = false;
		dragon_close = false;
		H.setNrDarts(1);
		MazeGenerator m6 = new MazeGenerator();
		m6.generateMaze();
		D = m6.placeHeroRDrag(H, D, E, P, Y);

		if (m6.getTabrand()[H.getLin()][H.getCol() + 1] == 'D')
			dart_close = true;
		test = play.throwDartRight(m6, H, D);
		if (test && D.isEmpty())
			test_res = true;
		assertEquals(dart_close, test_res);

	}

	/**
	 * test everything when the hero moves left(sword,shield,dart,dragon
	 * actions...)
	 */
	@Test
	public void heroLeft() {
		// hero dies
		MazeGenerator m1 = new MazeGenerator();
		m1.generateMaze();
		D = m1.placeHeroLDrag(H, D, E, P, Y);
		boolean test_res = false, test = false, dragon_close = false;

		if (m1.getTabrand()[H.getLin()][H.getCol() - 1] == 'D')
			dragon_close = true;
		test = play.moveHeroLeft(m1, H, E, D, Y, P);
		if (H.isHeroDead())
			test_res = true;
		assertEquals(dragon_close, test_res);
		D.remove(0);

		// dragon dies
		test_res = false;
		dragon_close = false;
		MazeGenerator m2 = new MazeGenerator();
		m2.generateMaze();
		D = m2.placeHeroLDrag(H, D, E, P, Y);

		if (m2.getTabrand()[H.getLin()][H.getCol() - 1] == 'D')
			dragon_close = true;
		H.giveHeroSword();
		test = play.moveHeroLeft(m2, H, E, D, Y, P);
		if (D.isEmpty())
			test_res = true;
		assertEquals(dragon_close, test_res);

		// get sword
		test_res = false;
		boolean sword_close = false;
		H.resetSword();
		MazeGenerator m3 = new MazeGenerator();
		m3.generateMaze();
		D = m3.placeHeroLDrag(H, D, E, P, Y);

		if (m3.getTabrand()[H.getLin()][H.getCol() + 1] == 'E')
			sword_close = true;
		test = play.moveHeroRight(m3, H, E, D, Y, P);
		if (H.heroHasSword() && E.getLin() == -1 && E.getCol() == -1)
			test_res = true;
		assertEquals(sword_close, test_res);
		D.remove(0);

		// get shield
		test_res = false;
		boolean shield_close = false;
		H.resetSword();
		MazeGenerator m4 = new MazeGenerator();
		m4.generateMaze();
		D = m4.placeHeroLDrag(H, D, E, P, Y);

		if (m4.getTabrand()[H.getLin() - 1][H.getCol()] == 'P')
			shield_close = true;
		test = play.moveHeroUp(m4, H, E, D, Y, P);
		if (H.heroHasShield() && P.getLin() == -1 && P.getCol() == -1)
			test_res = true;
		assertEquals(shield_close, test_res);
		D.remove(0);

		// get dart
		test_res = false;
		boolean dart_close = false;
		int darts = 0;
		H.setNrDarts(0);
		MazeGenerator m5 = new MazeGenerator();
		m5.generateMaze();
		D = m5.placeHeroLDrag(H, D, E, P, Y);

		if (m5.getTabrand()[H.getLin() + 1][H.getCol()] == 'Y')
			dart_close = true;
		test = play.moveHeroDown(m5, H, E, D, Y, P);
		if (H.getnrOfDarts() == darts + 1)
			test_res = true;
		assertEquals(dart_close, test_res);
		D.remove(0);

		// throw dart
		test_res = false;
		dragon_close = false;
		H.setNrDarts(1);
		MazeGenerator m6 = new MazeGenerator();
		m6.generateMaze();
		D = m6.placeHeroLDrag(H, D, E, P, Y);

		if (m6.getTabrand()[H.getLin()][H.getCol() - 1] == 'D')
			dart_close = true;
		test = play.throwDartLeft(m6, H, D);
		if (test && D.isEmpty())
			test_res = true;
		assertEquals(dart_close, test_res);
	}

	/**
	 * test everything when the hero moves upwards(sword,shield,dart,dragon
	 * actions...)
	 */
	@Test
	public void heroUp() {
		// hero dies
		MazeGenerator m1 = new MazeGenerator();
		m1.generateMaze();
		D = m1.placeHeroUDrag(H, D, E, P, Y);
		boolean test_res = false, test = false, dragon_close = false;

		if (m1.getTabrand()[H.getLin() - 1][H.getCol()] == 'D')
			dragon_close = true;
		test = play.moveHeroUp(m1, H, E, D, Y, P);
		if (H.isHeroDead())
			test_res = true;
		assertEquals(dragon_close, test_res);
		D.remove(0);

		// dragon dies
		test_res = false;
		dragon_close = false;
		MazeGenerator m2 = new MazeGenerator();
		m2.generateMaze();
		D = m2.placeHeroUDrag(H, D, E, P, Y);

		if (m2.getTabrand()[H.getLin() - 1][H.getCol()] == 'D')
			dragon_close = true;
		H.giveHeroSword();
		test = play.moveHeroUp(m2, H, E, D, Y, P);
		if (D.isEmpty())
			test_res = true;
		assertEquals(dragon_close, test_res);

		// get sword
		test_res = false;
		boolean sword_close = false;
		H.resetSword();
		MazeGenerator m3 = new MazeGenerator();
		m3.generateMaze();
		D = m3.placeHeroUDrag(H, D, E, P, Y);

		if (m3.getTabrand()[H.getLin() + 1][H.getCol()] == 'E')
			sword_close = true;
		test = play.moveHeroDown(m3, H, E, D, Y, P);
		if (H.heroHasSword() && E.getLin() == -1 && E.getCol() == -1)
			test_res = true;
		assertEquals(sword_close, test_res);
		D.remove(0);

		// get shield
		test_res = false;
		boolean shield_close = false;
		H.resetSword();
		MazeGenerator m4 = new MazeGenerator();
		m4.generateMaze();
		D = m4.placeHeroUDrag(H, D, E, P, Y);

		if (m4.getTabrand()[H.getLin()][H.getCol() + 1] == 'P')
			shield_close = true;
		test = play.moveHeroRight(m4, H, E, D, Y, P);
		if (H.heroHasShield() && P.getLin() == -1 && P.getCol() == -1)
			test_res = true;
		assertEquals(shield_close, test_res);
		D.remove(0);

		// get dart
		test_res = false;
		boolean dart_close = false;
		int darts = 0;
		H.setNrDarts(0);
		MazeGenerator m5 = new MazeGenerator();
		m5.generateMaze();
		D = m5.placeHeroUDrag(H, D, E, P, Y);

		if (m5.getTabrand()[H.getLin()][H.getCol() - 1] == 'Y')
			dart_close = true;
		test = play.moveHeroLeft(m5, H, E, D, Y, P);
		if (H.getnrOfDarts() == darts + 1)
			test_res = true;
		assertEquals(dart_close, test_res);
		D.remove(0);

		// throw dart
		test_res = false;
		dragon_close = false;
		H.setNrDarts(1);
		MazeGenerator m6 = new MazeGenerator();
		m6.generateMaze();
		D = m6.placeHeroUDrag(H, D, E, P, Y);

		if (m6.getTabrand()[H.getLin() - 1][H.getCol()] == 'D')
			dart_close = true;
		test = play.throwDartUp(m6, H, D);

		if (test && D.isEmpty())
			test_res = true;
		assertEquals(dart_close, test_res);

	}

	/**
	 * test everything when the hero moves downwards(sword,shield,dart,dragon
	 * actions...)
	 */
	@Test
	public void heroDown() {
		// hero dies
		MazeGenerator m1 = new MazeGenerator();
		m1.generateMaze();
		D = m1.placeHeroDDrag(H, D, E, P, Y);
		boolean test_res = false, test = false, dragon_close = false;

		if (m1.getTabrand()[H.getLin() + 1][H.getCol()] == 'D')
			dragon_close = true;
		test = play.moveHeroDown(m1, H, E, D, Y, P);
		if (H.isHeroDead())
			test_res = true;
		assertEquals(dragon_close, test_res);
		D.remove(0);

		// dragon dies
		test_res = false;
		dragon_close = false;
		MazeGenerator m2 = new MazeGenerator();
		m2.generateMaze();
		D = m2.placeHeroDDrag(H, D, E, P, Y);

		if (m2.getTabrand()[H.getLin() + 1][H.getCol()] == 'D')
			dragon_close = true;
		H.giveHeroSword();
		test = play.moveHeroDown(m2, H, E, D, Y, P);
		if (D.isEmpty())
			test_res = true;
		assertEquals(dragon_close, test_res);

		// get sword
		test_res = false;
		boolean sword_close = false;
		H.resetSword();
		MazeGenerator m3 = new MazeGenerator();
		m3.generateMaze();
		D = m3.placeHeroDDrag(H, D, E, P, Y);

		if (m3.getTabrand()[H.getLin() - 1][H.getCol()] == 'E')
			sword_close = true;
		test = play.moveHeroUp(m3, H, E, D, Y, P);
		if (H.heroHasSword() && E.getLin() == -1 && E.getCol() == -1)
			test_res = true;
		assertEquals(sword_close, test_res);
		D.remove(0);

		// get shield
		test_res = false;
		boolean shield_close = false;
		H.resetSword();
		MazeGenerator m4 = new MazeGenerator();
		m4.generateMaze();
		D = m4.placeHeroDDrag(H, D, E, P, Y);

		if (m4.getTabrand()[H.getLin()][H.getCol() - 1] == 'P')
			shield_close = true;
		test = play.moveHeroLeft(m4, H, E, D, Y, P);
		if (H.heroHasShield() && P.getLin() == -1 && P.getCol() == -1)
			test_res = true;
		assertEquals(shield_close, test_res);
		D.remove(0);

		// get dart
		test_res = false;
		boolean dart_close = false;
		int darts = 0;
		H.setNrDarts(0);
		MazeGenerator m5 = new MazeGenerator();
		m5.generateMaze();
		D = m5.placeHeroDDrag(H, D, E, P, Y);

		if (m5.getTabrand()[H.getLin()][H.getCol() + 1] == 'Y')
			dart_close = true;
		test = play.moveHeroRight(m5, H, E, D, Y, P);
		if (H.getnrOfDarts() == darts + 1)
			test_res = true;
		assertEquals(dart_close, test_res);
		D.remove(0);

		// throw dart
		test_res = false;
		dragon_close = false;
		H.setNrDarts(1);
		MazeGenerator m6 = new MazeGenerator();
		m6.generateMaze();
		D = m6.placeHeroDDrag(H, D, E, P, Y);

		if (m6.getTabrand()[H.getLin() + 1][H.getCol()] == 'D')
			dart_close = true;
		test = play.throwDartDown(m6, H, D);
		if (test && D.isEmpty())
			test_res = true;
		assertEquals(dart_close, test_res);
	}

	/**
	 * tests if the status and positions of the dragon and sword change when
	 * they are overlaped
	 */
	@Test
	public void DragOverSword() {
		rmaze.generateMaze();
		D = rmaze.placeGameElementsRandDartShield(H, E, 0, Y, P, 8);
		Dragao d = D.get(0);
		boolean test_res = false, test = false;

		test = play.dragonTurn(rmaze, H, E, D, Y, P, false);
		if (E.getStatus() == 'G' && (d.getLin() == E.getLin() && d.getCol() == E.getCol()))
			test_res = true;
		assertEquals(true, test_res);
	}

	/**
	 * tests if the status and positions of the dragon and shield change when
	 * they are overlaped
	 */
	@Test
	public void DragOverShield() {
		rmaze.generateMaze();
		D = rmaze.placeGameElementsRandDartShield(H, E, 0, Y, P, 9);
		Dragao d = D.get(0);
		boolean test_res = true, test = false;

		test = play.dragonTurn(rmaze, H, E, D, Y, P, false);
		if (P.getStatus() == 'N' && (d.getLin() == P.getLin() && d.getCol() == P.getCol()))
			test_res = true;
		assertEquals(true, test_res);

	}

	/**
	 * checks if there is no undesired overlaps of game elements
	 */
	@Test
	public void checkOverlaps() {
		rmaze.generateMaze();
		Dardo y = new Dardo();
		Y.add(y);
		D = rmaze.placeGameElementsRandDartShield(H, E, 1, Y, P, 0);
		Dragao d = D.get(0);
		E.setStatus('G');
		P.setStatus('N');
		Y.get(0).setStatus('M');
		boolean no_overlap = false, test_res = false;

		play.dragonTurn(rmaze, H, E, D, Y, P, false);
		if ((d.getLin() != E.getLin() || d.getCol() != E.getCol())
				&& (d.getLin() != P.getLin() || d.getCol() != P.getCol())
				&& (d.getLin() != Y.get(0).getLin() || d.getCol() != Y.get(0).getCol()))
			no_overlap = true;
		if (E.getStatus() == 'E' && P.getStatus() == 'P' && Y.get(0).getStatus() == 'Y')
			test_res = true;
		assertEquals(no_overlap, test_res);

	}

	/**
	 * tests if dragon really kills a hero close to him
	 */
	@Test
	public void fireBreath() {
		rmaze.generateMaze();
		D = rmaze.placeGameElementsRandDartShield(H, E, 1, Y, P, 3);
		boolean test = false, drag_close = false;

		if (rmaze.getTabrand()[H.getLin() + 1][H.getCol()] == 'D'
				|| rmaze.getTabrand()[H.getLin() - 1][H.getCol()] == 'D'
				|| rmaze.getTabrand()[H.getLin()][H.getCol() + 1] == 'D'
				|| rmaze.getTabrand()[H.getLin()][H.getCol() - 1] == 'D')
			drag_close = true;
		test = play.DragonFireBreath(H, D, rmaze);
		assertEquals(drag_close, test);

	}

	@Test
	public void ElementConstructors() {
		Dardo d = new Dardo(1, 2);
		boolean test = false;
		if (d.getStatus() == 'Y')
			test = true;
		assertEquals(true, test);

		test = false;
		Dragao drag = new Dragao(3, 3);
		if (drag.getStatus() == 'D')
			test = true;
		assertEquals(true, test);

		test = false;
		Escudo P = new Escudo(5, 3);
		if (P.getStatus() == 'P')
			test = true;
		assertEquals(true, test);

		test = false;
		Espada E = new Espada(5, 3);
		if (E.getStatus() == 'E')
			test = true;
		assertEquals(true, test);

	}

	/**
	 * checks if the maze boundaries and exit position are in order
	 * 
	 * @param maze
	 *            a generated maze
	 * @return false if there is something else than X or S on the surrounding
	 *         walls
	 */
	private boolean checkBoundaries(MazeGenerator maze) {
		int countExit = 0;
		int n = maze.getncol();
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				if (i == 0 || j == 0 || i == n - 1 || j == n - 1)
					if (maze.getTabrand()[i][j] == 'S')
						if ((i == 0 || i == n - 1) && (j == 0 || j == n - 1))
							return false;
						else
							countExit++;
					else if (maze.getTabrand()[i][j] != 'X')
						return false;
		return countExit == 1;
	}

	// d) there cannot exist 2x2 (or greater) squares with blanks only
	// e) there cannot exit 2x2 (or greater) squares with blanks in one diagonal
	// and walls in the other
	// d) there cannot exist 3x3 (or greater) squares with walls only
	/**
	 * see if there is a square of blank spaces in the maze
	 * 
	 * @param maze
	 *            generated
	 * @param square
	 *            of blank spaces
	 * @return true if there is at a square of ' ' which cannot happen
	 */
	private boolean hasSquare(MazeGenerator maze, char[][] square) {
		char[][] m = maze.getTabrand();
		for (int i = 0; i < m.length - square.length; i++)
			for (int j = 0; j < m.length - square.length; j++) {
				boolean match = true;
				for (int x = 0; x < square.length; x++)
					for (int y = 0; y < square.length; y++) {
						if (m[i + x][j + y] != square[x][y])
							match = false;
					}
				if (match)
					return true;
			}
		return false;
	}

	// c) there must exist a path between any blank cell and the maze exit
	/**
	 * Sees if you can reach the exit with the hero
	 * 
	 * @param maze
	 *            generated
	 * @return true if you can end the game
	 */
	private boolean checkExitReachable(MazeGenerator maze) {
		boolean brk = false;
		int i = 0, j = 0;
		outer_loop: for (i = 0; i < maze.getncol(); i++)
			for (j = 0; j < maze.getnlin(); j++)
				if (maze.getTabrand()[j][i] == 'S')
					break outer_loop;

		char[][] m = deepClone(maze.getTabrand());
		visit(m, j, i);

		for (int a = 0; a < m.length; a++)
			for (int b = 0; b < m.length; b++)
				if (m[a][b] != 'X' && m[a][b] != 'V')
					return false;

		return true;
	}

	/**
	 * marks a cell as visited (V) and proceeds recursively to its neighbors
	 * 
	 * @param m
	 *            maze
	 * @param i
	 *            x position
	 * @param j
	 *            y position
	 */
	private void visit(char[][] m, int i, int j) {
		if (i < 0 || i >= m.length || j < 0 || j >= m.length)
			return;
		if (m[i][j] == 'X' || m[i][j] == 'V')
			return;
		m[i][j] = 'V';
		visit(m, i - 1, j);
		visit(m, i + 1, j);
		visit(m, i, j - 1);
		visit(m, i, j + 1);
	}

	/**
	 * Gets a deep clone of a char matrix.
	 * 
	 * @param m
	 *            maze
	 * @return
	 */
	private char[][] deepClone(char[][] m) {
		char[][] c = m.clone();
		for (int i = 0; i < m.length; i++)
			c[i] = m[i].clone();
		return c;
	}

	// Checks if all the arguments (in the variable arguments list) are not null
	// and distinct
	/**
	 * Checks if all the arguments (in the variable arguments list) are not null
	 * and distinct
	 * 
	 * @param args
	 *            arguments
	 * @return false if some of the argument are null
	 */
	private <T> boolean notNullAndDistinct(T... args) {
		for (int i = 0; i < args.length - 1; i++)
			for (int j = i + 1; j < args.length; j++)
				if (args[i] == null || args[j] == null
						|| args[i].equals(args[j]))
					return false;
		return true;
	}

	/**
	 * tests the random maze generator
	 * 
	 * @throws Exception
	 */
	@Test
	public void testRandomMazeGenerator() throws Exception {
		int numMazes = 1000;
		int maxSize = 101; // can change to any odd number >= 5

		// MazeBuilder builder = new MazeBuilder();
		char[][] badWalls = { { 'X', 'X', 'X' }, { 'X', 'X', 'X' },
				{ 'X', 'X', 'X' } };
		char[][] badSpaces = { { ' ', ' ' }, { ' ', ' ' } };
		char[][] badDiag1 = { { 'X', ' ' }, { ' ', 'X' } };
		char[][] badDiag2 = { { ' ', 'X' }, { 'X', ' ' } };
		Random rand = new Random();
		for (int i = 0; i < numMazes; i++) {
			int size = maxSize == 5 ? 5 : 5 + 2 * rand
					.nextInt((maxSize - 5) / 2);
			// Maze m = builder.buildMaze(size);

			MazeGenerator m = new MazeGenerator();
			m.generateMaze();

			assertTrue("Invalid maze boundaries in maze:\n" + m,
					checkBoundaries(m));
			assertTrue("Maze exit not reachable in maze:\n" + m,
					checkExitReachable(m));
			assertNotNull("Invalid walls in maze:\n" + m,
					!hasSquare(m, badWalls));
			assertNotNull("Invalid spaces in maze:\n" + m,
					!hasSquare(m, badSpaces));
			assertNotNull("Invalid diagonals in maze:\n" + m,
					!hasSquare(m, badDiag1));
			assertNotNull("Invalid diagonals in maze:\n" + m,
					!hasSquare(m, badDiag2));
			assertTrue("Missing or overlapping objects in maze:\n" + m,
					notNullAndDistinct(/* m.getExitPosition(), */H, D, E));
		}
	}
}
