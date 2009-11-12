package algorithm.force;

import java.awt.Component;
import java.awt.Rectangle;

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
	Vector3D r = getDirectionVector(c1, c2);
	// return: r * ((c1*c2) / (4*PI*E0*|r|�))
	return r.unit().mul((getCharge(c1) * getCharge(c2)) / (E0 * r.length() * r.length()));
    }

    public static Vector3D hooke(Entity c1, Entity c2) {
	Vector3D r = getDirectionVector(c1, c2);
	//System.out.println("direction vector: " + r);
	return r.unit().mul(E1 * (EQUILIBRUM - r.length()));
    }

    public static double getCharge(Entity c) {
	return 1000;
	//return (c.getWidth() + c.getHeight());
    }

    public static Vector3D getDirectionVector(Entity c1, Entity c2) {
	Vector3D ret = new Vector3D(c1.getPosition());
	ret.sub(c2.getPosition());
	return ret;
    }
}
