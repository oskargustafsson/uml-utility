package uml_entity_connectives;

import algorithm.force.Vector3D;
import uml_entities.Entity;

public class Association extends BezierCurve {

	public Association() {
		super(Connective.EMPTY, Connective.EMPTY, Connective.PLAIN);		
	}
	
	public void calculatePoints() {
		
		Entity 
		e0 = getVertex(0), 
		e1 = getVertex(1);

		double 
		x0 = e0.getX() + e0.getWidth() / 2,
		y0 = e0.getY() + e0.getHeight() / 2,
		z0 = e0.getZ(),
		w0 = e0.getWidth() / 2,
		h0 = e0.getHeight() / 2,
		x1 = e1.getX() + e1.getWidth() / 2,
		y1 = e1.getY() + e1.getHeight() / 2,
		z1 = e1.getZ(),
		w1 = e1.getWidth() / 2,
		h1 = e1.getHeight() / 2,
		k = (y1 - y0) / (x1 - x0),
		s;
		
		Vector3D intersect = new Vector3D(0,0,0);
		Vector3D distance = new Vector3D(0,0,0);
		
		double xzAngle = Math.atan2(z1-z0, x1-x0);
		double kVal = (y1-y0)/(x1-x0);
		
		// distance from center to center
		distance.setTo(x1 - x0, y1 - y0, z1 - z0);
		
		// FIRST CYLINDER
		
		// subtract intersections
		intersect.setTo(w0 * Math.cos(xzAngle), 0, Math.sin(xzAngle));
		intersect.y = kVal * intersect.length();
		
		// top/bottom
		/*if(Math.abs(intersect.y) > h0) {
			//intersect.mul(1 - (intersect.y - (Math.signum(intersect.y) * h0)));
			intersect.mul(h0/Math.abs(intersect.y));
		}*/
		
		distance.sub(intersect);
		
		xpoints[0] = (int)x0;
		ypoints[0] = (int)y0;
		
		xpoints[NODES] = (int)(x0 + distance.x);
		ypoints[NODES] = (int)(y0 + distance.y);
		
		generateRepresentation();
	}
}
