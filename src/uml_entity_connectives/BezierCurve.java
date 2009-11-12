package uml_entity_connectives;

import java.awt.Polygon;

import uml_entities.Entity;

import algorithm.force.Vector3D;

@SuppressWarnings("serial")
public class BezierCurve extends Connective {

    public static final int NODES = 19;

    public BezierCurve() {
	super(new int[NODES + 1], new int[NODES + 1], NODES + 1);
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
	dx0 = 0,
	dy0 = 0, 
	dx1 = 0,
	dy1 = 0, 
	s, t;

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
	
	dx0 *= (xpoints[NODES] - xpoints[0]) / 2;
	dy0 *= (ypoints[NODES] - ypoints[0]) / 2;
	dx1 *= (xpoints[0] - xpoints[NODES]) / 2;
	dy1 *= (ypoints[0] - ypoints[NODES]) / 2;

	// Points in between
	for(int i = 1; i < NODES; i++) {
	    t =  (double)i / NODES;
	    xpoints[i] = (int)( (1-t)*(1-t)*(1-t)*xpoints[0] + 3*(1-t)*(1-t)*t*(xpoints[0] + dx0) + 3*(1-t)*t*t*(xpoints[NODES] + dx1) + t*t*t*xpoints[NODES] );
	    ypoints[i] = (int)( (1-t)*(1-t)*(1-t)*ypoints[0] + 3*(1-t)*(1-t)*t*(ypoints[0] + dy0) + 3*(1-t)*t*t*(ypoints[NODES] + dy1) + t*t*t*ypoints[NODES] );
	}
    }

}
