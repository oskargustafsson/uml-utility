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
			return Visibility.PUBLIC;

		if(string.equals("PROTECTED"))
			return Visibility.PROTECTED;

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
			//System.out.println("PUBLIC");
			return Visibility.PUBLIC;
		}
		if((v & 2) != 0) {
			//System.out.println("PROTECTED");
			return Visibility.PROTECTED;
		}
		if((v & 4) != 0) {
			//System.out.println("PRIVATE");
			return Visibility.PRIVATE;
		}
		//System.out.println("UNDEF");
		return Visibility.UNDEFINED;
	}

	private static Vector3D intersect = new Vector3D(0,0,0);
	private static Vector3D distance = new Vector3D(0,0,0);

	public static Vector3D getDistance(Entity e0, Entity e1) {

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
		h1 = e1.getHeight() / 2;

		double k = (y1 - y0) / (x1 - x0) ;

		/*	// Second entity
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

		distance.z += c2.getZ() - c1.getZ(); */

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

		distance.sub(intersect);

		return distance;
	}
	
	public static String getMultiplicityFromString(String s) {
		return "1";
	}
}
