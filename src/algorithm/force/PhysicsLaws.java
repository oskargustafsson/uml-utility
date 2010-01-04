package algorithm.force;

import java.awt.Component;
import java.awt.Rectangle;

import base.GUI;

import uml_entities.Entity;
import utils.Functions;

public class PhysicsLaws {

	public static double PI = 3.14159265;

	// 
	public static double SHORTEST_DIST = 1000.0;

	// Coulomb constants
	public static double E0 = 10.0;

	// Hooke constants
	public static double EQUILIBRUM = 300.0;
	public static double E1 = 0.1;

	// Hierarchy constants
	public static double HIERARCHY_Y_DIST = 100;
	/*
	 * calculates the force vector between c1 and c2
	 */
	public static Vector3D coulomb(Entity c1, Entity c2) {
		Vector3D r = getDirectionVector(c1, c2);
		// return: r * ((c1*c2) / (4*PI*E0*|r|�))
		return r.unit().mul((getCharge(c1) * getCharge(c2)) / (E0 * r.length() * r.length() + SHORTEST_DIST));
	}

	public static Vector3D hooke(Entity c1, Entity c2) {
		Vector3D r = getDirectionVector(c1, c2);
		//System.out.println("direction vector: " + r);
		return r.unit().mul(
				E1 * Math.log1p(Math.abs(EQUILIBRUM - r.length()) ) * Math.signum(EQUILIBRUM - r.length()) );
				//Math.sqrt(Math.abs(EQUILIBRUM - r.length()) ) * Math.signum(EQUILIBRUM - r.length()) );
	}

	public static Vector3D attraction(Entity c1, Entity c2) {
		Vector3D r = getDirectionVector(c1, c2);
		//System.out.println(r);
		return r.unit().mul(E1 * Math.sqrt(1 + r.length()));
	}

	public static Vector3D boundingBoxRestriction(Vector3D c1) {
		Rectangle bounds = GUI.getInstance().getCanvas().getPhysicsBounds();
		/*if(bounds.contains(c1.x, c1.y)) {
		return new Vector3D(0,0,0);
	    }
	    else {
		return attraction(new Vector3D(bounds.getCenterX(), bounds.getCenterY(), 0), c1);
	    }*/

		/*if(Math.abs(c1.x - bounds.x) < Math.abs(c1.x - (bounds.x + bounds.width))) {

	    }*/

		double dx = 0, dy = 0;

		if(c1.x < bounds.x) {
			dx = Math.sqrt(bounds.x - c1.x);
		}
		else if(bounds.x + bounds.width < c1.x) {
			dx = -Math.sqrt(c1.x - (bounds.x + bounds.width));
		}

		if(c1.y < bounds.y) {
			dy = Math.sqrt(bounds.y - c1.y);
		}
		else if(bounds.y + bounds.height < c1.y) {
			dy = -Math.sqrt(c1.y - (bounds.y + bounds.height));
		}

		return new Vector3D(dx, dy, 0);
	}

	public static Vector3D ortogonalize(Entity c1, Entity c2) {
		Vector3D r = getDirectionVector(c1, c2);
		double angle = Math.atan2(r.x, r.y);
		if(angle < 0) angle = 2*PI+angle;
		//System.out.println(angle);
		return normal(r).unit().mul(4 * Math.signum(Math.sin((4.0 * angle))) * Math.abs(Math.sin(2.0 * angle)));
	}

	public static Vector3D hierarchy(Entity c1, Entity c2) {
		// c1 is a subordinate of c2
		/*Vector3D r = getDirectionVector(c2, c1);
		r.y += HIERARCHY_Y_DIST;
		if(r.y < 0) {
			r.setTo(0, (50.0 / (double)(1 + (r.y * r.y))), 0);
		}
		else {
			r.setTo(0, 50.0, 0);
		}
		return r; */
		Vector3D r = getDirectionVector(c2, c1);
		r.setTo(0, ((PI/2.0) + Math.atan(r.y + HIERARCHY_Y_DIST)) * 50, 0);
		return r;
	}

	public static Vector3D normal(Vector3D c1) {
		return new Vector3D(-c1.y, c1.x, c1.z);
	}

	public static double getCharge(Entity c) {
		return 1000;
		//return (c.getWidth() + c.getHeight());
	}

	public static Vector3D getDirectionVector(Entity c1, Entity c2) {
		/*Vector3D ret = new Vector3D(c1.getPosition());
		ret.sub(c2.getPosition());
		return ret;*/

		// swap to compensate for incorrect implementations of physics functions
		return Functions.getDistance(c2, c1);
	}
}
