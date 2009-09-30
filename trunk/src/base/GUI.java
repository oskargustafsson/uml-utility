package base;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.LinkedList;

import javax.swing.*;
import javax.swing.border.BevelBorder;

import algorithm.Applier;
import algorithm.force.ForceAlgorithm;

import uml_entities.Entity;
import uml_entities.UmlClass;
import uml_entity_components.Attribute;
import uml_entity_components.Visibility;
import uml_entity_connectives.Connective;

@SuppressWarnings("serial")
public class GUI extends JFrame implements MouseListener, MouseMotionListener {

	private static GUI me = null;
	
	private JMenuBar menubar;
	private JMenu mnuFile, mnuEdit;
	private JToolBar toolbar;
	private JButton addClass, addConnective;
	private Canvas canvas;

	private Tool currentTool = Tool.NONE;

	

	private GUI() {
		addMouseListener(this);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(800, 600);
		initGUI();
		setVisible(true);
		new Applier(new ForceAlgorithm(), canvas, 100).start();
	}
	
	public static GUI getInstance() {
		if(me == null) {
			me = new GUI();
		}
		return me;
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
				addClass(JOptionPane.showInputDialog(canvas, "Class name:"));
			}
		});

		addConnective = new JButton("Conn");
		addConnective.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentTool = Tool.DRAW_CONNECTIVE;
			}
		});

		toolbar = new JToolBar(JToolBar.VERTICAL);
		toolbar.setRollover(true);
		toolbar.add(addClass);
		toolbar.add(addConnective);

		canvas = new Canvas();
		canvas.setLayout(null);
		canvas.setBorder(BorderFactory.createEtchedBorder());
		canvas.setBackground(new Color(225, 235, 240));
		canvas.setOpaque(true);

		addClass("class 1");
		addClass("class 2");

		Component[] classes = canvas.getComponents();

		((UmlClass)(classes[0])).addAttribute(new Attribute(Visibility.PRIVATE, "name", "String", "Oskar"));
		((UmlClass)(classes[0])).addAttribute(new Attribute(Visibility.PRIVATE, "age", "int", "21"));

		Container c = getContentPane();

		c.setLayout(new BorderLayout());

		setJMenuBar(menubar);
		c.add(toolbar, BorderLayout.WEST);
		c.add(canvas, BorderLayout.CENTER);

	}

	private static int pos = 0;
	private void addClass(String name) {
		UmlClass c = new UmlClass(canvas, name);
		canvas.add(c);
		pos = (pos + 100) % (Math.min(getWidth(), getHeight()) - c.getPreferredSize().height);
		c.setBounds(pos, pos, c.getPreferredSize().width, c.getPreferredSize().height);	
		c.validate();
	}

	public void setCurrentTool(Tool currentTool) {
		this.currentTool = currentTool;
	}

	public Tool getCurrentTool() {
		return currentTool;
	}

	public static void main(String[] args) {
		GUI.getInstance();
	}

	public void mouseClicked(MouseEvent e) {}
	public void mouseMoved(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {	}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseDragged(MouseEvent e) {}
}

