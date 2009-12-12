package uml_entity_connectives;

import algorithm.force.Vector3D;
import uml_entities.Entity;

public class Association extends BezierCurve {

	public Association() {
		super(Connective.EMPTY, Connective.EMPTY, Connective.PLAIN);
	}
	
	private Vector3D intersect = new Vector3D(0,0,0);
	private Vector3D distance = new Vector3D(0,0,0);
	
	public void calculatePoints() {
		
	    super.calculatePoints();
	    
		/*Entity 
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
		
		double dx = (x1-x0) != 0 ? (x1-x0) : 0.00001;
		double dy = (y1-y0) != 0 ? (y1-y0) : 0.00001;
		double dz = (z1-z0) != 0 ? (z1-z0) : 0.00001;
	
		// distance from center to center, in XZ-plane
		distance.setTo(dx, 0, dz);
		double origXZDistance = distance.length();
		
		
		// circles cylinder in XZ-plane
		intersect.setTo(dx, 0, dz);
		intersect.mul((w0 + w1) / origXZDistance);
		
		// if the circles intersect, set intersect to original distance - 1
		if(intersect.length() > origXZDistance + 1) {
		    intersect.normalize();
		    intersect.mul(distance.length() - 1);
		}
		
		// switch to 3D
		distance.y = dy;
		
		// calculate where the Y-component should be
		intersect.y = dy * intersect.length() / origXZDistance;
		
		// cap Y at top/bottom of cylinder
		if(Math.abs(intersect.y) > h0 + h1) {
		    intersect.mul((h0 + h1)/Math.abs(intersect.y));
		}
		
		
		
		
		
		xpoints[0] = (int)(x0 + intersect.x / 2.0);
		ypoints[0] = (int)(y0 + intersect.y / 2.0);
		
		distance.sub(intersect);
		
		xpoints[NODES] = (int)(xpoints[0] + distance.x);
		ypoints[NODES] = (int)(ypoints[0] + distance.y);

		    
		generateRepresentation();*/
	}
}
