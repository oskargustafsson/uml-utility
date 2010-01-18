package uml_entity_connectives;

import uml_entities.Entity;

public class Generalization extends BezierCurve {

	public Generalization() {
		super(Connective.EMPTY, Connective.TRIANGLE, Connective.PLAIN);
		weight = 1;
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
		px1 = (int)x1;
		py1 = (int)(y1 + h1);
		dy1 = h1;
		
		if(y1 > y0) {
			dy1 += y1 - y0;
		}

		dx0 *= (px1 - px0) / 2;
		dy0 *= (py1 - py0) / 2;

		generateRepresentation();

	}

}
