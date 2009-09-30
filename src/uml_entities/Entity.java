package uml_entities;

import java.util.AbstractCollection;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.SortedSet;

import javax.swing.JPanel;

import uml_entity_connectives.Connective;

@SuppressWarnings("serial")
public abstract class Entity extends JPanel {
	
	private String identifier = "";
	
	LinkedHashSet<Connective> connectives;
	
	public Entity() {
		connectives = new LinkedHashSet<Connective>(); 
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

	public void addConnective(Connective connective) {
	    connectives.add(connective);
	}
	
	public void removeConnective(Connective connective) {
	    connectives.remove(connectives);
	}
	
	public Connective[] getConnectives() {
	    return connectives.toArray(new Connective[connectives.size()]);
	}
}
