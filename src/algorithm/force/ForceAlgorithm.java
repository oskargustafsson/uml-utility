package algorithm.force;

import java.awt.Component;

import javax.swing.JComponent;

import uml_entities.Entity;
import uml_entity_connectives.Connective;

import base.Canvas;
import algorithm.Algorithm;

public class ForceAlgorithm extends Algorithm {

    private Component[] edges;
    private Connective[] verices;
    
    Vector2D netForce = new Vector2D();

    public void execute(Canvas canvas) {
	edges = canvas.getComponents();
	

	for(Component c : edges) {
	    netForce.setTo(0.0, 0.0);
	    
	    for(Component d : edges) {
		if(c != d) {
		    netForce.add(PhysicsLaws.coulomb(c, d));
		}
	    }
	    
	    verices = ((Entity)c).getConnectives();
	    for(Connective conn : verices) {
		netForce.add(PhysicsLaws.hooke(conn.getEdge(0), conn.getEdge(1)));
	    }
	    
	    c.setLocation((int)(c.getX() + netForce.x), (int)(c.getY() + netForce.y));
	    
	}
    }

}
