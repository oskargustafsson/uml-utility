package uml_entity_connectives;

import java.awt.Point;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import uml_entities.Entity;

@SuppressWarnings("serial")
public class Connective extends Line2D {

	private Entity[] vertices;
	
	int nVertices;
	
	public Connective() {
		vertices = new Entity[2];
		nVertices = 0;
	}

	public void addVertex(Entity entity) {
		vertices[nVertices] = entity;
		nVertices++;
	}

	public int getVertexCount() {
		return nVertices;
	}
	
	public Entity getVertex(int index) {
		return vertices[index];
	}

	@Override
	public Point2D getP1() {
		return new Point2D.Double(  vertices[0].getX() + vertices[0].getWidth() / 2.0, 
									vertices[0].getX() + vertices[0].getHeight() / 2.0);
		
		// NOT DONE
	}

	@Override
	public Point2D getP2() {
		return new Point2D.Double(vertices[1].getBounds().getCenterX(), vertices[1].getBounds().getCenterY());
	}
	
	@Override
	public double getX1() {
		if(Math.abs(kVal()) < 1) {
			if(getCenterX1() < getCenterX2()) {
				return vertices[0].getX() + vertices[0].getWidth();
			}
			else {
				return vertices[0].getX();
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
				return vertices[1].getX();
			}
			else {
				return vertices[1].getX() + vertices[1].getWidth();
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
				return vertices[0].getY() + vertices[0].getHeight();
			}
			else {
				return vertices[0].getY();
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
				return vertices[1].getY();
			}
			else {
				return vertices[1].getY() + vertices[1].getHeight();
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

	public Rectangle2D getBounds2D() {
		double x1, y1, x2, y2;
		if(vertices[0].getX() < vertices[1].getX()) {
			x1 = vertices[0].getX() + vertices[0].getWidth() / 2.0;
			x2 = vertices[1].getX() - vertices[1].getWidth() / 2.0;
		}
		else {
			x1 = vertices[1].getX() + vertices[1].getWidth() / 2.0;
			x2 = vertices[0].getX() - vertices[0].getWidth() / 2.0;
		}
		
		if(vertices[0].getY() < vertices[1].getY()) {
			y1 = vertices[0].getY() + vertices[0].getHeight() / 2.0;
			y2 = vertices[1].getY() - vertices[1].getHeight() / 2.0;
		}
		else {
			y1 = vertices[1].getY() + vertices[1].getHeight() / 2.0;
			y2 = vertices[0].getY() - vertices[0].getHeight() / 2.0;
		}
		
		return new Rectangle2D.Double(x1, y1, x2-x1, y2-y1);
	}
	
	public void debugPrint() {
		System.out.println("P1: " + getP1());
		System.out.println("P2: " + getP2());
	}
	
	public double getCenterX1() {
		return vertices[0].getX() + vertices[0].getWidth() / 2.0; 
	}
	
	public double getCenterX2() {
		return vertices[1].getX() + vertices[1].getWidth() / 2.0; 
	}
	
	public double getCenterY1() {
		return vertices[0].getY() + vertices[0].getHeight() / 2.0; 
	}
	
	public double getCenterY2() {
		return vertices[1].getY() + vertices[1].getHeight() / 2.0; 
	}
	
	public double kVal() {
		return (getCenterY2()-getCenterY1())/(getCenterX2()-getCenterX1());
	}

}
