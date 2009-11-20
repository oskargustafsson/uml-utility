package uml_entity_connectives;

import java.awt.BasicStroke;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import algorithm.force.Vector3D;

import uml_entities.Entity;

@SuppressWarnings("serial")
public class StraightLine extends Connective {

	public StraightLine(Polygon startSymbol, Polygon endSymbol, BasicStroke stroke) {
		super(new int[2], new int[2], 2, startSymbol, endSymbol, stroke);
	}

	public void calculatePoints() {

		Entity 
		e0 = getVertex(0), 
		e1 = getVertex(1);

		double 
		x0 = e0.getX() + e0.getWidth() / 2,
		y0 = e0.getY() + e0.getHeight() / 2,
		w0 = e0.getWidth() / 2,
		h0 = e0.getHeight() / 2,
		x1 = e1.getX() + e1.getWidth() / 2,
		y1 = e1.getY() + e1.getHeight() / 2,
		w1 = e1.getWidth() / 2,
		h1 = e1.getHeight() / 2;

		double k = (y1 - y0) / (x1 - x0) ;

		// First point
		if(Math.abs(k) > (h0 / w0)) {
			xpoints[0] = (int)(x0 + Math.signum(y1-y0) * h0 / k);
			ypoints[0] = (int)(y0 + Math.signum(y1-y0) * h0);
		}
		else {
			xpoints[0] = (int)(x0 + Math.signum(x1-x0) * w0);
			ypoints[0] = (int)(y0 + Math.signum(x1-x0) * w0 * k);
		}

		// Second point
		if(Math.abs(k) > (h1 / w1)) {
			xpoints[1] = (int)(x1 + Math.signum(y0-y1) * h1 / k);
			ypoints[1] = (int)(y1 + Math.signum(y0-y1) * h1);
		}
		else {
			xpoints[1] = (int)(x1 + Math.signum(x0-x1) * w1);
			ypoints[1] = (int)(y1 + Math.signum(x0-x1) * w1 * k);
		}
	}
}
