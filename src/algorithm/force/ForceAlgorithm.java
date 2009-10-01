package algorithm.force;

import java.awt.Component;
import java.util.AbstractCollection;

import javax.swing.JComponent;

import uml_entities.Entity;
import uml_entity_connectives.Connective;

import base.Canvas;
import algorithm.Algorithm;

public class ForceAlgorithm extends Algorithm {

    public static double DAMPING = 0.9;

    private Component[] vertices;
    private AbstractCollection<Connective> edges;

    Vector2D netForce = new Vector2D();

    public void execute(Canvas canvas) {
	// Electric repulsion
	vertices = canvas.getComponents();
	for(Component vertex : vertices) {

	    for(Component otherEdge : vertices) {
		if(vertex != otherEdge) {
		    ((Entity)vertex).addVelocity(PhysicsLaws.coulomb(vertex, otherEdge));
		}
	    }
	}

	// Spring attraction/repulsion
	edges = canvas.getConnectives();
	for(Connective edge : edges) {
	    edge.getVertex(0).addVelocity(PhysicsLaws.hooke(edge.getVertex(0), edge.getVertex(1)));
	    edge.getVertex(1).addVelocity(PhysicsLaws.hooke(edge.getVertex(1), edge.getVertex(0)));
	}

	// Damping and appliance of the net velocity
	for(Component vertex : vertices) {
	    Vector2D vel = ((Entity)vertex).getVelocity().mul(DAMPING);
	    vertex.setLocation(vertex.getX() + (int)vel.x, vertex.getY() + (int)vel.y);
	}
    }

}
