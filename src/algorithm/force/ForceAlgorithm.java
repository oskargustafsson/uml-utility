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
import base.Subgraph;
import algorithm.Algorithm;

public class ForceAlgorithm extends Algorithm {

	public static double DAMPING = 0.9, EQUILIBRIUM = 0.001;

	public static boolean doFlatten = false;

	private Component[] allVertices;
	private AbstractCollection<Subgraph> subgraphs;

	private double totalVelocity;

	public void execute(Canvas canvas) {

		allVertices = canvas.getComponents();
		subgraphs = canvas.getSubgraphs();

		for(Subgraph subgraph : subgraphs) {
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
					System.out.println(Math.abs(vertex.getZ()) * Math.signum(-vertex.getZ()));
				}
			}

			for(Connective edge : subgraph.getEdges()) {
				// Spring attraction/repulsion
				edge.getVertex(0).addVelocity(PhysicsLaws.hooke(edge.getVertex(0), edge.getVertex(1)));
				edge.getVertex(1).addVelocity(PhysicsLaws.hooke(edge.getVertex(1), edge.getVertex(0)));
			}

		}

		totalVelocity = 0;

		// Damping and appliance of the velocity
		for(Component vertex : allVertices) {
			Entity e = (Entity)vertex;
			Vector3D vel = e.getVelocity().mul(DAMPING);
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
