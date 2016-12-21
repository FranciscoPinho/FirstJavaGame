package maze.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.plaf.metal.MetalBorders.Flush3DBorder;

import maze.gui.GameGUI.GameState;
import maze.logic.Dardo;
import maze.logic.Dragao;
import maze.logic.Escudo;
import maze.logic.Espada;
import maze.logic.Heroi;
import maze.logic.MazeGenerator;

public class GameGUIFrame extends JFrame {
	JPanel buttonPanel;
	GameGUI panel;
	JButton newgame;
	JButton savegame;
	JButton loadgame;
	JButton Menu;
	JButton exit;
	JButton config;

	public void initComponents() { 
		setTitle("Maze Explorer Alpha Turbo Fail");
		buttonPanel = new JPanel();
		panel = new GameGUI();
		Menu = new JButton("Main Menu");
		newgame = new JButton("New Game");
		savegame = new JButton("Save Game");
		loadgame = new JButton("Load Game");
		exit = new JButton("Exit");
		config = new JButton("Config");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(1000, 1000));
		setLayout(new BorderLayout());
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		buttonPanel.add(newgame);
		buttonPanel.add(savegame);
		buttonPanel.add(loadgame);
		buttonPanel.add(config);
		buttonPanel.add(Menu);
		buttonPanel.add(exit);
		getContentPane().add(panel);
		add(buttonPanel, BorderLayout.SOUTH);
		pack();
		setVisible(true);
		panel.setFocusable(true);
		panel.requestFocus(); // para receber eventos do teclado
	}

	public GameGUIFrame() { 
		initComponents();

		Menu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int n = JOptionPane.showConfirmDialog(GameGUIFrame.this,
						"Go to Main Menu?", "Main Menu",
						JOptionPane.YES_NO_OPTION);
				if (n == 0) {
					setVisible(false);
					try {
						JFrame mainmenu = new MainMenu();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					dispose();
				}
			}
		});

		newgame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int n = JOptionPane.showConfirmDialog(GameGUIFrame.this,
						"Create New Game?", "New Game",
						JOptionPane.YES_NO_OPTION);
				if (n == 0) {
					panel = new GameGUI();
					getContentPane().removeAll();
					add(buttonPanel, BorderLayout.SOUTH);
					getContentPane().add(panel);
					getContentPane().revalidate();
					getContentPane().repaint();
					panel.setFocusable(true);
					panel.requestFocus(); // para receber eventos do
											// teclado
				}
			}
		});
		loadgame.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				Object[] options = { "Saved Maze", "Custom Maze", "Cancel" };
				int n = JOptionPane
						.showOptionDialog(GameGUIFrame.this,
								"Choose the maze type", "Choose Wisely",
								JOptionPane.YES_NO_OPTION,
								JOptionPane.QUESTION_MESSAGE, null, options,
								options[0]);
				if (n == 0) {
					final JFileChooser fc = new JFileChooser("SavedMazes");
					int returnVal1 = fc.showOpenDialog(fc);
					if (returnVal1 == JFileChooser.APPROVE_OPTION) {
						File file = fc.getSelectedFile();
						// This is where a real application would open the file.
						load_game(file);
						getContentPane().removeAll();
						add(buttonPanel, BorderLayout.SOUTH);
						getContentPane().add(panel);
						getContentPane().revalidate();
						getContentPane().repaint();
						panel.setFocusable(true);
						panel.requestFocus(); // para receber eventos do
												// teclado
					}
				}
				if (n == 1) {
					setVisible(false);
					final JFileChooser fc = new JFileChooser("CustomMazes");
					int returnVal1 = fc.showOpenDialog(fc);
					if (returnVal1 == JFileChooser.APPROVE_OPTION) {
						File file = fc.getSelectedFile();
						// This is where a real application would open the file.
						GameGUIFrame f = new GameGUIFrame();
						f.getContentPane().removeAll();
						f.panel=load_custom(file);
						f.add(f.buttonPanel, BorderLayout.SOUTH);
						f.getContentPane().add(panel);
						f.getContentPane().revalidate();
						f.getContentPane().repaint();
						f.panel.setFocusable(true);
						f.panel.requestFocus();
						dispose();
					}
				}
				if (n == 2) {
					JOptionPane.showMessageDialog(GameGUIFrame.this,
							"Loading cancelled");
					panel.requestFocus(); // para receber eventos do
											// teclado
				}

			}
		});

		savegame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final JFileChooser fc = new JFileChooser("SavedMazes");
				int retrival = fc.showSaveDialog(null);
				if (retrival == JFileChooser.APPROVE_OPTION) {
					try {
						save_game(fc.getSelectedFile());
						panel.requestFocus();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(GameGUIFrame.this,
							"Cancelled saving operation");
					panel.requestFocus(); // para receber eventos do
											// teclado
				}
			}
		});
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int n = JOptionPane.showConfirmDialog(GameGUIFrame.this,
						"Close the application?", "Exit",
						JOptionPane.YES_NO_OPTION);
				if (n == 0) {
					setVisible(false);
					System.exit(0);
				}
			}
		});
		config.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ConfigPanel cfg = new ConfigPanel();
				panel.setFocusable(true);
				panel.requestFocus(); // para receber eventos do
										// teclado
			}
		});
	}

	public void save_game(File file) {

		try {

			// FileOutputStream fout = new FileOutputStream("SavedMazes\\" +
			// filename + ".dat");
			FileOutputStream fout = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(panel.getHero());
			oos.writeObject(panel.getDragoes());
			oos.writeObject(panel.getSword());
			oos.writeObject(panel.getShield());
			oos.writeObject(panel.getDardos());
			oos.writeObject(panel.getMaze());
			oos.writeObject(panel.getMode());
			oos.writeObject(panel.getFire());
			oos.close();
			fout.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void load_game(File file) {
		Heroi H = null;
		Vector<Dragao> dragoes = null;
		Espada E = null;
		Escudo P = null;
		Vector<Dardo> dardos = null;
		MazeGenerator maze = null;
		GameState mode = null;
		boolean fire = false;

		FileInputStream fin = null;
		try {
			// fin = new FileInputStream("SavedMazes//" + filename + ".dat");
			fin = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			ObjectInputStream in = new ObjectInputStream(fin);
			H = (Heroi) in.readObject();
			dragoes = (Vector<Dragao>) in.readObject();
			E = (Espada) in.readObject();
			P = (Escudo) in.readObject();
			dardos = (Vector<Dardo>) in.readObject();
			maze = (MazeGenerator) in.readObject();
			mode = (GameState) in.readObject();
			fire = (boolean) in.readObject();
			in.close();
			fin.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		GameGUI game = new GameGUI(maze, H, E, P, dragoes, dardos, mode, fire);
		this.panel = game;
	}
	public GameGUI load_custom(File file) { 
		Heroi H = null;
		Vector<Dragao> dragoes = null;
		Espada E = null;
		Escudo P = null;
		Vector<Dardo> dardos = null;
		MazeGenerator maze = null;
		GameState mode = GameGUI.gameState;
		boolean fire = GameGUI.fire;

		FileInputStream fin = null;
		try {
			// fin = new FileInputStream("SavedMazes//" + filename + ".dat");
			fin = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			ObjectInputStream in = new ObjectInputStream(fin);
			H = (Heroi) in.readObject();
			dragoes = (Vector<Dragao>) in.readObject();
			E = (Espada) in.readObject();
			P = (Escudo) in.readObject();
			dardos = (Vector<Dardo>) in.readObject();
			maze = (MazeGenerator) in.readObject();
			in.close();
			fin.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		GameGUI game = new GameGUI(maze, H, E, P, dragoes, dardos,
				mode, fire);
		game.setFocusable(true);
		return game;
	}
	
}
