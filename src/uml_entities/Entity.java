package uml_entities;

import java.io.File;
import java.util.AbstractCollection;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.SortedSet;

import javax.swing.JPanel;

import base.Subgraph;

import algorithm.force.Body;
import algorithm.force.Vector2D;
import algorithm.force.Vector3D;

import uml_entity_connectives.Connective;
import uml_entity_connectives.StraightLine;

@SuppressWarnings("serial")
public abstract class Entity extends JPanel implements Body {

    private Subgraph subgraph;

    private AbstractCollection<Connective> edges;
    
    private Vector3D position, velocity;

    private boolean isAffected = true;

    
    private File source;

    public Entity() {
	position = new Vector3D();
	velocity = new Vector3D();
	edges = new LinkedList<Connective>();
    }

    public Entity(File source) {
	this();
	this.source = source;
    }

    public String getIdentifier() {
	return source.getName().substring(0, source.getName().length() - 5);
    }

    public void setIdentifier(String identifier) {
	// NOT DONE
    }

    public void addVelocity(Vector3D v) {
	velocity.add(v);
    }

    public void setVelocity(Vector3D v) {
	velocity.setTo(v);
    }

    public Vector3D getVelocity() {
	return velocity;
    }

    public void addPosition(Vector3D v) {
	position.add(v);
    }

    public void setPosition(Vector3D v) {
	position.setTo(v);
    }

    public void setPosition(double x, double y, double z) {
	position.setTo(x, y, z);
    }

    public void setPosition(double x, double y) {
	position.setTo(x, y, position.z);
    }

    public Vector3D getPosition() {
	return position;
    }

    public boolean isAffected() {
	return isAffected;
    }

    public void setAffected(boolean isAffected) {
	this.isAffected = isAffected;
    }
    
    
    public Subgraph getSubgraph() {
        return subgraph;
    }

    public void setSubgraph(Subgraph subgraph) {
        this.subgraph = subgraph;
    }

    public void addEdge(Connective edge) {
	edges.add(edge);
    }
    
    public void removeEdge(Connective edge) {
	edges.remove(edge);
    }

    public AbstractCollection<Connective> getEdges() {
	return edges;
    }
    
}
