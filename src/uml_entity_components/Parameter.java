package uml_entity_components;

public class Parameter {

	private String name, type, defValue;

	public Parameter(String defValue, String name, String type) {
		this.defValue = defValue;
		this.name = name;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDefValue() {
		return defValue;
	}

	public void setDefValue(String defValue) {
		this.defValue = defValue;
	}
	
}
