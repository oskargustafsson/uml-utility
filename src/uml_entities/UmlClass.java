package uml_entities;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;

import javax.swing.*;

import algorithm.force.ForceAlgorithm;
import algorithm.force.Vector2D;
import algorithm.force.Vector3D;
import base.Canvas;
import base.EditClassDialog;
import base.GUI;
import base.Tool;

import uml_entity_components.*;
import uml_entity_connectives.Connective;

@SuppressWarnings("serial")
public class UmlClass extends Entity implements MouseListener {

	private JLabel jIdentifier;
	private JPanel jAttributes, jOperations;

	private JPopupMenu jPopup;
	private JMenuItem jEditClass;
	
	private int oldZoom = 0;

	public UmlClass(Canvas canvas, File source) {
		super(canvas, source);

		//System.out.println("Added class: " + source.getAbsolutePath());
		//System.out.println(" Path:\t" + source.getParent());

		jPopup = new JPopupMenu();
		jEditClass = new JMenuItem("Edit class...");
		jEditClass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showEditDialog();
			}});
		jPopup.add(jEditClass);

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(BorderFactory.createLineBorder(Color.BLACK));

		jIdentifier = new JLabel(getIdentifier(), JLabel.CENTER);
		jIdentifier.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));

		jAttributes = new JPanel();
		jAttributes.setLayout(new BoxLayout(jAttributes, BoxLayout.Y_AXIS));
		jAttributes.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));
		jAttributes.setBackground(Color.WHITE);
		jAttributes.setOpaque(true);
		jOperations = new JPanel();
		jOperations.setLayout(new BoxLayout(jOperations, BoxLayout.Y_AXIS));
		jOperations.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));
		jOperations.setBackground(Color.WHITE);
		jOperations.setOpaque(true);

		add(jIdentifier);
		add(new JSeparator(JSeparator.HORIZONTAL));
		add(jAttributes);
		add(new JSeparator(JSeparator.HORIZONTAL));
		add(jOperations);

		setBackground(Color.WHITE);
		setOpaque(true);

		addMouseListener(this);
		addMouseMotionListener(this);
	}

	public void addAttribute(Attribute attribute) {
		jAttributes.add(attribute);
		updateRepresentation();
	}

	public void removeAttribute(int index) {
		jAttributes.remove(index);
		updateRepresentation();
	}

	public void addOperation(Operation operation) {
		jOperations.add(operation);
		updateRepresentation();
	}

	public void removeOperation(int index) {
		jOperations.remove(index);
		updateRepresentation();
	}

	@Override
	public void setIdentifier(String identifier) {
		super.setIdentifier(identifier);
		jIdentifier.setText(identifier);
		updateRepresentation();
	}

	public void showEditDialog() {
		new EditClassDialog(this);
	}

	public void updateRepresentation() {
		invalidate();
		setBounds(getX(), getY(), getPreferredSize().width, getPreferredSize().height);
		validate();
	}

	public JPanel getAttributesPanel() {
		return jAttributes;
	}

	public void mouseClicked(MouseEvent e) {
		// Right click -> show popup menu 
		if(e.getButton() == MouseEvent.BUTTON3) {
			jPopup.show(e.getComponent(), e.getX(), e.getY());
			return;
		}

		if(GUI.getInstance().getCurrentTool() != Tool.NONE) {
			getCanvas().addEdgeToCurrentConnective(this);
		}
	}

	@Override
	public void setZoom(int zoom) {
		// Change size
		Font f = new Font("sansserif", Font.BOLD, zoom);

		jIdentifier.setFont(f);

		for(Component c : jAttributes.getComponents())
			c.setFont(f);

		for(Component c : jOperations.getComponents())
			c.setFont(f);
		
		updateRepresentation();
	}

}

