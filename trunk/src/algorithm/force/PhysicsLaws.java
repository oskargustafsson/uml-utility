package algorithm.force;

import java.awt.Component;
import java.awt.Rectangle;

import base.GUI;

import uml_entities.Entity;

public class PhysicsLaws {

	public static double PI = 3.14159265;

	// Coulomb constants
	public static double E0 = 10.0;

	// Hooke constants
	public static double EQUILIBRUM = 100.0;
	public static double E1 = 0.1;

	/*
	 * calculates the force vector between c1 and c2
	 */
	public static Vector3D coulomb(Entity c1, Entity c2) {
		Vector3D r = getDirectionVector(c1.getPosition(), c2.getPosition());
		// return: r * ((c1*c2) / (4*PI*E0*|r|�))
		return r.unit().mul((getCharge(c1) * getCharge(c2)) / (E0 * r.length() * r.length()));
	}

	public static Vector3D hooke(Entity c1, Entity c2) {
		Vector3D r = getDirectionVector(c1.getPosition(), c2.getPosition());
		//System.out.println("direction vector: " + r);
		return r.unit().mul(E1 * (EQUILIBRUM - r.length()));
	}
	
	public static Vector3D attraction(Vector3D c1, Vector3D c2) {
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

	public static double getCharge(Entity c) {
		return 1000;
		//return (c.getWidth() + c.getHeight());
	}

	public static Vector3D getDirectionVector(Vector3D c1, Vector3D c2) {
		Vector3D ret = new Vector3D(c1);
		ret.sub(c2);
		return ret;
	}
}
