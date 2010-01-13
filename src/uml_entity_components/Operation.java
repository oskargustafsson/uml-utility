package uml_entity_components;

import java.util.LinkedList;

import utils.Functions;


@SuppressWarnings("serial")
public class Operation extends ClassEntity implements Indexable {
	
	public static final int partCount = 4;
	
	private String type, value;
	
	LinkedList<Attribute> arguments;

	public Operation(Visibility visibility, String name, String type, String value) {
		super(visibility, name);
		arguments = new LinkedList<Attribute>();
		this.type = type;
		this.value = value;
		updateRepresentation();
	}
	
	public Operation(Object[] args) {
		super(Functions.visibilityFromString(args[0].toString()), args[1].toString());
		arguments = new LinkedList<Attribute>();
		this.type = args[2].toString();
		this.value = args[3].toString();
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
		String rep = Functions.getVisibilityRepresentation(getVisibility()) + getName() + "(";		// visibility and name always defined
		for(Attribute a : arguments) {
			rep += a.getText() + ",";
		}
		if(arguments.size() > 0) {
			rep = rep.substring(0, rep.length() - 1);	// remove trailing comma
		}
		rep += ")";
		rep += type.equals("") ? "" : ": " + type;
		if(rep.length() >= 40)
			setText(rep.substring(0,37) + "...");
		else
			setText(rep);
		validate();
	}
	
	public static String[] getDataLabels() {
		return new String[]{"Visibility", "Name", "Type", "Value"};
	}

	public Object getValue(int index) {
		switch(index) {
		case 0: return getVisibility();
		case 1: return getName();
		case 2: return getType();
		case 3: return getValue();
		default: return null;
		}
	}

	public int getValueCount() {
		return 4;
	}

	public void setValue(int index, Object object) {
		switch(index) {
		case 0: setVisibility(null); break;
		case 1: setName(object.toString()); break;
		case 2: setType(object.toString()); break;
		case 3: setValue(object.toString()); break;
		default: System.out.println("Something is wrong.");;
		}
		updateRepresentation();
	}
	
	public void addArgument(Attribute attribute) {
		arguments.add(attribute);
		updateRepresentation();
	}
	
}
