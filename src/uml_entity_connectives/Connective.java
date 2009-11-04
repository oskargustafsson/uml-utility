package uml_entity_connectives;

import java.awt.Polygon;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import uml_entities.Entity;

public abstract class Connective extends Polygon {
    
    Connective(int[] xpoints, int[] ypoints, int npoints) {
	super(xpoints, ypoints, npoints);
	vertices = new Entity[2];
	nVertices = 0;
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

    public abstract void calculatePoints();
    
}
