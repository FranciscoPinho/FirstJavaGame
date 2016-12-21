package maze.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;

import maze.gui.GameGUI.GameState;
import maze.logic.Dardo;
import maze.logic.Dragao;
import maze.logic.Escudo;
import maze.logic.Espada;
import maze.logic.Heroi;
import maze.logic.MazeGenerator;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainMenu extends JFrame {

	public MainMenu() throws IOException { 
		getContentPane().setPreferredSize(new Dimension(1000, 1000));
		final BufferedImage image = ImageIO.read(new File("main.jpg"));
		setTitle("Maze Explorer Alpha Turbo Fail");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(new Dimension(1000, 1000));
		JPanel menu = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(),
						null);
			}
		};

		getContentPane().add(menu);
		menu.setLayout(null);

		JButton btnPlayGame = new JButton("Play Game");
		btnPlayGame.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent arg0) { 
				Object[] options = { "Random Maze", "Custom Maze" };
				int n = JOptionPane
						.showOptionDialog(MainMenu.this,
								"Choose the maze type", "Choose Wisely",
								JOptionPane.YES_NO_OPTION,
								JOptionPane.QUESTION_MESSAGE, null, options,
								options[0]);
				if (n == 0) {
					setVisible(false);
					GameGUIFrame f = new GameGUIFrame();
					dispose();
				}
				if (n == 1) {
					setVisible(false);
					dispose();
					final JFileChooser fc = new JFileChooser("CustomMazes");
					int returnVal1 = fc.showOpenDialog(fc);
					if (returnVal1 == JFileChooser.APPROVE_OPTION) {
						File file = fc.getSelectedFile();
						// This is where a real application would open the file.
						GameGUIFrame f = new GameGUIFrame();
						f.getContentPane().removeAll();
						f.add(f.buttonPanel, BorderLayout.SOUTH);
						f.getContentPane().add(f.load_custom(file));
						f.getContentPane().revalidate();
						f.getContentPane().repaint();
						f.panel.setFocusable(true);
						f.panel.requestFocus();
						
					} else {
						JOptionPane.showMessageDialog(MainMenu.this,
								"Loading cancelled");
					}

				}
			}
		});
		btnPlayGame.setBounds(449, 104, 154, 57);
		menu.add(btnPlayGame);

		JButton btnMazeEditor = new JButton("Maze Editor");
		btnMazeEditor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MazeEditorFrame f = new MazeEditorFrame();
				f.getContentPane().removeAll();
				f.add(new MazeEditorTools(f), BorderLayout.SOUTH);
				f.getContentPane().add(f.editor);
				f.getContentPane().revalidate();
				f.getContentPane().repaint();
				setVisible(false);
				dispose();

			}
		});
		btnMazeEditor.setBounds(449, 198, 154, 57);
		menu.add(btnMazeEditor);

		JButton btnQuitGame = new JButton("Quit Game");
		btnQuitGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				System.exit(0);
			}
		});
		btnQuitGame.setBounds(449, 426, 154, 46);
		menu.add(btnQuitGame);

		JButton btnConfigurations = new JButton("Configurations");
		btnConfigurations.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ConfigPanel cfg = new ConfigPanel();
			}
		});
		btnConfigurations.setBounds(449, 287, 154, 57);
		menu.add(btnConfigurations);
		revalidate();
		pack();
		setVisible(true);
	}

	

	public static void main(String[] args) throws IOException { 
		JFrame f = new MainMenu();
	}
}
