package uml_entity_connectives;

import java.awt.BasicStroke;
import java.awt.Polygon;

import uml_entities.Entity;

import algorithm.force.Vector3D;

@SuppressWarnings("serial")
public abstract class BezierCurve extends Connective {

	public static final int NODES = 16;

	public BezierCurve(Polygon startSymbol, Polygon endSymbol, BasicStroke stroke) {
		super(new int[NODES + 1], new int[NODES + 1], NODES + 1, startSymbol, endSymbol, stroke);
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
		h1 = e1.getHeight() / 2,
		k = (y1 - y0) / (x1 - x0),
		s;

		dx0 = 0;
		dy0 = 0; 
		dx1 = 0;
		dy1 = 0; 
		// First point
		if(Math.abs(k) > (h0 / w0)) {
			s = Math.signum(y1-y0);
			px0 = (int)(x0 + s * h0 / k);
			py0 = (int)(y0 + s * h0);
			dy0 = 1;
		}
		else {
			s = Math.signum(x1 - x0);
			px0 = (int)(x0 + s * w0);
			py0 = (int)(y0 + s * w0 * k);
			dx0 = 1;
		}

		// Second point
		if(Math.abs(k) > (h1 / w1)) {
			s = Math.signum(y0-y1);
			px1 = (int)(x1 + s * h1 / k);
			py1 = (int)(y1 + s * h1);
			dy1 = 1;
		}
		else {
			s = Math.signum(x0 - x1);
			px1 = (int)(x1 + s * w1);
			py1 = (int)(y1 + s * w1 * k);
			dx1 = 1;
		}

		dx0 *= (px1 - px0) / 2;
		dy0 *= (py1 - py0) / 2;
		dx1 *= (px0 - px1) / 2;
		dy1 *= (py0 - py1) / 2;
		
		//generateRepresentation();
		setCurve(px0, py0, px0+dx0, py0+dy0, px1+dx1, py1+dy1, px1, py1);
	}

	public void generateRepresentation() {
		/*double t;
		for(int i = 1; i < NODES; i++) {
			t =  (double)i / NODES;
			xpoints[i] = (int)( (1-t)*(1-t)*(1-t)*xpoints[0] + 3*(1-t)*(1-t)*t*(xpoints[0] + dx0) + 3*(1-t)*t*t*(xpoints[NODES] + dx1) + t*t*t*xpoints[NODES] );
			ypoints[i] = (int)( (1-t)*(1-t)*(1-t)*ypoints[0] + 3*(1-t)*(1-t)*t*(ypoints[0] + dy0) + 3*(1-t)*t*t*(ypoints[NODES] + dy1) + t*t*t*ypoints[NODES] );
		}*/
		setCurve(px0, py0, px0+dx0, py0+dy0, px1+dx1, py1+dy1, px1, py1);
	}
	
}
