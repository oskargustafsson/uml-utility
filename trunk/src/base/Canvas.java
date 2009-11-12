package base;

import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.LinkedList;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import algorithm.force.Vector3D;

import uml_entities.Entity;
import uml_entities.UmlClass;
import uml_entity_connectives.BezierCurve;
import uml_entity_connectives.Connective;
import uml_entity_connectives.StraightLine;

@SuppressWarnings("serial")
public class Canvas extends JPanel {

    private LinkedList<Subgraph> subgraphs;

    private LinkedList<Connective> connectives;
    private Connective currentConnective;

    private Point mousePos = new Point(), prevMousePos = new Point();

    private int fontSize = 12;

    public Canvas() {
	//currentConnective = new StraightLine();
	currentConnective = new BezierCurve();
	connectives = new LinkedList<Connective>();
	subgraphs = new LinkedList<Subgraph>();

	addMouseWheelListener(new MouseWheelListener() {
	    public void mouseWheelMoved(MouseWheelEvent arg0) {
		fontSize -= arg0.getWheelRotation();
		for(Component component : getComponents()) {
		    ((UmlClass)component).setFontSize(fontSize);
		}
		for(Connective connective : connectives) {
		    connective.calculatePoints();
		}
	    }
	});

	addMouseListener(new MouseListener() {
	    public void mouseClicked(MouseEvent e) {}
	    public void mouseEntered(MouseEvent e) {}
	    public void mouseExited(MouseEvent e) {}
	    public void mousePressed(MouseEvent e) {}
	    public void mouseReleased(MouseEvent e) {
		if(getCursor().getType() == Cursor.MOVE_CURSOR) {
		    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
	    }
	});

	addMouseMotionListener(new MouseMotionListener() {
	    public void mouseDragged(MouseEvent e) {
		moveDiagram(e);
		setCursor(new Cursor(Cursor.MOVE_CURSOR));
	    }
	    public void mouseMoved(MouseEvent e) {
		prevMousePos.setLocation(mousePos);
		mousePos.setLocation(e.getPoint());
		if(currentConnective.getVertexCount() == 1) {
		    repaint();
		}
	    }

	});
    }

    private void moveDiagram(MouseEvent e) {
	prevMousePos.setLocation(mousePos);
	mousePos.setLocation(e.getPoint());
	int dx = mousePos.x - prevMousePos.x;
	int dy = mousePos.y - prevMousePos.y;
	for(Component component : getComponents()) {
	    component.setLocation(component.getX() + dx, component.getY() + dy);
	}
	for(Connective connective : connectives) {
	    connective.calculatePoints();
	}
    }

    public Connective getCurrentConnective() {
	return currentConnective;
    }

    public void setCurrentConnective(Connective currentConnective) {
	this.currentConnective = currentConnective;
    }

    public LinkedList<Connective> getConnectives() {
	return connectives;
    }

    public void addEdgeToCurrentConnective(Entity entity) {
	// System.out.println(entity.getIdentifier());

	switch(currentConnective.getVertexCount()) {
	case 0:
	    currentConnective.addVertex(entity);
	    break;
	case 1:
	    currentConnective.addVertex(entity);
	    currentConnective.calculatePoints();

	    connectives.add(currentConnective);

	    currentConnective.getVertex(0).addEdge(currentConnective);
	    currentConnective.getVertex(1).addEdge(currentConnective);

	    currentConnective = new BezierCurve();
	    GUI.getInstance().setCurrentTool(Tool.NONE);
	    GUI.getInstance().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	    GUI.getInstance().startAlgorithm();
	    repaint();
	    break;
	}
    }

    public void addConnective(UmlClass c1, UmlClass c2) {
	Connective connective = new BezierCurve();
	connective.addVertex(c1);
	connective.addVertex(c2);
	connective.calculatePoints();

	c1.addEdge(connective);
	c2.addEdge(connective);

	connectives.add(connective);
    }

    Vector3D p0 = new Vector3D(10, 10, 0);
    Vector3D p1 = new Vector3D(300, 300, 0);
    Vector3D p2 = new Vector3D(100, 100, 0);
    Vector3D p3 = new Vector3D(120, 100, 0);

    public void paint(Graphics g) {

	Graphics2D g2d = (Graphics2D)g;
	//g2d.scale(0.5, 0.5);

	super.paint(g);

	for(Connective c : connectives) {
	    //g2d.draw(c);
	    g2d.drawPolyline(c.xpoints, c.ypoints, c.npoints);
	}
    }
}
