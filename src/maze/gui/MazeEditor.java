package maze.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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

import maze.gui.GameGUI.GameState;
import maze.logic.*;

import javax.imageio.ImageIO;
import javax.swing.*;

public class MazeEditor extends JPanel implements KeyListener, MouseListener,Serializable {
	int x1 = 1, y1 = 1;
	BufferedImage hero, dragon, wall, dart, sword, shield, exit, ashes,
			dragonsleep, tile;
	Vector<Dardo> dardos;
	Vector<Dragao> dragoes;
	MazeGenerator rmaze;
	transient GameLogic game;
	Heroi H;
	Espada E;
	Escudo P;

	static enum EditState {
		HERO, WALL, DRAGON, DART, TILE, EXIT, SWORD, SHIELD
	}

	static EditState editingState = EditState.TILE;
	static int nrlin = 15;
	static int nrcol = 15;

	public Vector<Dardo> getDardos()  {
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

	// Construtor, regista listeners de eventos do rato e teclado
	public MazeEditor() { 
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
			tile = ImageIO.read(new File("tile.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		dardos= new Vector<Dardo>();
		dragoes = new Vector<Dragao>();
		rmaze = new MazeGenerator(nrlin, nrcol, true);
	}
	public MazeEditor(MazeGenerator maze,Heroi H,Espada E,Escudo P,Vector<Dragao> dragoes,Vector<Dardo> dardos) { 
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
			tile = ImageIO.read(new File("tile.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.H = H;
		this.E = E;
		this.P = P;
		this.dragoes = dragoes;
		this.dardos = dardos;
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

	@Override
	public void mouseClicked(MouseEvent arg0) {
		int poslin = Math.floorDiv(MouseInfo.getPointerInfo().getLocation().y+((this.getHeight() / rmaze.getnlin())/2),this.getHeight() / rmaze.getnlin())-1;
		int poscol = Math.floorDiv(MouseInfo.getPointerInfo().getLocation().x,this.getWidth()/rmaze.getncol());
		switch (editingState) {
		case TILE:
			rmaze.setPosition(poslin, poscol, ' ');
			break;
		case WALL:
			rmaze.setPosition(poslin, poscol, 'X');
			break;
		case EXIT:
			clearOneTimeElementExit();
			rmaze.setPosition(poslin, poscol, 'S');
			break;
		case HERO:
			clearOneTimeElementHero();
			rmaze.setPosition(poslin, poscol, 'H');
			break;
		case DRAGON:
			rmaze.setPosition(poslin, poscol, 'D');
			break;
		case DART:
			rmaze.setPosition(poslin, poscol, 'Y');
			break;
		case SWORD:
			clearOneTimeElementSword();
			rmaze.setPosition(poslin, poscol, 'E');
			break;
		case SHIELD:
			clearOneTimeElementShield();
			rmaze.setPosition(poslin, poscol, 'P');
			break;
		default:
			break;
		}
		repaint();
	}
	public void clearOneTimeElementHero(){
		for(int l=0; l<rmaze.getnlin(); l++){
			for(int c=0; c<rmaze.getncol(); c++){
				if(rmaze.getTabrand()[l][c]=='H')
					rmaze.setPosition(l,c,' ');
			}
		}
	}
	public void clearOneTimeElementExit(){
		for(int l=0; l<rmaze.getnlin(); l++){
			for(int c=0; c<rmaze.getncol(); c++){
				if(rmaze.getTabrand()[l][c]=='S')
				rmaze.setPosition(l,c,' ');
			}
		}
	}
	public void clearOneTimeElementShield(){
		for(int l=0; l<rmaze.getnlin(); l++){
			for(int c=0; c<rmaze.getncol(); c++){
				if(rmaze.getTabrand()[l][c]=='P')
				rmaze.setPosition(l,c,' ');
			}
		}
	}
	public void clearOneTimeElementSword(){
		for(int l=0; l<rmaze.getnlin(); l++){
			for(int c=0; c<rmaze.getncol(); c++){
				if(rmaze.getTabrand()[l][c]=='E')
				rmaze.setPosition(l,c,' ');
			}
		}
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
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}
	
}
