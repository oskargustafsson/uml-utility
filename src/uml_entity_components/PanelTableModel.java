package uml_entity_components;

import javax.swing.JComponent;
import javax.swing.table.AbstractTableModel;

import uml_entities.UmlClass;


@SuppressWarnings("serial")
public abstract class PanelTableModel extends AbstractTableModel {

	private UmlClass umlClass;
	private JComponent contents;
	
	public PanelTableModel(UmlClass umlClass, JComponent contents) {
		this.umlClass = umlClass;
		this.contents = contents;
	}

	public UmlClass getUmlClass() {
		return umlClass;
	}

	public JComponent getContents() {
		return contents;
	}
	
	public abstract int getColumnCount();

	public int getRowCount() {
		return contents.getComponentCount();
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		return ((Indexable)contents.getComponent(rowIndex)).getValue(columnIndex);
	}
	
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		((Indexable)contents.getComponent(rowIndex)).setValue(columnIndex, aValue);
		this.fireTableCellUpdated(rowIndex, columnIndex);
	}
	
	public abstract void addRow(Object[] newData);
	
	public boolean isCellEditable(int row, int col) {
		return true;
	}

	public void removeRow(int index) {
		contents.remove(index);
		fireTableRowsDeleted(index, index);
		umlClass.updateRepresentation();
	}
}
