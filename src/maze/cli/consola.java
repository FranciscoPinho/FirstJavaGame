package maze.cli;

import java.util.Random;
import maze.logic.GameLogic;
import java.util.Scanner;
import java.util.Vector;
import maze.logic.*;

public class consola {
	GameLogic game = new GameLogic();

	public void mostraTabRand(MazeGenerator rmaze) {
		for (int i = 0; i < rmaze.getnlin(); i++) {

			for (int j = 0; j < rmaze.getncol(); j++) {

				System.out.print(rmaze.getTabrand()[i][j]);
			}
			System.out.println();
		}
	}

	public char teclaValida() {
		char ch = 0;
		Scanner sc = new Scanner(System.in);

		try {
			ch = sc.findInLine(".").charAt(0);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ch;
	}

	// play functions
	public boolean throwDart(MazeGenerator rmaze, Heroi H, Vector<Dragao> D) {
		System.out
				.println("In which direction do you wish to send dart?(wasd)");

		switch (teclaValida()) {
		case 'w':
			game.throwDartUp(rmaze, H, D);
			break;
		case 's':
			game.throwDartDown(rmaze, H, D);
			break;
		case 'a':
			game.throwDartLeft(rmaze, H, D);
			break;
		case 'd':
			game.throwDartRight(rmaze, H, D);
			break;
		default:
			System.out
					.println("Wrong input, use W for North, S for South, A for West and D for East throw");
			throwDart(rmaze, H, D);
			break;
		}

		return false;
	}

	public boolean jogarDragaoRandDartShieldEstatico(MazeGenerator rmaze,
			Heroi H, Espada E, Vector<Dragao> D, Vector<Dardo> Y, Escudo P) {
		switch (teclaValida()) {
		case 'd':
			if (game.moveHeroRight(rmaze, H, E, D, Y, P)) {
				mostraTabRand(rmaze);
				return true;
			}

			break;
		case 'a':
			if (game.moveHeroLeft(rmaze, H, E, D, Y, P)) {
				mostraTabRand(rmaze);
				return true;
			}

			break;
		case 'w':
			if (game.moveHeroUp(rmaze, H, E, D, Y, P)) {
				mostraTabRand(rmaze);
				return true;
			}
			break;
		case 's':
			if (game.moveHeroDown(rmaze, H, E, D, Y, P)) {
				mostraTabRand(rmaze);
				return true;
			}

			break;
		case 't':
			if (H.getnrOfDarts() > 0) {
				throwDart(rmaze, H, D);
			} else {
				System.out.println("No darts to throw ");
				mostraTabRand(rmaze);
				return false;
			}
			break;
		default:
			return false;
		}

		if (game.DragonFireBreath(H, D, rmaze) == true) {
			mostraTabRand(rmaze);
			return true;
		}
		mostraTabRand(rmaze);

		return false;
	}

	public boolean jogarDragaoRandDartShieldMov(MazeGenerator rmaze, Heroi H,
			Espada E, Vector<Dragao> D, Vector<Dardo> Y, Escudo P) {
		switch (teclaValida()) {
		case 'd':
			if (game.moveHeroRight(rmaze, H, E, D, Y, P)) {
				mostraTabRand(rmaze);
				return true;
			}

			break;
		case 'a':
			if (game.moveHeroLeft(rmaze, H, E, D, Y, P)) {
				mostraTabRand(rmaze);
				return true;
			}

			break;
		case 'w':
			if (game.moveHeroUp(rmaze, H, E, D, Y, P)) {
				mostraTabRand(rmaze);
				return true;
			}
			break;
		case 's':
			if (game.moveHeroDown(rmaze, H, E, D, Y, P)) {
				mostraTabRand(rmaze);
				return true;
			}

			break;
		case 't':
			if (H.getnrOfDarts() > 0) {
				throwDart(rmaze, H, D);
			} else {
				System.out.println("No darts to throw ");
				mostraTabRand(rmaze);
				return false;
			}
			break;
		default:
			return false;
		}
		if (game.dragonTurn(rmaze, H, E, D, Y, P,true)) {
			mostraTabRand(rmaze);
			return true;
		}
		mostraTabRand(rmaze);
		return false;
	}

	public boolean jogarDragaoRandDartShieldSleepy(MazeGenerator rmaze,
			Heroi H, Espada E, Vector<Dragao> D, Vector<Dardo> Y, Escudo P) {

		boolean gameover = false;

		while (!gameover) {
			if (!D.isEmpty())
				game.updateDragSleepRand(D, rmaze);
			gameover = jogarDragaoRandDartShieldMov(rmaze, H, E, D, Y, P);
		}

		return true;
	}

	// main function
	public static void main(String[] args){
		Heroi H = new Heroi();
		Espada E = new Espada();
		Dragao D = new Dragao();
		Escudo P = new Escudo();
		consola C = new consola();
		MazeGenerator rmaze = new MazeGenerator();
		Vector<Dardo> dardos = new Vector<Dardo>();
		System.out.println("==Jogo do Labirinto==\n");
		System.out.println("Game modes: ");
		System.out
				.println("Moving Dragon(M/m), Static Dragon(N/n) or Sleepy Dragon(S/s)");
		Scanner sc = new Scanner(System.in);
		char command = sc.findInLine(".").charAt(0);
		while (command != 'm' && command != 'M' && command != 's'
				&& command != 'S' && command != 'n' && command != 'N') {
			command = sc.findInLine(".").charAt(0);
		}
		sc.reset();
		int nrDragons;
		if (command == 'n' || command == 'N') {
			System.out
					.println("How many dragons(max is 5 for playability sake, min is 1)?");

			while (!sc.hasNextInt()){
				sc.reset();
				sc.nextLine();}
			while (sc.nextInt() > 5 || sc.nextInt() < 1){
				sc.reset();
				sc.nextInt();
			}
			nrDragons = sc.nextInt();
			rmaze.generateMaze();
			Vector<Dragao> Dragoes = rmaze.placeGameElementsRandDartShield(H,
					E, nrDragons, dardos, P,0);
			C.mostraTabRand(rmaze);
			while (!C.jogarDragaoRandDartShieldEstatico(rmaze, H, E, Dragoes,
					dardos, P))
				;
			return;
		}
		if (command == 'S' || command == 's') {
			System.out
					.println("How many dragons(max is 5 for playability sake, min is 1)?");

			while (!sc.hasNextInt())
				sc.nextLine();
			while (sc.nextInt() > 5 || sc.nextInt() < 1)
				sc.nextInt();

			nrDragons = sc.nextInt();
			rmaze.generateMaze();

			Vector<Dragao> Dragoes = rmaze.placeGameElementsRandDartShield(H,
					E, nrDragons, dardos, P,0);
			C.mostraTabRand(rmaze);
			while (!C.jogarDragaoRandDartShieldSleepy(rmaze, H, E, Dragoes,
					dardos, P))
				;
			return;
		} else {
			System.out
					.println("How many dragons(max is 5 for playability sake, min is 1)?");

			while (!sc.hasNextInt())
				sc.nextLine();
			while (sc.nextInt() > 5 || sc.nextInt() < 1)
				sc.nextInt();

			nrDragons = sc.nextInt();
			rmaze.generateMaze();

			Vector<Dragao> Dragoes = rmaze.placeGameElementsRandDartShield(H,
					E, nrDragons, dardos, P,0);
			C.mostraTabRand(rmaze);
			while (!C.jogarDragaoRandDartShieldMov(rmaze, H, E, Dragoes,
					dardos, P))
				;
			return;
		}
	}

}