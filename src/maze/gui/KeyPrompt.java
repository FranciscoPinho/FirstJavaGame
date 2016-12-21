package maze.gui;

import javax.swing.JDialog;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog.ModalExclusionType;
import java.awt.Dialog.ModalityType;
import java.awt.Window.Type;

public class KeyPrompt extends JDialog{
	public KeyPrompt() {
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setType(Type.POPUP);
		setModal(true);	
		getContentPane().setLayout(null);
		
		setTitle("Input Mapper");
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				ConfigPanel.tempkey=arg0.getKeyCode();
				dispose();
			}
		});
		
		
		JLabel lblPressAKey = new JLabel("Press a key");
		lblPressAKey.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblPressAKey.setBounds(144, 41, 97, 14);
		getContentPane().add(lblPressAKey);
		pack();
		setSize(new Dimension(450, 140));
		setVisible(true);
		
	}
}
