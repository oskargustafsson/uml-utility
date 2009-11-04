package base;

import java.util.AbstractCollection;
import java.util.LinkedList;

import uml_entities.Entity;
import uml_entity_connectives.StraightLine;

public class Subgraph {
    
    private LinkedList<Entity> vertices;
    private LinkedList<StraightLine> edges;
    
    public Subgraph() {
	vertices = new LinkedList<Entity>();
	edges = new LinkedList<StraightLine>();
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
    
    public void addEdge(StraightLine e) {
	edges.add(e);
    }
    
    public void removeEdge(StraightLine e) {
	edges.remove(e);
    }
    
    public AbstractCollection<StraightLine> getEdges() {
	return edges;
    }
}
