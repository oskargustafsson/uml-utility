package uml_entities;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.util.AbstractCollection;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.SortedSet;

import javax.swing.JPanel;

import base.Canvas;
import base.Subgraph;

import algorithm.force.Body;
import algorithm.force.Vector2D;
import algorithm.force.Vector3D;

import uml_entity_connectives.Connective;
import uml_entity_connectives.StraightLine;

@SuppressWarnings("serial")
public abstract class Entity extends JPanel implements Body, MouseMotionListener, MouseListener {

	private Subgraph subgraph;

	private AbstractCollection<Connective> edges;

	private Vector3D position, velocity;

	private boolean isAffected = true;

	private String javaPackage;

	private File source;

	private Canvas canvas;

	private Point mouseDragOffset = new Point();

	public Entity() {
		position = new Vector3D();
		velocity = new Vector3D();
		edges = new LinkedList<Connective>();
	}

	public Entity(Canvas canvas, File source) {
		this();
		this.setCanvas(canvas);
		this.source = source;
	}

	public synchronized void mouseDragged(MouseEvent e) {
		int x = e.getLocationOnScreen().x - getParent().getLocationOnScreen().x;
		int y = e.getLocationOnScreen().y - getParent().getLocationOnScreen().y;
		setLocation(x - mouseDragOffset.x, y - mouseDragOffset.y);
		setPosition(x - mouseDragOffset.x, y - mouseDragOffset.y);
		for(Connective c : getEdges()) {
			c.calculatePoints();
		}
		setAffected(false);
		getParent().repaint();
	}

	public void mouseMoved(MouseEvent e) {}

	public void mousePressed(MouseEvent e) {
		mouseDragOffset = e.getPoint();
	}

	public void mouseClicked(MouseEvent e) {

	}

	public void mouseReleased(MouseEvent e) {
		setAffected(true);
	}

	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public String getIdentifier() {
		return source.getName().substring(0, source.getName().length() - 5);
	}

	public void setIdentifier(String identifier) {
		// NOT DONE
	}

	public void addVelocity(Vector3D v) {
		velocity.add(v);
	}

	public void addVelocity(double x, double y, double z) {
		velocity.add(x, y, z);
	}

	public void setVelocity(Vector3D v) {
		velocity.setTo(v);
	}

	public Vector3D getVelocity() {
		return velocity;
	}

	public void addPosition(Vector3D v) {
		position.add(v);
	}

	public void setPosition(Vector3D v) {
		position.setTo(v);
	}

	public void setPosition(double x, double y, double z) {
		position.setTo(x, y, z);
	}

	public void setPosition(double x, double y) {
		position.setTo(x, y, position.z);
	}

	public Vector3D getPosition() {
		return position;
	}

	public boolean isAffected() {
		return isAffected;
	}

	public void setAffected(boolean isAffected) {
		this.isAffected = isAffected;
	}


	public Subgraph getSubgraph() {
		return subgraph;
	}

	public void setSubgraph(Subgraph subgraph) {
		this.subgraph = subgraph;
	}

	public void addEdge(Connective edge) {
		edges.add(edge);
	}

	public void removeEdge(Connective edge) {
		edges.remove(edge);
	}

	public AbstractCollection<Connective> getEdges() {
		return edges;
	}


	public File getSourceFile() {
		return source;
	}

	public void setSourceFile(File source) {
		this.source = source;
	}

	public String getJavaPackage() {
		return javaPackage;
	}

	public void setJavaPackage(String javaPackage) {
		this.javaPackage = javaPackage;
	}

	public double getZ() {
		return position.z;
	}

	public void setCanvas(Canvas canvas) {
		this.canvas = canvas;
	}

	public Canvas getCanvas() {
		return canvas;
	}

	public abstract void setZoom(int zoom);
	
	public void setLocation(int x, int y) {
		super.setLocation(x, y);
	}
}
