package utils;

import java.awt.Rectangle;

import algorithm.force.Vector3D;
import uml_entities.Entity;
import uml_entities.UmlClass;
import uml_entity_components.Visibility;

public class Functions {

	public static Visibility visibilityFromString(String string) {
		string = string.toUpperCase().trim();
		
		if(string.equals("PRIVATE") || string.equals("+"))
			return Visibility.PRIVATE;
		
		if(string.equals("PUBLIC"))
			return Visibility.PRIVATE;
		
		if(string.equals("PROTECTED"))
			return Visibility.PRIVATE;
		
		return Visibility.UNDEFINED;
	}
	
	public static String getVisibilityRepresentation(Visibility visibility) {
		switch(visibility) {
		case PRIVATE:	return "-";
		case PUBLIC:	return "+";
		case PROTECTED:	return "#";
		default:		return "";
		}
	}
	
	public static Visibility visibilityFromInt(int v) {
	    if((v & 1) != 0) {
		return Visibility.PUBLIC;
	    }
	    if((v & 2) != 0) {
		return Visibility.PROTECTED;
	    }
	    if((v & 4) != 0) {
		return Visibility.PRIVATE;
	    }
	    return Visibility.UNDEFINED;
	}
	
	public static Vector3D getDistance(Entity c1, Entity c2) {
		
		Vector3D distance = new Vector3D(0,0,0);
		
		Entity 
		e0 = c1, 
		e1 = c2;

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

		// Second entity
		if(Math.abs(k) > (h1 / w1)) {
			distance.x += (int)(x1 + Math.signum(y0-y1) * h1 / k);
			distance.y += (int)(y1 + Math.signum(y0-y1) * h1);
		}
		else {
			distance.x += (int)(x1 + Math.signum(x0-x1) * w1);
			distance.y += (int)(y1 + Math.signum(x0-x1) * w1 * k);
		}
		
		// First entity
		if(Math.abs(k) > (h0 / w0)) {
			distance.x -= (int)(x0 + Math.signum(y1-y0) * h0 / k);
			distance.y -= (int)(y0 + Math.signum(y1-y0) * h0);
		}
		else {
			distance.x -= (int)(x0 + Math.signum(x1-x0) * w0);
			distance.y -= (int)(y0 + Math.signum(x1-x0) * w0 * k);
		}
		
		distance.z += c2.getZ() - c1.getZ();
		
		/* // First point
		if(Math.abs(k) > (h0 / w0)) {
			distance.setTo(-(x0 + Math.signum(y1-y0) * h0 / k), -(y0 + Math.signum(y1-y0) * h0), 0);
		}
		else {
			distance.setTo(-(x0 + Math.signum(x1-x0) * w0), -(y0 + Math.signum(x1-x0) * w0 * k), 0);
		}

		// Second point
		if(Math.abs(k) > (h1 / w1)) {
			distance.add((x1 + Math.signum(y0-y1) * h1 / k), (y1 + Math.signum(y0-y1) * h1), 0);
		}
		else {
			distance.add((x1 + Math.signum(x0-x1) * w1), (y1 + Math.signum(x0-x1) * w1 * k), 0);
		} */
		
		//Vector3D r = new Vector3D(c2.getPosition());
		//return r.sub(c1.getPosition());
		
		return distance;
	}
}
