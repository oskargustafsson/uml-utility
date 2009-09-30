package algorithm.force;

import java.awt.Component;

import javax.swing.JComponent;

import base.Canvas;
import algorithm.Algorithm;

public class ForceAlgorithm extends Algorithm {

	private Component[] edges;
	
	public void execute(Canvas canvas) {
		 edges = canvas.getComponents();
		 
		 for(Component c : edges) {
			 c.setLocation(c.getX() + 1, c.getY() + 1);
		 }
	}

}
