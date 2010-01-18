package base;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import algorithm.force.PhysicsLaws;

public class EditForces extends JDialog {
	
	private JLabel lblNodeDist, lblEdgeLen, lblEdgeStiffness, lblOrthogonality, lblHierarchalMag, lblHierarchalDist;
	private JSlider sliNodeDist, sliEdgeLen, sliEdgeStiffness, sliOrthogonality, sliHierarchalMag, sliHierarchalDist;
	
	public EditForces(JFrame parent) {
		super(parent, "Edit forces");
		initGUI();
		setAlwaysOnTop(true);
		setSize(400, 400);
		setVisible(true);
	}
	
	private void initGUI() {
		lblNodeDist = new JLabel("Node repulsion", JLabel.RIGHT);
		sliNodeDist = new JSlider(JSlider.HORIZONTAL, 0, 10000, PhysicsLaws.NODE_CHARGE);
		sliNodeDist.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				PhysicsLaws.NODE_CHARGE = sliNodeDist.getValue();
			}
		});
		
		lblEdgeLen = new JLabel("Edge length", JLabel.RIGHT);
		sliEdgeLen = new JSlider(JSlider.HORIZONTAL, 0, 1000, (int)PhysicsLaws.EQUILIBRUM);
		sliEdgeLen.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				PhysicsLaws.EQUILIBRUM = sliEdgeLen.getValue();
			}
		});
		
		lblEdgeStiffness = new JLabel("Edge stiffness", JLabel.RIGHT);
		sliEdgeStiffness = new JSlider(JSlider.HORIZONTAL, 0, 100, (int)PhysicsLaws.HOOKE_C);
		sliEdgeStiffness.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				PhysicsLaws.HOOKE_C = sliEdgeStiffness.getValue();
			}
		});
		
		lblOrthogonality = new JLabel("Orthogonal stiffness", JLabel.RIGHT);
		sliOrthogonality = new JSlider(JSlider.HORIZONTAL, 0, 20, (int)PhysicsLaws.ORTHO_C);
		sliOrthogonality.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				PhysicsLaws.ORTHO_C = sliOrthogonality.getValue();
			}
		});
		
		lblHierarchalDist = new JLabel("Hierarchal distance", JLabel.RIGHT);
		sliHierarchalDist = new JSlider(JSlider.HORIZONTAL, 0, 1000, (int)PhysicsLaws.HIERARCHY_Y_DIST);
		sliHierarchalDist.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				PhysicsLaws.HIERARCHY_Y_DIST = sliHierarchalDist.getValue();
			}
		});
		
		lblHierarchalMag = new JLabel("Hierarchal stiffness", JLabel.RIGHT);
		sliHierarchalMag = new JSlider(JSlider.HORIZONTAL, 0, 1000, (int)PhysicsLaws.HIERAR_C);
		sliHierarchalMag.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				PhysicsLaws.HIERAR_C = sliHierarchalMag.getValue();
			}
		});
		
		//setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setLayout(new GridLayout(6,2));
		
		add(lblNodeDist);
		add(sliNodeDist);
		add(lblEdgeLen);
		add(sliEdgeLen);
		add(lblEdgeStiffness);
		add(sliEdgeStiffness);
		add(lblOrthogonality);
		add(sliOrthogonality);
		add(lblHierarchalDist);
		add(sliHierarchalDist);
		add(lblHierarchalMag);
		add(sliHierarchalMag);
		
	}

}
