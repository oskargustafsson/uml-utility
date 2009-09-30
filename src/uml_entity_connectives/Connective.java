package uml_entity_connectives;

import java.awt.Point;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import uml_entities.Entity;

@SuppressWarnings("serial")
public class Connective extends Line2D {

	private Entity[] edges;
	
	int nEdges;
	
	public Connective() {
		edges = new Entity[2];
		nEdges = 0;
	}

	public void addEdge(Entity entity) {
		edges[nEdges] = entity;
		nEdges++;
	}

	public int getEdgeCount() {
		return nEdges;
	}
	
	public Entity getEdge(int index) {
		return edges[index];
	}

	@Override
	public Point2D getP1() {
		return new Point2D.Double(  edges[0].getX() + edges[0].getWidth() / 2.0, 
									edges[0].getX() + edges[0].getHeight() / 2.0);
		
		// NOT DONE
	}

	@Override
	public Point2D getP2() {
		return new Point2D.Double(edges[1].getBounds().getCenterX(), edges[1].getBounds().getCenterY());
	}
	
	@Override
	public double getX1() {
		if(Math.abs(kVal()) < 1) {
			if(getCenterX1() < getCenterX2()) {
				return edges[0].getX() + edges[0].getWidth();
			}
			else {
				return edges[0].getX();
			}
		}
		else {
			return getCenterX1();
		}
	}

	@Override
	public double getX2() {
		if(Math.abs(kVal()) < 1) {
			if(getCenterX1() < getCenterX2()) {
				return edges[1].getX();
			}
			else {
				return edges[1].getX() + edges[1].getWidth();
			}
		}
		else {
			return getCenterX2();
		} 
	}

	@Override
	public double getY1() {
		if(Math.abs(kVal()) > 1) {
			if(getCenterY1() < getCenterY2()) {
				return edges[0].getY() + edges[0].getHeight();
			}
			else {
				return edges[0].getY();
			}
		}
		else {
			return getCenterY1();
		}
	}

	@Override
	public double getY2() {
		if(Math.abs(kVal()) > 1) {
			if(getCenterY1() < getCenterY2()) {
				return edges[1].getY();
			}
			else {
				return edges[1].getY() + edges[1].getHeight();
			}
		}
		else {
			return getCenterY2();
		}
	}

	@Override
	public void setLine(double x1, double y1, double x2, double y2) {
		// do nothing
	}

	@Override
	public Rectangle2D getBounds2D() {
		double x1, y1, x2, y2;
		if(edges[0].getX() < edges[1].getX()) {
			x1 = edges[0].getX() + edges[0].getWidth() / 2.0;
			x2 = edges[1].getX() - edges[1].getWidth() / 2.0;
		}
		else {
			x1 = edges[1].getX() + edges[1].getWidth() / 2.0;
			x2 = edges[0].getX() - edges[0].getWidth() / 2.0;
		}
		
		if(edges[0].getY() < edges[1].getY()) {
			y1 = edges[0].getY() + edges[0].getHeight() / 2.0;
			y2 = edges[1].getY() - edges[1].getHeight() / 2.0;
		}
		else {
			y1 = edges[1].getY() + edges[1].getHeight() / 2.0;
			y2 = edges[0].getY() - edges[0].getHeight() / 2.0;
		}
		
		return new Rectangle2D.Double(x1, y1, x2-x1, y2-y1);
	}
	
	public void debugPrint() {
		System.out.println("P1: " + getP1());
		System.out.println("P2: " + getP2());
	}
	
	public double getCenterX1() {
		return edges[0].getX() + edges[0].getWidth() / 2.0; 
	}
	
	public double getCenterX2() {
		return edges[1].getX() + edges[1].getWidth() / 2.0; 
	}
	
	public double getCenterY1() {
		return edges[0].getY() + edges[0].getHeight() / 2.0; 
	}
	
	public double getCenterY2() {
		return edges[1].getY() + edges[1].getHeight() / 2.0; 
	}
	
	public double kVal() {
		return (getCenterY2()-getCenterY1())/(getCenterX2()-getCenterX1());
	}
}
