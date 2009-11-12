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

    public static double DAMPING = 0.9, EQUILIBRIUM = 0.001;

    public static boolean doFlatten = false;

    private Component[] vertices;
    private AbstractCollection<Connective> edges;

    private double totalVelocity;

    public void execute(Canvas canvas) {

	vertices = canvas.getComponents();
	for(Component vertex : vertices) {
	    Entity e = (Entity)vertex;

	    // Electric repulsion
	    for(Component otherVertex : vertices) {
		if(vertex != otherVertex) {
		    e.addVelocity(PhysicsLaws.coulomb(e, (Entity)otherVertex));
		}
	    }


	    // Flattening
	    if(doFlatten) {
		//e.addVelocity(0, 0, -Math.sqrt(e.getZ()));
		e.addVelocity(0, 0, Math.abs(e.getZ()) * Math.signum(-e.getZ()));
		System.out.println(Math.abs(e.getZ()) * Math.signum(-e.getZ()));
	    }
	}

	edges = canvas.getConnectives();
	for(Connective edge : edges) {
	    // Spring attraction/repulsion
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

	if(totalVelocity < EQUILIBRIUM) {
	    // Flatten, then stop
	    if(!doFlatten) {
		doFlatten = true;
	    }
	    else {
		GUI.getInstance().stopAlgorithm();
	    }
	}

    }

}
