package uml_entities;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Entity extends JPanel {
	
	private String identifier = "";
	
	public Entity() {
		// nothing yet
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

}
