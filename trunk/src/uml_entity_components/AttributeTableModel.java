package uml_entity_components;

import javax.swing.JComponent;

import uml_entities.UmlClass;

@SuppressWarnings("serial")
public class AttributeTableModel extends PanelTableModel{
	
	public AttributeTableModel(UmlClass umlClass, JComponent contents) {
		super(umlClass, contents);
	}
	
	@Override
	public void addRow(Object[] newData) {
		
		getContents().add(new Attribute(newData));
		this.fireTableRowsInserted(getContents().getComponentCount(), 
				getContents().getComponentCount());
		
		getUmlClass().updateRepresentation();
	}
	
	@Override
	public int getColumnCount() {
		return Attribute.partCount;
	}

	public String getColumnName(int index) {
		String[] labels = Attribute.getDataLabels();
		return labels[index];
	}
}
