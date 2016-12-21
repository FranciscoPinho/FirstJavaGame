package maze.gui;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Frame;

import javax.swing.SwingConstants;
import javax.swing.JSpinner;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;

import javax.swing.SpinnerNumberModel;
import javax.swing.JList;
import javax.swing.JComboBox;

import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;

import javax.swing.JRadioButton;
import javax.swing.JSeparator;

import java.awt.Color;

import javax.swing.JButton;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Dimension;

public class ConfigPanel extends JDialog {
	static int tempkey;

	public ConfigPanel() {
		setSize(new Dimension(475, 300));
		setVisible(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("Configurations");
		getContentPane().setLayout(null);

		JLabel mazeOptions = new JLabel(" Maze Options:");
		mazeOptions.setFont(new Font("Tahoma", Font.BOLD, 12));
		mazeOptions.setBounds(0, 11, 434, 14);
		getContentPane().add(mazeOptions);

		JLabel mazeWidth = new JLabel("  Width:");
		mazeWidth.setBounds(0, 36, 46, 14);
		getContentPane().add(mazeWidth);

		JLabel lblHeight = new JLabel("Height:");
		lblHeight.setBounds(78, 36, 46, 14);
		getContentPane().add(lblHeight);

		JSpinner spinner_width = new JSpinner();
		spinner_width.setModel(new SpinnerNumberModel(GameGUI.nrcol,
				new Integer(1), null, new Integer(2)));
		spinner_width.setBounds(40, 33, 29, 20);
		getContentPane().add(spinner_width);

		JSpinner spinner_height = new JSpinner();
		spinner_height.setModel(new SpinnerNumberModel(GameGUI.nrlin,
				new Integer(1), null, new Integer(2)));
		spinner_height.setBounds(115, 33, 29, 20);
		getContentPane().add(spinner_height);

		JComboBox<String> DragonType = new JComboBox();
		DragonType.addItem("Moving");
		DragonType.addItem("Sleepy");
		DragonType.addItem("Static");
		if(GameGUI.gameState==GameGUI.GameState.MOVINGDRAG)
			DragonType.setSelectedIndex(0);
		if(GameGUI.gameState==GameGUI.GameState.SLEEPYDRAG)
			DragonType.setSelectedIndex(1);
		if(GameGUI.gameState==GameGUI.GameState.STATICDRAG)
			DragonType.setSelectedIndex(2);
		DragonType.setMaximumRowCount(3);
		DragonType.setBounds(238, 22, 70, 20);
		getContentPane().add(DragonType);

		JLabel lblDragonType = new JLabel("Dragon Type");
		lblDragonType.setBounds(152, 36, 76, 14);
		getContentPane().add(lblDragonType);

		JRadioButton rdbtnFireBreath = new JRadioButton("Fire");
		rdbtnFireBreath.setSelected(GameGUI.fire);
		rdbtnFireBreath.setBounds(238, 48, 84, 23);
		getContentPane().add(rdbtnFireBreath);

		JLabel lblNumberOfDragons = new JLabel("Number of Dragons");
		lblNumberOfDragons.setBounds(311, 36, 111, 14);
		getContentPane().add(lblNumberOfDragons);

		JSpinner nrdrag = new JSpinner();
		nrdrag.setModel(new SpinnerNumberModel(GameGUI.nrDrag, 0,
				(GameGUI.nrcol * GameGUI.nrlin) / 10, 1));
		nrdrag.setBounds(422, 36, 29, 20);
		getContentPane().add(nrdrag);

		JLabel lblKeyboardControls = new JLabel(" Keyboard Controls:");
		lblKeyboardControls.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblKeyboardControls.setBounds(0, 115, 142, 14);
		getContentPane().add(lblKeyboardControls);

		JButton btnMoveRight = new JButton("Move Right");
		btnMoveRight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				KeyPrompt changekey = new KeyPrompt();
				GameGUI.right = tempkey;
			}

		});
		btnMoveRight.setBounds(10, 140, 101, 23);
		getContentPane().add(btnMoveRight);

		JButton btnMoveLeft = new JButton("Move Left");
		btnMoveLeft.setBounds(10, 177, 101, 23);
		btnMoveLeft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				KeyPrompt changekey = new KeyPrompt();
				GameGUI.left = tempkey;
			}

		});
		getContentPane().add(btnMoveLeft);

		JButton btnMoveUp = new JButton("Move Up");
		btnMoveUp.setBounds(150, 140, 106, 23);
		btnMoveUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				KeyPrompt changekey = new KeyPrompt();
				GameGUI.up = tempkey;
			}

		});
		getContentPane().add(btnMoveUp);

		JButton btnMoveDown = new JButton("Move Down");
		btnMoveDown.setBounds(150, 177, 106, 23);
		btnMoveDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				KeyPrompt changekey = new KeyPrompt();
				GameGUI.down = tempkey;
			}

		});
		getContentPane().add(btnMoveDown);

		JButton btnThrowDart = new JButton("Throw Dart");
		btnThrowDart.setBounds(292, 155, 120, 23);
		btnThrowDart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				KeyPrompt changekey = new KeyPrompt();
				GameGUI.throwdart = tempkey;
			}

		});
		getContentPane().add(btnThrowDart);

		JButton btnSaveChanges = new JButton("Save Changes");
		btnSaveChanges.setBounds(78, 227, 125, 23);
		btnSaveChanges.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				GameGUI.fire = rdbtnFireBreath.isSelected();
				GameGUI.nrlin = (Integer) spinner_height.getValue();
				GameGUI.nrcol = (Integer) spinner_width.getValue();
				GameGUI.nrDrag = (Integer) nrdrag.getValue();
				if (DragonType.getSelectedIndex() == 0)
					GameGUI.gameState = GameGUI.GameState.MOVINGDRAG;
				if (DragonType.getSelectedIndex() == 1)
					GameGUI.gameState = GameGUI.GameState.SLEEPYDRAG;
				if (DragonType.getSelectedIndex() == 2)
					GameGUI.gameState = GameGUI.GameState.STATICDRAG;
			}

		});
		getContentPane().add(btnSaveChanges);

		JButton btnExit = new JButton("Done");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnExit.setBounds(219, 227, 114, 23);
		getContentPane().add(btnExit);
	}

}
