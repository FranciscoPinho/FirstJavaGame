package maze.gui;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Vector;

import maze.logic.*;

import javax.imageio.ImageIO;
import javax.swing.*;

public class GameGUI extends JPanel implements KeyListener, MouseListener,
		Serializable {

	int x1 = 0, y1 = 0;
	BufferedImage hero, dragon, wall, dart, sword, shield, exit, ashes,
			dragonsleep, tile;
	Vector<Dardo> dardos;

	Vector<Dragao> dragoes;
	transient GameLogic game;
	MazeGenerator rmaze;
	Heroi H;
	Espada E;
	Escudo P;
	boolean firstdart;
	static int right = KeyEvent.VK_RIGHT;
	static int up = KeyEvent.VK_UP;
	static int left = KeyEvent.VK_LEFT;
	static int down = KeyEvent.VK_DOWN;
	static int throwdart = KeyEvent.VK_T;
	static int nrDrag = 1;
	static int nrlin = 15;
	static int nrcol = 15;
	static boolean fire = true;

	static enum GameState {
		SLEEPYDRAG, STATICDRAG, MOVINGDRAG
	}

	static GameState gameState = GameState.MOVINGDRAG;
	
	private GameState mode;

	public Vector<Dardo> getDardos() {
		return dardos;
	}

	public Vector<Dragao> getDragoes() {
		return dragoes;
	}

	public MazeGenerator getMaze() {
		return rmaze;
	}

	public Heroi getHero() {
		return H;
	}

	public Espada getSword() {
		return E;
	}

	public Escudo getShield() {
		return P;
	}

	public GameState getMode() {
		return mode;
	}

	public boolean getFire() {
		return fire;
	}

	// Construtor, regista listeners de eventos do rato e teclado
	public GameGUI() { 
		game = new GameLogic();
		mode = gameState;
		this.addKeyListener(this);
		this.addMouseListener(this);
		try {
			hero = ImageIO.read(new File("Heroi.jpg"));
			dragon = ImageIO.read(new File("Dragon.jpg"));
			wall = ImageIO.read(new File("wall.jpg"));
			sword = ImageIO.read(new File("sword.jpg"));
			shield = ImageIO.read(new File("shield.jpg"));
			exit = ImageIO.read(new File("exit.jpg"));
			dart = ImageIO.read(new File("dardo.jpg"));
			ashes = ImageIO.read(new File("ashes.jpg"));
			dragonsleep = ImageIO.read(new File("sleepydrag.jpg"));
			tile = ImageIO.read(new File("tile.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		initGame();
	}

	public GameGUI(MazeGenerator maze, Heroi H, Espada E, Escudo P,
			Vector<Dragao> dragoes, Vector<Dardo> dardos, GameState mode,
			boolean fire) {
		game = new GameLogic();
		this.mode = mode;
		this.addKeyListener(this);
		this.addMouseListener(this);
		try {
			hero = ImageIO.read(new File("Heroi.jpg"));
			dragon = ImageIO.read(new File("Dragon.jpg"));
			wall = ImageIO.read(new File("wall.jpg"));
			sword = ImageIO.read(new File("sword.jpg"));
			shield = ImageIO.read(new File("shield.jpg"));
			exit = ImageIO.read(new File("exit.jpg"));
			dart = ImageIO.read(new File("dardo.jpg"));
			ashes = ImageIO.read(new File("ashes.jpg"));
			dragonsleep = ImageIO.read(new File("sleepydrag.jpg"));
			tile = ImageIO.read(new File("tile.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.H = H;
		this.E = E;
		this.P = P;
		this.dragoes = dragoes;
		this.dardos = dardos;
		this.fire = fire;
		rmaze = maze;
	}

	public void paintComponent(Graphics g) { 
		int imgsizex = this.getWidth() / rmaze.getnlin();
		int imgsizey = this.getHeight() / rmaze.getncol();

		super.paintComponent(g); // limpa fundo ...
		g.setColor(Color.BLUE);

		for (int lin = 0; lin < rmaze.getnlin(); lin++) {
			y1 = lin * imgsizey;
			for (int col = 0; col < rmaze.getncol(); col++) {
				x1 = col * imgsizex;
				switch (this.rmaze.getTabrand()[lin][col]) {
				case 'H':
					g.drawImage(hero, x1, y1, imgsizex, imgsizey, null);
					break;
				case 'D':
					g.drawImage(dragon, x1, y1, imgsizex, imgsizey, null);
					break;
				case 'S':
					g.drawImage(exit, x1, y1, imgsizex, imgsizey, null);
					break;
				case 'X':
					g.drawImage(wall, x1, y1, imgsizex, imgsizey, null);
					break;
				case 'E':
					g.drawImage(sword, x1, y1, imgsizex, imgsizey, null);
					break;
				case 'P':
					g.drawImage(shield, x1, y1, imgsizex, imgsizey, null);
					break;
				case 'Y':
					g.drawImage(dart, x1, y1, imgsizex, imgsizey, null);
					break;
				case 'F':
					g.drawImage(ashes, x1, y1, imgsizex, imgsizey, null);
					break;
				case 'G':
					if (dragoes.get(game.findDrag(dragoes, lin, col)).getSleepRounds() > 0)
						g.drawImage(dragonsleep, x1, y1, imgsizex / 2,
								imgsizey / 2, null);
					else
						g.drawImage(dragon, x1, y1, imgsizex / 2, imgsizey / 2,
								null);
					g.drawImage(sword, x1 + imgsizex / 2, y1 + imgsizey / 2,
							imgsizex / 2, imgsizey / 2, null);
					g.drawImage(tile, x1, y1 + imgsizey / 2,
							imgsizex / 2, imgsizey / 2, null);
					g.drawImage(tile, x1 + imgsizex / 2, y1,
							imgsizex / 2, imgsizey / 2, null);
					break;
				case 'N':
					if (dragoes.get(game.findDrag(dragoes, lin, col)).getSleepRounds() > 0)
						g.drawImage(dragonsleep, x1, y1, imgsizex / 2,
								imgsizey / 2, null);
					else
						g.drawImage(dragon, x1, y1, imgsizex / 2, imgsizey / 2,
								null);
					g.drawImage(shield, x1 + imgsizex / 2, y1 + imgsizey / 2,
							imgsizex / 2, imgsizey / 2, null);
					g.drawImage(tile, x1, y1 + imgsizey / 2,
							imgsizex / 2, imgsizey / 2, null);
					g.drawImage(tile, x1 + imgsizex / 2, y1,
							imgsizex / 2, imgsizey / 2, null);
					break;
				case 'M':
					if (dragoes.get(game.findDrag(dragoes, lin, col)).getSleepRounds() > 0)
						g.drawImage(dragonsleep, x1, y1, imgsizex / 2,
								imgsizey / 2, null);
					else
						g.drawImage(dragon, x1, y1, imgsizex / 2, imgsizey / 2,
								null);
					g.drawImage(dart, x1 + imgsizex / 2, y1 + imgsizey / 2,
							imgsizex / 2, imgsizey / 2, null);
					g.drawImage(tile, x1, y1 + imgsizey / 2,
							imgsizex / 2, imgsizey / 2, null);
					g.drawImage(tile, x1 + imgsizex / 2, y1,
							imgsizex / 2, imgsizey / 2, null);
					break;
				case 'd':
					g.drawImage(dragonsleep, x1, y1, imgsizex, imgsizey, null);
					break;
				case ' ':
					g.drawImage(tile, x1, y1, imgsizex, imgsizey, null);
					break;
				default:
					break;
				}

			}

		}
	}

	public void loseGame(){
		repaint();
		Object[] options = { "Main Menu", "Exit" };
		int n = JOptionPane.showOptionDialog(this, "You Lost!", "Game Over",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
				options, options[0]);
		if (n == 0) {
			initGame();
		}
		if (n == 1) {

			int n2 = JOptionPane
					.showConfirmDialog(this, "Close the application?", "Exit",
							JOptionPane.YES_NO_OPTION);
			if (n2 == 0) {
				System.exit(0);
			}
		}

	}

	public void throwDart() {
		if (H.getnrOfDarts() > 0) {
			Object[] possibleValues = { "Up", "Down", "Left", "Right", "Exit" };
			Object selectedValue = JOptionPane.showInputDialog(null,
					"Choose a direction", "Throw Dart",
					JOptionPane.INFORMATION_MESSAGE, null, possibleValues,
					possibleValues[0]);
			if (selectedValue == "Exit") {
				JOptionPane.getRootFrame().dispose();
			}
			if (selectedValue == "Up") {
				game.throwDartUp(rmaze, H, dragoes);
			}
			if (selectedValue == "Right") {
				game.throwDartRight(rmaze, H, dragoes);
			}
			if (selectedValue == "Left") {
				game.throwDartLeft(rmaze, H, dragoes);
			}
			if (selectedValue == "Down") {
				game.throwDartDown(rmaze, H, dragoes);
			}
			repaint();
		} else {
			JOptionPane.showMessageDialog(this, "No Darts to throw");
		}
	}

	public void winGame() {
		repaint();
		Object[] options = { "New Game", "Exit" };
		int n = JOptionPane.showOptionDialog(this, "You Won!", "Game Over",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
				options, options[0]);
		if (n == 0) {
			initGame();
		}
		if (n == 1) {
			int n2 = JOptionPane
					.showConfirmDialog(this, "Close the application?", "Exit",
							JOptionPane.YES_NO_OPTION);
			if (n2 == 0) {
				System.exit(0);
			}
		}
	}

	public void initGame() {
		H = new Heroi();
		E = new Espada();
		P = new Escudo();
		dardos = new Vector<Dardo>();
		rmaze = new MazeGenerator(nrlin, nrcol, false);
		rmaze.generateMaze();
		dragoes = rmaze
				.placeGameElementsRandDartShield(H, E, nrDrag, dardos, P,0);
		firstdart = true;
	}

	public void foundfirstDart() {
		if (H.getnrOfDarts() == 1 && firstdart == true) {
			JOptionPane
					.showMessageDialog(this,
							"Press Throw Dart key(T by default) or Mouse click to throw darts");
			firstdart = false;
		}
	}

	@Override
	public void keyPressed(KeyEvent e) { 
		if (mode == GameState.MOVINGDRAG) {
			if (left == e.getKeyCode()) {
				if (game.moveHeroLeft(rmaze, H, E, dragoes, dardos, P)) {
					if (H.isHeroDead()) {
						loseGame();
					} else
						winGame();
				}
				if (game.dragonTurn(rmaze, H, E, dragoes, dardos, P, fire)) {
					loseGame();
				}
				repaint();
				foundfirstDart();
			}
			if (right == e.getKeyCode()) {
				if (game.moveHeroRight(rmaze, H, E, dragoes, dardos, P)) {
					if (H.isHeroDead()) {
						loseGame();
					} else
						winGame();
				}
				if (game.dragonTurn(rmaze, H, E, dragoes, dardos, P, fire)) {
					loseGame();
				}
				repaint();
				foundfirstDart();
			}
			if (up == e.getKeyCode()) {
				if (game.moveHeroUp(rmaze, H, E, dragoes, dardos, P)) {
					if (H.isHeroDead()) {
						loseGame();
					} else
						winGame();
				}
				if (game.dragonTurn(rmaze, H, E, dragoes, dardos, P, fire)) {
					loseGame();
				}
				repaint();
				foundfirstDart();
			}
			if (down == e.getKeyCode()) {
				if (game.moveHeroDown(rmaze, H, E, dragoes, dardos, P)) {
					if (H.isHeroDead()) {
						loseGame();
					} else
						winGame();
				}
				if (game.dragonTurn(rmaze, H, E, dragoes, dardos, P, fire)) {
					loseGame();
				}
				repaint();
				foundfirstDart();
			}
			if (throwdart == e.getKeyCode()) {
				throwDart();
			}
		}

		if (this.mode == GameState.STATICDRAG) {
			if (left == e.getKeyCode()) {

				if (game.moveHeroLeft(rmaze, H, E, dragoes, dardos, P)) {
					if (H.isHeroDead()) {
						loseGame();
					} else
						winGame();
				}
				if (fire)
					if (game.DragonFireBreath(H, dragoes, rmaze)) {
						loseGame();
					}
				repaint();
				foundfirstDart();
			}
			if (right == e.getKeyCode()) {
				if (game.moveHeroRight(rmaze, H, E, dragoes, dardos, P)) {
					if (H.isHeroDead()) {
						loseGame();
					} else
						winGame();
				}
				if (fire)
					if (game.DragonFireBreath(H, dragoes, rmaze)) {
						loseGame();
					}
				repaint();
				foundfirstDart();
			}
			if (up == e.getKeyCode()) {

				if (game.moveHeroUp(rmaze, H, E, dragoes, dardos, P)) {
					if (H.isHeroDead()) {
						loseGame();
					} else
						winGame();
				}
				if (fire)
					if (game.DragonFireBreath(H, dragoes, rmaze)) {
						loseGame();
					}
				repaint();
				foundfirstDart();
			}
			if (down == e.getKeyCode()) {

				if (game.moveHeroDown(rmaze, H, E, dragoes, dardos, P)) {
					if (H.isHeroDead()) {
						loseGame();
					} else
						winGame();
				}
				if (fire)
					if (game.DragonFireBreath(H, dragoes, rmaze)) {
						loseGame();
					}
				repaint();
				foundfirstDart();
			}
			if (throwdart == e.getKeyCode()) {
				throwDart();
			}
		}
		if (mode == GameState.SLEEPYDRAG) {
			if (left == e.getKeyCode()) {
				if (!dragoes.isEmpty())
					game.updateDragSleepRand(dragoes, rmaze);

				if (game.moveHeroLeft(rmaze, H, E, dragoes, dardos, P)) {
					if (H.isHeroDead()) {
						loseGame();
					} else
						winGame();
				}
				if (game.dragonTurn(rmaze, H, E, dragoes, dardos, P, fire)) {
					loseGame();
				}
				repaint();
				foundfirstDart();
			}
			if (right == e.getKeyCode()) {
				if (!dragoes.isEmpty())
					game.updateDragSleepRand(dragoes, rmaze);

				if (game.moveHeroRight(rmaze, H, E, dragoes, dardos, P)) {
					if (H.isHeroDead()) {
						loseGame();
					} else
						winGame();
				}
				if (game.dragonTurn(rmaze, H, E, dragoes, dardos, P, fire)) {
					loseGame();
				}
				repaint();
				foundfirstDart();
			}
			if (up == e.getKeyCode()) {
				if (!dragoes.isEmpty())
					game.updateDragSleepRand(dragoes, rmaze);

				if (game.moveHeroUp(rmaze, H, E, dragoes, dardos, P)) {
					if (H.isHeroDead()) {
						loseGame();
					} else
						winGame();
				}
				if (game.dragonTurn(rmaze, H, E, dragoes, dardos, P, fire)) {
					loseGame();
				}
				repaint();
				foundfirstDart();
			}
			if (down == e.getKeyCode()) {
				if (!dragoes.isEmpty())
					game.updateDragSleepRand(dragoes, rmaze);

				if (game.moveHeroDown(rmaze, H, E, dragoes, dardos, P)) {
					if (H.isHeroDead()) {
						loseGame();
					} else
						winGame();
				}
				if (game.dragonTurn(rmaze, H, E, dragoes, dardos, P, fire)) {
					loseGame();
				}
				repaint();
				foundfirstDart();
			}
			if (throwdart == e.getKeyCode()) {
				throwDart();
			}
		}

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		if (H.getnrOfDarts() > 0) {
			int herox = (this.getWidth() / rmaze.getnlin()) * H.getcol();
			int heroy = (this.getHeight() / rmaze.getncol()) * H.getlin();
			if (heroy > MouseInfo.getPointerInfo().getLocation().y
					&& Math.abs(herox
							- MouseInfo.getPointerInfo().getLocation().x) < this
							.getWidth() / rmaze.getncol()) {
				game.throwDartUp(rmaze, H, dragoes);
				repaint();
				JOptionPane.showMessageDialog(this, "Threw dart up");
			}
			if (heroy < MouseInfo.getPointerInfo().getLocation().y
					&& Math.abs(herox
							- MouseInfo.getPointerInfo().getLocation().x) < this
							.getWidth() / rmaze.getncol()) {
				game.throwDartDown(rmaze, H, dragoes);
				repaint();
				JOptionPane.showMessageDialog(this, "Threw dart down");
			}
			if (herox > MouseInfo.getPointerInfo().getLocation().x
					&& Math.abs(heroy
							- MouseInfo.getPointerInfo().getLocation().y) < this
							.getHeight() / rmaze.getnlin()) {
				game.throwDartLeft(rmaze, H, dragoes);
				repaint();
				JOptionPane.showMessageDialog(this, "Threw dart left");
			}
			if (herox < MouseInfo.getPointerInfo().getLocation().x
					&& Math.abs(heroy
							- MouseInfo.getPointerInfo().getLocation().y) < this
							.getHeight() / rmaze.getnlin()) {
				game.throwDartRight(rmaze, H, dragoes);
				repaint();
				JOptionPane.showMessageDialog(this, "Threw dart right");
			}
		} else
			JOptionPane.showMessageDialog(this, "No Darts to throw");
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}