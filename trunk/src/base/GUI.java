package base;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.BevelBorder;

import uml_entities.Class;
import uml_entity_components.Attribute;
import uml_entity_components.Visibility;

@SuppressWarnings("serial")
public class GUI extends JFrame {
	
	JMenuBar menubar;
		JMenu mnuFile, mnuEdit;
	JToolBar toolbar;
	JButton addClass;
	JPanel canvas;
	Class test, test2;
	
	public GUI() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(800, 600);
		initGUI();
		setVisible(true);
	}
	
	private void initGUI() {
		mnuFile = new JMenu("File");
		mnuEdit = new JMenu("Edit");
		
		menubar = new JMenuBar();
		
		menubar.add(mnuFile);
		menubar.add(mnuEdit);
		
		addClass = new JButton("Class");
		addClass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = JOptionPane.showInputDialog(canvas, "Class name:");
				canvas.add(new Class(canvas, name));
			}
		});
		
		toolbar = new JToolBar(JToolBar.VERTICAL);
		//toolbar.setBorder(BorderFactory.createEtchedBorder());
		toolbar.setRollover(true);
		toolbar.add(addClass);
		
		canvas = new JPanel();
		canvas.setLayout(null);
		//canvas.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		canvas.setBorder(BorderFactory.createEtchedBorder());
		canvas.setBackground(new Color(225, 235, 240));
		canvas.setOpaque(true);
		
		canvas.add(new Class(canvas, "class 1"));
		canvas.add(new Class(canvas, "class 2"));
		
		Component[] classes = canvas.getComponents();
		
		int i = 100;
		for(Component c: classes) {
			c.setBounds(i, i, c.getPreferredSize().width, c.getPreferredSize().height);
			i += 100;
		}
		
		((Class)(classes[0])).addAttribute(new Attribute(Visibility.PRIVATE, "name", "String", "Oskar"));
		((Class)(classes[0])).addAttribute(new Attribute(Visibility.PRIVATE, "age", "int", "21"));
		
		Container c = getContentPane();
		
		c.setLayout(new BorderLayout());
		
		setJMenuBar(menubar);
		c.add(toolbar, BorderLayout.WEST);
		c.add(canvas, BorderLayout.CENTER);
		
	}

	public static void main(String[] args) {
		new GUI();
	}
}

