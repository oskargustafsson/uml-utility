package base;

import java.util.AbstractCollection;
import java.util.HashSet;
import java.util.LinkedList;

import algorithm.force.Vector3D;

import uml_entities.Entity;
import uml_entity_connectives.Connective;
import uml_entity_connectives.StraightLine;

public class Subgraph {

	public static int count = 0;
	
	public int id;
	
	private HashSet<Entity> vertices;
	private HashSet<Connective> edges;

	public Subgraph() {
		vertices = new HashSet<Entity>();
		edges = new HashSet<Connective>();
		id = count++;
	}

	public void addVertex(Entity v) {
		vertices.add(v);
	}

	public void removeEdge(Entity v) {
		vertices.remove(v);
	}

	public AbstractCollection<Entity> getVertices() {
		return vertices;
	}

	public void addEdge(Connective e) {
		edges.add(e);
	}

	public void removeEdge(Connective e) {
		edges.remove(e);
	}

	public AbstractCollection<Connective> getEdges() {
		return edges;
	}
	
	public int size() {
		return vertices.size();
	}
	
	public void addAll(Subgraph other) {
		vertices.addAll(other.getVertices());
		edges.addAll(other.getEdges());
	}
	
	public Vector3D centreOfMass() {
		Vector3D centre = new Vector3D(0,0,0);
		for(Entity vertex : vertices) {
			centre.add(vertex.getPosition());
		}
		return centre.mul(1.0 / (double)vertices.size());
	}
	
}
