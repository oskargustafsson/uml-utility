package algorithm.force;

import java.awt.Component;
import java.awt.Rectangle;

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
    public static Vector2D coulomb(Component c1, Component c2) {
	Vector2D r = getDirectionVector(c1, c2);
	// return: r * ((c1*c2) / (4*PI*E0*|r|²))
	return r.unit().mul((getCharge(c1) * getCharge(c2)) / (E0 * r.length() * r.length()));
    }
    
    public static Vector2D hooke(Component c1, Component c2) {
	Vector2D r = getDirectionVector(c1, c2);
	return r.unit().normalize().mul(E1 * (EQUILIBRUM - r.length()));
    }
    
    public static double getCharge(Component c) {
	return (c.getWidth() * c.getHeight());
    }
    
    public static Vector2D getDirectionVector(Component c1, Component c2) {
	Rectangle r1 = c1.getBounds();
	Rectangle r2 = c2.getBounds();
	
	return new Vector2D(r1.getCenterX() - r2.getCenterX(), r1.getCenterY() - r2.getCenterY());
    }
}
