package uml_entity_components;

import java.util.LinkedList;

public class Operation extends ClassEntity {

	String type, stereotype;
	LinkedList<Parameter> parameters;
	
	public Operation(Visibility visibility, String name, String type, String stereotype) {
		super(visibility, name);
		this.type = type;
		this.stereotype = stereotype;
		parameters = new LinkedList<Parameter>();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStereotype() {
		return stereotype;
	}

	public void setStereotype(String stereotype) {
		this.stereotype = stereotype;
	}
	
	public void addParameter(String name, String type, String defValue) {
		parameters.add(new Parameter(name, type, defValue));
	}
	
	public void removeParameter(int index) {
		parameters.remove(index);
	}

}
