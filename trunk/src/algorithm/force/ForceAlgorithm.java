package algorithm.force;

import java.awt.Component;
import java.util.AbstractCollection;

import javax.swing.JComponent;

import uml_entities.Entity;
import uml_entities.UmlClass;
import uml_entity_connectives.Connective;
import uml_entity_connectives.StraightLine;

import base.Canvas;
import base.GUI;
import algorithm.Algorithm;

public class ForceAlgorithm extends Algorithm {

    public static double DAMPING = 0.9;

    private Component[] vertices;
    private AbstractCollection<Connective> edges;

    double totalVelocity;

    public void execute(Canvas canvas) {
	
	// Electric repulsion
	vertices = canvas.getComponents();
	for(Component vertex : vertices) {
	    Entity e = (Entity)vertex;
	    for(Component otherVertex : vertices) {
		if(vertex != otherVertex) {
		    e.addVelocity(PhysicsLaws.coulomb(e, (Entity)otherVertex));
		}
	    }
	}

	// Spring attraction/repulsion
	edges = canvas.getConnectives();
	for(Connective edge : edges) {
	    edge.getVertex(0).addVelocity(PhysicsLaws.hooke(edge.getVertex(0), edge.getVertex(1)));
	    edge.getVertex(1).addVelocity(PhysicsLaws.hooke(edge.getVertex(1), edge.getVertex(0)));
	}

	totalVelocity = 0;
	
	// Damping and appliance of the velocity
	for(Component vertex : vertices) {
	    Entity e = (Entity)vertex;
	    Vector3D vel = e.getVelocity().mul(DAMPING);
	    totalVelocity += vel.length() * vel.length();
	    e.addPosition(vel);
	    if(e.isAffected()) {
		e.setLocation((int)(e.getPosition().x), (int)(e.getPosition().y));
	    }
	}
	
	edges = canvas.getConnectives();
	for(Connective edge : edges) {
	    edge.calculatePoints();
	}
	
	if(totalVelocity < 0.0001) {
	    GUI.getInstance().stopAlgorithm();
	}
    }

}
