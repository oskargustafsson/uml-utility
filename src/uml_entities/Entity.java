package uml_entities;

import java.util.AbstractCollection;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.SortedSet;

import javax.swing.JPanel;

import algorithm.force.Body;
import algorithm.force.Vector2D;

import uml_entity_connectives.Connective;

@SuppressWarnings("serial")
public abstract class Entity extends JPanel implements Body {
	
	private Vector2D velocity;
	
	private String identifier = "";
	
	public Entity() {
	    velocity = new Vector2D();
	}
	
	public Entity(String identifier) {
		this();
		this.identifier = identifier;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public void addVelocity(Vector2D v) {
		velocity.add(v);
	}

	public void setVelocity(Vector2D v) {
		velocity.setTo(v);
	}

	public Vector2D getVelocity() {
		return velocity;
	}
}
