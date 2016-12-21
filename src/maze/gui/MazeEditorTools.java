package maze.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JList;
import javax.swing.JComboBox;
import javax.swing.SpinnerNumberModel;

import maze.logic.MazeGenerator;

public class MazeEditorTools extends JPanel {
	MazeEditorFrame frame;

	public MazeEditorTools(MazeEditorFrame f) {
		frame = f;
		setLayout(new FlowLayout(FlowLayout.CENTER));
		JButton SaveMaze = new JButton("Save Maze");
		SaveMaze.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				final JFileChooser fc = new JFileChooser("CustomMazes");
				int retrival = fc.showSaveDialog(null);
				if (retrival == JFileChooser.APPROVE_OPTION) {
					try {
						f.save_maze(fc.getSelectedFile());
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(f,
							"Cancelled saving operation");
				}
			}

		});
		
		JButton LoadMaze = new JButton("Load Maze");
		LoadMaze.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				final JFileChooser fc = new JFileChooser("CustomMazes");
				int returnVal1 = fc.showOpenDialog(fc);
				if (returnVal1 == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					// This is where a real application would open the file.
					f.load_maze(file);
					f.getContentPane().removeAll();
					f.add(MazeEditorTools.this, BorderLayout.SOUTH);
					f.getContentPane().add(f.editor);
					f.getContentPane().revalidate();
					f.getContentPane().repaint();
					f.editor.setFocusable(true);

				} else {
					JOptionPane.showMessageDialog(f, "Loading cancelled");
				}
			}

		});
		JButton Menu = new JButton("Main Menu");
		Menu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int n = JOptionPane.showConfirmDialog(f, "Go to Main Menu?",
						"Main Menu", JOptionPane.YES_NO_OPTION);
				if (n == 0) {
					f.setVisible(false);
					try {
						JFrame mainmenu = new MainMenu();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					f.dispose();
				}
			}
		});
	
		JButton Exit = new JButton("Exit");
		Exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int n = JOptionPane.showConfirmDialog(f,
						"Close the application?", "Exit",
						JOptionPane.YES_NO_OPTION);
				if (n == 0) {
					System.exit(0);
				}
			}

		});

		JLabel CurrentToollbl = new JLabel("Current Tool:");
		add(CurrentToollbl);

		JComboBox<String> Element = new JComboBox();
		Element.setMaximumRowCount(8);
		Element.addItem("Floor Tile");
		Element.addItem("Wall");
		Element.addItem("Exit");
		Element.addItem("Hero");
		Element.addItem("Dragon");
		Element.addItem("Dart");
		Element.addItem("Shield");
		Element.addItem("Sword");
		Element.setSelectedIndex(0);
		Element.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switch (Element.getSelectedIndex()) {
				case 0:
					MazeEditor.editingState = MazeEditor.EditState.TILE;
					break;
				case 1:
					MazeEditor.editingState = MazeEditor.EditState.WALL;
					break;
				case 2:
					MazeEditor.editingState = MazeEditor.EditState.EXIT;
					break;
				case 3:
					MazeEditor.editingState = MazeEditor.EditState.HERO;
					break;
				case 4:
					MazeEditor.editingState = MazeEditor.EditState.DRAGON;
					break;
				case 5:
					MazeEditor.editingState = MazeEditor.EditState.DART;
					break;
				case 6:
					MazeEditor.editingState = MazeEditor.EditState.SHIELD;
					break;
				case 7:
					MazeEditor.editingState = MazeEditor.EditState.SWORD;
					break;
				default:
					break;
				}
			}
		});
		add(Element);

		JLabel Widthlbl = new JLabel("Width:");
		add(Widthlbl);

		JSpinner spinner_width = new JSpinner();
		spinner_width.setModel(new SpinnerNumberModel(MazeEditor.nrcol,
				new Integer(1), null, new Integer(1)));
		add(spinner_width);

		JLabel Heightlbl = new JLabel("Height:");
		add(Heightlbl);

		JSpinner spinner_height = new JSpinner();
		spinner_height.setModel(new SpinnerNumberModel(MazeEditor.nrcol,
				new Integer(1), null, new Integer(1)));

		add(spinner_height);

		JButton NewMaze = new JButton("New Maze");
		NewMaze.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MazeEditor.nrlin = (Integer) spinner_height.getValue();
				MazeEditor.nrcol = (Integer) spinner_width.getValue();
				f.editor = new MazeEditor();	
				f.getContentPane().removeAll();
				f.add(MazeEditorTools.this, BorderLayout.SOUTH);
				f.getContentPane().add(f.editor);
				f.getContentPane().revalidate();
				f.getContentPane().repaint();
				f.editor.setFocusable(true);
			}

		});
		add(NewMaze);
		add(SaveMaze);
		add(LoadMaze);
		add(Menu);
		add(Exit);
	}
}
