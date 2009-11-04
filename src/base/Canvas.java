package base;

import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.LinkedList;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import algorithm.force.Vector3D;

import uml_entities.Entity;
import uml_entity_connectives.BezierCurve;
import uml_entity_connectives.Connective;
import uml_entity_connectives.StraightLine;

@SuppressWarnings("serial")
public class Canvas extends JPanel {

    private LinkedList<Subgraph> subgraphs;

    private LinkedList<Connective> connectives;
    private Connective currentConnective;

    private Point mousePos;

    public Canvas() {
	//currentConnective = new StraightLine();
	currentConnective = new BezierCurve();
	connectives = new LinkedList<Connective>();
	subgraphs = new LinkedList<Subgraph>();
	addMouseMotionListener(new MouseMotionListener() {
	    public void mouseDragged(MouseEvent e) {}
	    public void mouseMoved(MouseEvent e) {
		mousePos = e.getPoint();
		if(currentConnective.getVertexCount() == 1) {
		    repaint();
		}
	    }

	});
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
	System.out.println(entity.getIdentifier());

	// add (potentially unfinished) connective to the entity, to make it easier to find

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
