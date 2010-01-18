package uml_entities;

import java.io.File;

import base.Canvas;

public class DummyNode extends Entity {
	
	public DummyNode(Canvas canvas, String name) {
		super(canvas, new File("/javalib/" + name + ".java"));
	}

	@Override
	public void setZoom(int zoom) {
		// TODO Auto-generated method stub
		
	}

}
