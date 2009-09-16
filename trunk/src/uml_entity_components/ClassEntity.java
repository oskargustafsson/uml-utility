package uml_entity_components;

import javax.swing.JLabel;

/**
 * Super class for all (uml)class entities 
 * @author cs07og4
 *
 */
@SuppressWarnings("serial")
public abstract class ClassEntity extends JLabel {

	private Visibility visibility;
	private String name;
	
	public ClassEntity(Visibility visibility, String name) {
		this.visibility = visibility;
		this.name = name;
		//setOpaque(true);
		//setBackground(Color.GREEN);
	}

	public Visibility getVisibility() {
		return visibility;
	}

	public void setVisibility(Visibility visibility) {
		this.visibility = visibility;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
