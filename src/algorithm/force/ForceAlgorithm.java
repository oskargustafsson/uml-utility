package algorithm.force;

import java.awt.Component;
import java.util.AbstractCollection;

import javax.swing.JComponent;

import uml_entities.Entity;
import uml_entities.UmlClass;
import uml_entity_connectives.Connective;
import uml_entity_connectives.Generalization;
import uml_entity_connectives.Realization;
import uml_entity_connectives.StraightLine;

import base.Canvas;
import base.GUI;
import base.Subgraph;
import algorithm.Algorithm;

public class ForceAlgorithm extends Algorithm {

	public static double DAMPING = 0.55, EQUILIBRIUM = 0.001, MAX_VELOCITY = 100;

	public static boolean doFlatten = false;

	private Component[] allVertices;
	private AbstractCollection<Subgraph> subgraphs;

	private double totalVelocity;

	public static Vector3D centreOfGravity = new Vector3D(400,300,0);

	public void execute(Canvas canvas) {

		allVertices = canvas.getComponents();
		subgraphs = canvas.getSubgraphs();

		// Attraction to center
		/* for(Component vertex : allVertices) {
			Entity e = (Entity)vertex;
			e.getVelocity().sub(PhysicsLaws.attraction(e, centreOfGravity));
		} */

		// Repel on subgraph level
		/* for(Subgraph subgraph : subgraphs) {
			for(Subgraph otherSubgraph : subgraphs) {
				if(subgraph != otherSubgraph) {
					for(Entity vertex : subgraph.getVertices()) {

					}
				}
			}
		}*/

		/* for(Subgraph subgraph : subgraphs) {
			for(Entity vertex : subgraph.getVertices()) {

				// Electric repulsion
				for(Entity otherVertex : subgraph.getVertices()) {
					if(vertex != otherVertex) {
						vertex.addVelocity(PhysicsLaws.coulomb(vertex, otherVertex));
					}
				}

				// Flattening
				if(doFlatten) {
					//e.addVelocity(0, 0, -Math.sqrt(e.getZ()));
					vertex.addVelocity(0, 0, Math.abs(vertex.getZ()) * Math.signum(-vertex.getZ()));
					//System.out.println(Math.abs(vertex.getZ()) * Math.signum(-vertex.getZ()));
				}
			}

			for(Connective edge : subgraph.getEdges()) {
				// Spring attraction/repulsion
				edge.getVertex(0).addVelocity(PhysicsLaws.hooke(edge.getVertex(0), edge.getVertex(1)));
				edge.getVertex(1).addVelocity(PhysicsLaws.hooke(edge.getVertex(1), edge.getVertex(0)));
			}

		} */

		for(Component c : canvas.getComponents()) {
			Entity vertex = (Entity)c;

			// Electric repulsion
			for(Component oc : canvas.getComponents()) {
				Entity otherVertex = (Entity)oc;
				if(vertex != otherVertex) {
					vertex.addVelocity(PhysicsLaws.coulomb(vertex, otherVertex));
				}
			}

			// Flattening
			if(doFlatten) {
				vertex.addVelocity(0, 0, Math.abs(vertex.getZ()) * Math.signum(-vertex.getZ()));
			}

			// Keep inside bounding box
			// vertex.addVelocity(PhysicsLaws.boundingBoxRestriction(vertex.getPosition()));

		}

		for(Connective edge : canvas.getConnectives()) {
			// Spring attraction/repulsion
			edge.getVertex(0).addVelocity(PhysicsLaws.hooke(edge.getVertex(0), edge.getVertex(1)));
			edge.getVertex(1).addVelocity(PhysicsLaws.hooke(edge.getVertex(1), edge.getVertex(0)));

			
			if(!(edge instanceof Realization || edge instanceof Generalization)) {
				// Ortogonalize
				edge.getVertex(0).addVelocity(PhysicsLaws.ortogonalize(edge.getVertex(0), edge.getVertex(1)));
				edge.getVertex(1).addVelocity(PhysicsLaws.ortogonalize(edge.getVertex(1), edge.getVertex(0)));
			}
			else {
				// Hierarchies 
				Vector3D h = PhysicsLaws.hierarchy(edge.getVertex(0), edge.getVertex(1));
				edge.getVertex(0).addVelocity(h);
				edge.getVertex(1).addVelocity(h.mul(-1));
			}
		}

		totalVelocity = 0;

		// Gravitation, damping, max velocity check and appliance of the velocity
		for(Component vertex : canvas.getComponents()) {
			Entity e = (Entity)vertex;
			Vector3D vel = e.getVelocity().mul(DAMPING);
			if(vel.length() > MAX_VELOCITY) {
			    vel.mul(DAMPING);
			}
			totalVelocity += vel.length() * vel.length();
			e.addPosition(vel);
			if(e.isAffected()) {
				e.setLocation((int)(e.getPosition().x), (int)(e.getPosition().y));
			}
		}

		for(Connective edge : canvas.getConnectives()) {
			edge.calculatePoints();
		}

		if(totalVelocity < EQUILIBRIUM) {
			// Flatten, then stop
			if(!doFlatten) {
				GUI.getInstance().switchDimension();
			}
			else {
				GUI.getInstance().stopAlgorithm();
			}
		}

	}

}
