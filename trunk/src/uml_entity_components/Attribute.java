package uml_entity_components;


@SuppressWarnings("serial")
public class Attribute extends ClassEntity {
	
	private String type, value;

	public Attribute(Visibility visibility, String name, String type, String value) {
		super(visibility, name);
		this.type = type;
		this.value = value;
		updateRepresentation();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	private void updateRepresentation() {
		String rep = getVisibilityRepresentation() + getName();		// visibility and name always defined
		rep += type.equals("") ? "" : ": " + type;
		rep += value.equals("") ? "" : " = " + value;
		setText(rep);
		validate();
	}
	
	private String getVisibilityRepresentation() {
		switch(getVisibility()) {
		case PRIVATE:	return "-";
		case PUBLIC:	return "+";
		case PROTECTED:	return "#";
		default:		return "";
		}
	}
	
}
