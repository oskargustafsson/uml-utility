package base;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;

import uml_entities.UmlClass;
import uml_entity_components.Attribute;
import uml_entity_components.AttributeTableModel;
import uml_entity_components.PanelTableModel;
import uml_entity_components.Visibility;

@SuppressWarnings("serial")
public class EditClassDialog extends JDialog {

	private UmlClass umlClass;

	private JTabbedPane jTabs;
	private JPanel jTop, jBottom, jAttributesPanel, jAttributesButtonPanel, jOperationsPanel;
	
	private JScrollPane jAttributesPane, jOperations;
	
	private JTextField jNameText;
	
	private JButton jClose, jNewAttribute, jDeleteAttribute, jNewOperation, jDeleteOperation;
	
	private JTable jAttributesTable, jOperationsTable;
	private PanelTableModel attributesTableModel, operationsTableModel;

	public EditClassDialog(final UmlClass umlClass) {
		this.umlClass = umlClass;

		jNameText = new JTextField(umlClass.getIdentifier());
		jNameText.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {}
			public void focusLost(FocusEvent e) {
				umlClass.setIdentifier(jNameText.getText());
				umlClass.updateRepresentation();
			}
			
		});
		
		/* Top panel */
		jTop = new JPanel();
		jTop.setLayout(new BorderLayout());
		jTop.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		jTop.add(new JLabel("Name: "), BorderLayout.WEST);
		jTop.add(jNameText, BorderLayout.CENTER);
		
		/* Attributes tab */
		jNewAttribute = new JButton("New");
		jNewAttribute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { addAttribute(); } });
		jDeleteAttribute = new JButton("Delete");
		jDeleteAttribute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { deleteAttributes(); } });
		
		attributesTableModel = new AttributeTableModel(umlClass, umlClass.getAttributesPanel());
		jAttributesTable = new JTable(attributesTableModel);
		jAttributesTable.setRowSelectionAllowed(true);
		jAttributesPane = new JScrollPane(jAttributesTable);
		jAttributesPanel = new JPanel(new BorderLayout());
		jAttributesPanel.add(jAttributesPane, BorderLayout.CENTER);
		jAttributesButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		jAttributesButtonPanel.add(jNewAttribute);
		jAttributesButtonPanel.add(jDeleteAttribute);
		jAttributesPanel.add(jAttributesButtonPanel, BorderLayout.SOUTH);
		
		/* Operations tab */
		jOperations = new JScrollPane();
		
		/* Tabbed pane */
		jTabs = new JTabbedPane();
		jTabs.addTab("Attributes", jAttributesPanel);
		jTabs.addTab("Operations", jOperations);

		/* Bottom panel */
		jClose = new JButton("Close");
		jClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { dispose(); } });
		jBottom = new JPanel();
		jBottom.add(jClose);
		
		
		Container c = getContentPane();
		c.setLayout(new BorderLayout());

		c.add(jTop, BorderLayout.NORTH);
		c.add(jTabs, BorderLayout.CENTER);
		c.add(jBottom, BorderLayout.SOUTH);
		
		pack();
		setSize(400, 400);
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	private void deleteAttributes() {
		int[] selectedRows = jAttributesTable.getSelectedRows();
		
		if(selectedRows.length == 0)
			JOptionPane.showMessageDialog(this, "Please select which attributes to delete.");

		for(int i = 0; i < selectedRows.length; i++) {
			attributesTableModel.removeRow(selectedRows[i] - i);
		}
	}

	public void addAttribute() {
		attributesTableModel.addRow(new Object[]{Visibility.PRIVATE,"","",""});
	}

}
