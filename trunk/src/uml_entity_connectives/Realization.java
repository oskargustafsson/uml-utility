package uml_entity_connectives;

import uml_entities.Entity;
import uml_entities.SimpleInterface;

public class Realization extends BezierCurve {

	public Realization() {
		super(Connective.EMPTY, Connective.TRIANGLE, Connective.DASHED);
	}

	public void calculatePoints() {

		Entity 
		e0 = getVertex(0), 
		e1 = getVertex(1);

		int e1_w, e1_h;

		if(e1 instanceof SimpleInterface) {
			e1_w = SimpleInterface.CIRCLE_DIAMETER;
			e1_h = SimpleInterface.CIRCLE_DIAMETER;
		}
		else {
			e1_w = e1.getWidth();
			e1_h = e1.getHeight();
		}

		double 
		x0 = e0.getX() + e0.getWidth() / 2,
		y0 = e0.getY() + e0.getHeight() / 2,
		w0 = e0.getWidth() / 2,
		h0 = e0.getHeight() / 2,
		x1 = e1.getX() + e1_w / 2,
		y1 = e1.getY() + e1_h / 2,
		w1 = e1_w / 2,
		h1 = e1_h / 2,
		k = (y1 - y0) / (x1 - x0),
		length = Math.sqrt(Math.pow(x1-x0, 2) + Math.pow((y1-y0), 2)),
		s;

		dx0 = 0;
		dy0 = 0; 
		dx1 = 0;
		dy1 = 0; 
		// First point
		if(Math.abs(k) > (h0 / w0)) {
			s = Math.signum(y1-y0);
			xpoints[0] = (int)(x0 + s * h0 / k);
			ypoints[0] = (int)(y0 + s * h0);
			dy0 = 1;
		}
		else {
			s = Math.signum(x1 - x0);
			xpoints[0] = (int)(x0 + s * w0);
			ypoints[0] = (int)(y0 + s * w0 * k);
			dx0 = 1;
		}

		// Second point
		if(e1 instanceof SimpleInterface) {
			xpoints[NODES] = (int)(x1 - (w1 * (x1-x0) / length));
			ypoints[NODES] = (int)(y1 - (h1 * (y1-y0) / length));
			dx1 = 1;
			dy1 = 1;
		}
		else {
			if(Math.abs(k) > (h1 / w1)) {
				s = Math.signum(y0-y1);
				xpoints[NODES] = (int)(x1 + s * h1 / k);
				ypoints[NODES] = (int)(y1 + s * h1);
				dy1 = 1;
			}
			else {
				s = Math.signum(x0 - x1);
				xpoints[NODES] = (int)(x1 + s * w1);
				ypoints[NODES] = (int)(y1 + s * w1 * k);
				dx1 = 1;
			}
		}

		dx0 *= (xpoints[NODES] - xpoints[0]) / 2;
		dy0 *= (ypoints[NODES] - ypoints[0]) / 2;
		dx1 *= (xpoints[0] - xpoints[NODES]) / 2;
		dy1 *= (ypoints[0] - ypoints[NODES]) / 2;
		
		/*xpoints[NODES] -= (int)(10 * (x1-x0) / length);
		ypoints[NODES] -= (int)(10 * (y1-y0) / length);*/

		generateRepresentation();

	}
}
