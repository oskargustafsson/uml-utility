package uml_entity_connectives;

import java.awt.BasicStroke;
import java.awt.Polygon;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import uml_entities.Entity;

public abstract class Connective extends CubicCurve2D.Double {
	
	protected double weight = 1;

	protected double dx0 = 0, dy0 = 0, dx1 = 0, dy1 = 0, px0, py0, px1, py1;
	
	protected String multiplicity[] = {"", ""};
	
	public static final Polygon EMPTY = new Polygon(new int[0], new int[0], 0);
	public static final Polygon TRIANGLE = new Polygon(new int[]{0, 10, 10}, new int[]{0, -10, 10}, 3);
	public static final Polygon ARROWHEAD = new Polygon(new int[]{0, 10, 0, 10, 0}, new int[]{0, -10, 0, 10, 0}, 5);
	
	private final static float dash1[] = {10.0f};
	public final static BasicStroke DASHED = new BasicStroke(1.0f, 
			BasicStroke.CAP_BUTT, 
			BasicStroke.JOIN_MITER, 
			10.0f, dash1, 0.0f);
	public final static BasicStroke PLAIN = new BasicStroke(1.0f);
	
	private Polygon startSymbol, endSymbol, dummy;
	private BasicStroke stroke;
	
	private int DUMMY_N = 100;
	
	Connective(int[] xpoints, int[] ypoints, int npoints, Polygon startSymbol, Polygon endSymbol, BasicStroke stroke) {
		//super(xpoints, ypoints, npoints);
		dummy = new Polygon(new int[DUMMY_N], new int[DUMMY_N], DUMMY_N);
		vertices = new Entity[2];
		nVertices = 0;
		this.startSymbol = startSymbol;
		this.endSymbol = endSymbol;
		this.setStroke(stroke);
	}

	private Entity[] vertices;

	int nVertices;

	public void addVertex(Entity entity) {
		vertices[nVertices] = entity;
		nVertices++;
	}

	public int getVertexCount() {
		return nVertices;
	}

	public Entity getVertex(int index) {
		return vertices[index];
	}
	
	public void setVertex(int index, Entity vertex) {
		vertices[index] = vertex;
	}

	public abstract void calculatePoints();
	
	public int getStartX() {
		return (int)getX1();
	}
	
	public int getStartY() {
		return (int)getY1();
	}
	
	public int getEndX() {
		return (int)getX2();
	}
	
	public int getEndY() {
		return (int)getY2();
	}

	public int getStartDX() {
		return (int)dx0;
	}
	
	public int getStartDY() {
		return (int)dy0;
	}
	
	public int getEndDX() {
		return (int)dx1;
	}
	
	public int getEndDY() {
		return (int)dy1;
	}

	public Polygon getStartSymbol() {
		return rotatePolygon(startSymbol, false);
	}

	public void setStartSymbol(Polygon startSymbol) {
		this.startSymbol = startSymbol;
	}

	public Polygon getEndSymbol() {
		return rotatePolygon(endSymbol, true);
	}

	public void setEndSymbol(Polygon endSymbol) {
		this.endSymbol = endSymbol;
	}
	
	public double getStartAngle() {
		return Math.atan2(dy0, dx0);
	}

	public double getEndAngle() {
		return Math.atan2(dy1, dx1);
	}

	public void setStroke(BasicStroke stroke) {
		this.stroke = stroke;
	}

	public BasicStroke getStroke() {
		return stroke;
	}
	
	private Polygon rotatePolygon(Polygon p, boolean end) {

		double theta = end ? getEndAngle() : getStartAngle();
		double s = Math.sin(theta);
		double c = Math.cos(theta);
		
		for(int i = 0; i < p.npoints; i++) {
			dummy.xpoints[i] = (int)(p.xpoints[i] * c - p.ypoints[i] * s);
			dummy.ypoints[i] = (int)(p.xpoints[i] * s + p.ypoints[i] * c);
		}
		
		dummy.npoints = p.npoints;
		
		return dummy;
	}
	
	public String getMultiplicity(int i) {
		return multiplicity[i];
	}
	
	public void setMultiplicity(String str, int i) {
		multiplicity[i] = str;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}
}
