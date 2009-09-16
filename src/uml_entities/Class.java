package uml_entities;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.*;

import uml_entity_components.*;

@SuppressWarnings("serial")
public class Class extends Entity implements MouseListener, MouseMotionListener {
	
	private JPanel parent;
	private Point mouseDragOffset;
	
	private JLabel lblIdentifier;
	private JPanel pnlAttributes, pnlOperations;
	
//	Not needed, using JContainers internal representation
//	private LinkedList<Attribute> attributes;
//	private LinkedList<Operation> operations;
	
	public Class(JPanel parent, String name) {
		super(name);		
		this.parent = parent;
		
//		attributes = new LinkedList<Attribute>();
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		lblIdentifier = new JLabel(getIdentifier(), JLabel.CENTER);
		//lblIdentifier.setAlignmentX(CENTER_ALIGNMENT);
		
		pnlAttributes = new JPanel();
		pnlAttributes.setLayout(new BoxLayout(pnlAttributes, BoxLayout.Y_AXIS));
		pnlAttributes.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 4));
		pnlAttributes.setBackground(Color.WHITE);
		pnlAttributes.setOpaque(true);
		pnlOperations = new JPanel();
		pnlOperations.setLayout(new BoxLayout(pnlOperations, BoxLayout.Y_AXIS));
		pnlOperations.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 4));
		pnlOperations.setBackground(Color.WHITE);
		pnlOperations.setOpaque(true);
		
		add(lblIdentifier);
		add(new JSeparator(JSeparator.HORIZONTAL));
		add(pnlAttributes);
		add(new JSeparator(JSeparator.HORIZONTAL));
		add(pnlOperations);
		
		setBackground(Color.WHITE);
		setOpaque(true);
		
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	public void addAttribute(Attribute attribute) {
		pnlAttributes.add(attribute);
		updateRepresentation();
	}
	
	public void addOperations(Operation operation) {
		pnlOperations.add(operation);
		updateRepresentation();
	}
	
	public void updateRepresentation() {
		invalidate();
		setBounds(getX(), getY(), getPreferredSize().width, getPreferredSize().height);
	}

	@Override
	public synchronized void mouseDragged(MouseEvent e) {
		int x = e.getLocationOnScreen().x - parent.getLocationOnScreen().x;
		int y = e.getLocationOnScreen().y - parent.getLocationOnScreen().y;
		setLocation(x - mouseDragOffset.x, y - mouseDragOffset.y);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		mouseDragOffset = e.getPoint();
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}
}

