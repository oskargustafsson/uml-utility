package base;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.AbstractCollection;
import java.util.LinkedList;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import algorithm.force.ForceAlgorithm;
import algorithm.force.Vector3D;

import uml_entities.Entity;
import uml_entities.UmlClass;
import uml_entity_connectives.Association;
import uml_entity_connectives.BezierCurve;
import uml_entity_connectives.Connective;
import uml_entity_connectives.Dependency;
import uml_entity_connectives.Generalization;
import uml_entity_connectives.Realization;
import uml_entity_connectives.StraightLine;

@SuppressWarnings("serial")
public class Canvas extends JPanel {

	public static final int LABEL_DIST = 10, CHAR_W = 5;
	
	private LinkedList<Subgraph> subgraphs;

	private LinkedList<Connective> connectives;
	private Connective currentConnective;

	private Point mousePos = new Point(), prevMousePos = new Point();

	private int zoomLevel = 12;
	
	private Rectangle boundingBox = new Rectangle(0, 0, 1000, 1000);
	
	public Canvas() {
		//currentConnective = new StraightLine();
		currentConnective = null;
		connectives = new LinkedList<Connective>();
		subgraphs = new LinkedList<Subgraph>();

		addMouseWheelListener(new MouseWheelListener() {
			public void mouseWheelMoved(MouseWheelEvent arg0) {
				zoomLevel -= arg0.getWheelRotation();
				for(Component component : getComponents()) {
					((Entity)component).setZoom(zoomLevel);
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
				/*if(currentConnective.getVertexCount() == 1) {
					repaint();
				}*/
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
			((Entity)component).setPosition(component.getX(), component.getY());
		}
		ForceAlgorithm.centreOfGravity.add(dx, dy, 0);
		boundingBox.translate(dx, dy);
		for(Connective connective : connectives) {
			connective.calculatePoints();
		}
		repaint();
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

	public AbstractCollection<Subgraph> getSubgraphs() {
		return subgraphs;
	}
	
	public Rectangle getPhysicsBounds() {
	    return boundingBox;
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


			mergeSubgraphs(currentConnective);
			currentConnective.getVertex(0).getSubgraph().addEdge(currentConnective);

			currentConnective = null;
			GUI.getInstance().setCurrentTool(Tool.NONE);
			GUI.getInstance().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			GUI.getInstance().startAlgorithm();
			repaint();
			break;
		}
	}

	public void addConnective(Connective connective, Entity c1, Entity c2) {
		connective.addVertex(c1);
		connective.addVertex(c2);
		connective.calculatePoints();

		c1.addEdge(connective);
		c2.addEdge(connective);

		mergeSubgraphs(connective);

		connectives.add(connective);
		connective.getVertex(0).getSubgraph().addEdge(connective);
	}

	public void addSubgraph(Entity vertex) {
		Subgraph subgraph = new Subgraph();
		subgraph.addVertex(vertex);
		vertex.setSubgraph(subgraph);
		subgraphs.add(subgraph);
		System.out.println(vertex.getIdentifier() + " first element in subgraph " + subgraph.id);
	}

	public void mergeSubgraphs(Connective connective) {
		System.out.println("merging subgraphs " + connective.getVertex(0).getSubgraph().id + " and " + connective.getVertex(1).getSubgraph().id);
		/*connective.getVertex(0).getSubgraph().addAll(connective.getVertex(1).getSubgraph());
		subgraphs.remove(connective.getVertex(1).getSubgraph());
		for(Entity vertex : connective.getVertex(1).getSubgraph().getVertices())
			vertex.setSubgraph(connective.getVertex(0).getSubgraph());*/
		Subgraph newSubgraph = new Subgraph();
		Subgraph subgraph1 = connective.getVertex(0).getSubgraph();
		Subgraph subgraph2 = connective.getVertex(1).getSubgraph();
		newSubgraph.addAll(subgraph1);
		newSubgraph.addAll(subgraph2);
		for(Entity vertex : newSubgraph.getVertices()) {
			vertex.setSubgraph(newSubgraph);
		}
		subgraphs.add(newSubgraph);
		subgraphs.remove(subgraph1);
		subgraphs.remove(subgraph2);
	}

	private int dy = 0, dx = 0;
	
	public void paint(Graphics g) {

		Graphics2D g2d = (Graphics2D)g;
		//g2d.scale(0.5, 0.5);

		super.paint(g);

		// g2d.setStroke(dashed);

		Stroke stroke = g2d.getStroke();

		for(Connective c : connectives) {
			//g2d.draw(c);
			g2d.setStroke(c.getStroke());
			g2d.drawPolyline(c.xpoints, c.ypoints, c.npoints);
			g2d.setStroke(stroke);

			g2d.translate(c.getStartX(), c.getStartY());
			g2d.setColor(Color.WHITE);
			g2d.fillPolygon(c.getStartSymbol());
			g2d.setColor(Color.BLACK);
			g2d.drawPolygon(c.getStartSymbol());
			dx = (c.getStartDX() > 0) ? LABEL_DIST : -LABEL_DIST - CHAR_W * c.getMultiplicity(0).length();
			dy = (c.getStartDY() > 0) ? LABEL_DIST : -LABEL_DIST;
			g2d.drawString(c.getMultiplicity(0), dx, dy + 3);
			g2d.translate(-c.getStartX(), -c.getStartY());

			g2d.translate(c.getEndX(), c.getEndY());
			g2d.setColor(Color.WHITE);
			g2d.fillPolygon(c.getEndSymbol());
			g2d.setColor(Color.BLACK);
			g2d.drawPolygon(c.getEndSymbol());
			dx = (c.getEndDX() > 0) ? LABEL_DIST : -LABEL_DIST - CHAR_W * c.getMultiplicity(1).length();
			dy = (c.getEndDY() > 0) ? LABEL_DIST : -LABEL_DIST;
			g2d.drawString(c.getMultiplicity(1), dx, dy + 3);
			g2d.translate(-c.getEndX(), -c.getEndY());
		}
		
		//g2d.draw(boundingBox);
	}

	public void debugSubgraphs() {
		for(Subgraph subgraph : subgraphs) {
			System.out.println("SUBGRAPH");
			System.out.println(" VERTICES");
			for(Entity vertex : subgraph.getVertices())
				System.out.println("  " + vertex.getIdentifier());
			System.out.println(" EDGES");
			for(Connective edge : subgraph.getEdges())
				System.out.println("  " + edge.getVertex(0).getIdentifier() + " -> " + edge.getVertex(1).getIdentifier());
			System.out.println();
		}

	}
}
