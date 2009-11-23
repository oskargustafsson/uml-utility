package base;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.util.AbstractCollection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.*;
import javax.swing.border.BevelBorder;

import algorithm.Applier;
import algorithm.force.ForceAlgorithm;
import algorithm.force.Vector3D;

import uml_entities.Entity;
import uml_entities.SimpleInterface;
import uml_entities.UmlClass;
import uml_entity_components.Attribute;
import uml_entity_components.Visibility;
import uml_entity_connectives.Connective;
import uml_entity_connectives.StraightLine;

@SuppressWarnings("serial")
public class GUI extends JFrame implements MouseListener, MouseMotionListener {

	private static GUI me = null;

	private final JFileChooser fileChooser = new JFileChooser();

	private File projectPath = null;

	private JMenuBar menubar;
	private JMenu mnuFile, mnuEdit;
	private JMenuItem mnuNew, mnuOpen, mnuDebugSubgraphs;
	private JToolBar toolbar;
	private JButton addClass, addConnective, runAlgorithm, btnFlatten;
	private Canvas canvas;

	private Tool currentTool = Tool.NONE;

	private Applier applier;

	private GUI() {
		addMouseListener(this);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(800, 600);
		initGUI();
		setVisible(true);
	}

	public static GUI getInstance() {
		if(me == null) {
			me = new GUI();
		}
		return me;
	}

	private void initGUI() {
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		mnuOpen = new JMenuItem("Open...");
		mnuOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				selectProjectPath();
				DirectoryParser.generateUmlClassDiagram(fileChooser.getSelectedFile());
			}});

		mnuNew= new JMenuItem("New project...");
		mnuNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				selectProjectPath();
			}});

		mnuDebugSubgraphs = new JMenuItem("Debug subgraphs");
		mnuDebugSubgraphs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				canvas.debugSubgraphs();
			}
		});
		
		mnuFile = new JMenu("File");
		mnuFile.add(mnuOpen);

		mnuEdit = new JMenu("Edit");
		mnuEdit.add(mnuDebugSubgraphs);

		menubar = new JMenuBar();

		menubar.add(mnuFile);
		menubar.add(mnuEdit);

		addClass = new JButton("Class");
		addClass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addClass(new File(projectPath.getAbsolutePath() + "/" + JOptionPane.showInputDialog(canvas, "Class name:") + ".java"));
			}
		});

		addConnective = new JButton("Conn");
		addConnective.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentTool = Tool.DRAW_CONNECTIVE;
				setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
		});

		runAlgorithm = new JButton("Run");
		runAlgorithm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(applier == null) {
					startAlgorithm();
				}
				else {
					stopAlgorithm();
				}
			}
		});

		btnFlatten = new JButton("Flatten");
		btnFlatten.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				switchDimension();
			}
		});

		toolbar = new JToolBar(JToolBar.VERTICAL);
		toolbar.setRollover(true);
		toolbar.setVisible(false);
		toolbar.add(addClass);
		toolbar.add(addConnective);
		toolbar.addSeparator();
		toolbar.add(runAlgorithm);
		toolbar.add(btnFlatten);


		canvas = new Canvas();
		canvas.setLayout(null);
		canvas.setBorder(BorderFactory.createEtchedBorder());
		canvas.setBackground(new Color(225, 235, 240));
		canvas.setOpaque(true);

		Component[] classes = canvas.getComponents();

		for(Component c : classes) {
			System.out.println(c.getParent().getComponentZOrder(c));
		}

		//((UmlClass)(classes[0])).addAttribute(new Attribute(Visibility.PRIVATE, "name", "String", "Oskar"));
		//((UmlClass)(classes[0])).addAttribute(new Attribute(Visibility.PRIVATE, "age", "int", "21"));

		Container c = getContentPane();

		c.setLayout(new BorderLayout());

		setJMenuBar(menubar);
		c.add(toolbar, BorderLayout.WEST);
		c.add(canvas, BorderLayout.CENTER);

	}

	public void switchDimension() {
		if(ForceAlgorithm.doFlatten) {
			btnFlatten.setText("Flatten");
		}
		else {
			btnFlatten.setText("Expand");
		}
		ForceAlgorithm.doFlatten = !ForceAlgorithm.doFlatten; 
	}

	protected void selectProjectPath() {
		if (fileChooser.showOpenDialog(me) == JFileChooser.APPROVE_OPTION) { 
			if(projectPath == null) {
				toolbar.setVisible(true);
			}
			projectPath = fileChooser.getSelectedFile();
			System.out.println("Project path: " + projectPath);
		}
	}

	public void stopAlgorithm() {
		applier.interrupt();
		applier = null;
		runAlgorithm.setText("Run");
	}

	public void startAlgorithm() {
		applier = new Applier(new ForceAlgorithm(), canvas, 25);
		applier.start();
		runAlgorithm.setText("Stop");
	}

	private static int pos = 0, spacing = 200, noise = 20;

	public UmlClass addClass(File file) {
		UmlClass c = new UmlClass(canvas, file);
		canvas.add(c);
		canvas.addSubgraph(c);
		c.setBounds(pos % (getWidth()-spacing) + (int)(Math.random() * noise), 
				spacing * (pos/(getWidth()-spacing)) + (int)(Math.random() * noise), 
				c.getPreferredSize().width, 
				c.getPreferredSize().height);
		c.setPosition(new Vector3D(c.getX(), c.getY(), (int)(Math.random() * noise * 10)));
		pos = (pos + spacing) % (getWidth() * getHeight());
		c.validate();
		return c;
	}

	public SimpleInterface addSimpleInterface(Entity realizer, String name) {
		SimpleInterface i = new SimpleInterface(canvas, name);
		i.setBounds(realizer.getX(), realizer.getY() - 100, SimpleInterface.BOUND_W, SimpleInterface.BOUND_H);
		i.setPosition(new Vector3D(i.getX(), i.getY(), (int)(Math.random() * noise * 10)));
		canvas.add(i);
		canvas.addSubgraph(i);
		i.validate();
		return i;
	}

	public HashMap<String, Entity> getClassMap() {
		HashMap<String, Entity> classes = new HashMap<String, Entity>();
		for(Component c : canvas.getComponents()) {
			classes.put(((Entity)c).getIdentifier(), (Entity)c);
		}
		return classes;
	}

	public void addConnective(Connective conn, Entity c1, Entity c2) {
		canvas.addConnective(conn, c1, c2);
	}

	public void setCurrentTool(Tool currentTool) {
		this.currentTool = currentTool;
	}

	public Tool getCurrentTool() {
		return currentTool;
	}

	public Canvas getCanvas() {
	    return canvas;
	}

	public void setCanvas(Canvas canvas) {
	    this.canvas = canvas;
	}

	public static void main(String[] args) {
		GUI gui = GUI.getInstance();
	}

	public void mouseClicked(MouseEvent e) {}
	public void mouseMoved(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {	}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseDragged(MouseEvent e) {}
}

