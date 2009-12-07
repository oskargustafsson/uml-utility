package uml_entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import algorithm.force.ForceAlgorithm;
import algorithm.force.Vector3D;
import base.Canvas;

public class SimpleInterface extends Entity implements MouseListener {

	public static final int CIRCLE_DIAMETER = 30, BOUND_W = 150, BOUND_H = 50;

	public SimpleInterface(Canvas canvas, String name) {
		super(canvas, new File("/javalib/" + name + ".java"));

		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.setColor(Color.WHITE);
		g2d.fillOval(0, 0, CIRCLE_DIAMETER, CIRCLE_DIAMETER);
		g2d.setColor(Color.BLACK);
		g2d.drawOval(0, 0, CIRCLE_DIAMETER, CIRCLE_DIAMETER);
		g2d.drawString(getIdentifier(), 0, CIRCLE_DIAMETER + 16);
	}

	@Override
	public void setZoom(int zoom) {
		// TODO Auto-generated method stub
		
	}

}
