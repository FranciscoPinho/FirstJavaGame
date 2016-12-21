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
public class MazeEditorFrame extends JFrame{
	MazeEditor editor;
	MazeEditorTools ToolPanel;
	public MazeEditorFrame() {
		setTitle("Maze Editor");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(1000, 1000));
		getContentPane().setLayout(new BorderLayout());
		
		editor = new MazeEditor();
		getContentPane().add(editor);
		
		ToolPanel = new MazeEditorTools(this);
		add(ToolPanel, BorderLayout.SOUTH);
		pack();
		setVisible(true);
	}
	public void save_maze(File file) {
		for(int l=0; l<editor.rmaze.getnlin();l++){
			for(int c=0; c<editor.rmaze.getncol();c++){
				switch(editor.rmaze.getTabrand()[l][c]){
				case 'H':
					editor.H=new Heroi(l,c);
					break;
				case 'D':
					editor.dragoes.add(new Dragao(l,c));
					break;
				case 'Y':
					editor.dardos.add(new Dardo(l,c));
					break;
				case 'P':
					editor.P=new Escudo(l,c);
					break;
				case 'E':
					editor.E=new Espada(l,c);
					break;
				default:
					break;
				}
			}
		}
		try {

			//FileOutputStream fout = new FileOutputStream("CustomMazes\\" + filename + ".dat");
			FileOutputStream fout = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(editor.H);
			oos.writeObject(editor.dragoes);
			oos.writeObject(editor.E);
			oos.writeObject(editor.P);
			oos.writeObject(editor.dardos);
			oos.writeObject(editor.rmaze);
			oos.close();
			fout.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	public void load_maze(File file) {
		Heroi H = null;
		Vector<Dragao> dragoes = null;
		Espada E = null;
		Escudo P = null;
		Vector<Dardo> dardos = null;
		MazeGenerator maze = null;;

		FileInputStream fin = null;
		try {
			//fin = new FileInputStream("CustomMazes//" + filename + ".dat");
			fin= new FileInputStream(file);
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
		MazeEditor customizemaze = new MazeEditor(maze, H, E, P, dragoes, dardos);
		this.editor = customizemaze;
	}

}
