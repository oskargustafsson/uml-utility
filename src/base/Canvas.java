package base;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.LinkedList;

import javax.swing.JPanel;

import uml_entities.Entity;
import uml_entity_connectives.Connective;

@SuppressWarnings("serial")
public class Canvas extends JPanel {

	private LinkedList<Connective> connectives;
	private Connective currentConnective;

	private Point mousePos;
	
	public Canvas() {
		currentConnective = new Connective();
		connectives = new LinkedList<Connective>();
		addMouseMotionListener(new MouseMotionListener() {
			public void mouseDragged(MouseEvent e) {}
			public void mouseMoved(MouseEvent e) {
				mousePos = e.getPoint();
				asdasd();
			}
			
		});
	}
	
	public Connective getCurrentConnective() {
		return currentConnective;
	}

	public void setCurrentConnective(Connective currentConnective) {
		this.currentConnective = currentConnective;
	}

	public LinkedList<Connective> getConnectives() {
		return connectives;
	}
	
	public void addEdgeToCurrentConnective(Entity entity) {
		System.out.println(entity.getIdentifier());
		
		// add (potentially unfinished) connective to the entity, to make it easier to find
		
		switch(currentConnective.getEdgeCount()) {
		case 0:
			currentConnective.addEdge(entity);
			break;
		case 1:
			currentConnective.addEdge(entity);
			currentConnective.debugPrint();
			connectives.add(currentConnective);
			currentConnective.getEdge(0).addConnective(currentConnective);
			currentConnective.getEdge(1).addConnective(currentConnective);
			currentConnective = new Connective();
			GUI.getInstance().setCurrentTool(Tool.NONE);
			repaint();
			break;
		}
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D)g;
		
		/*if(GUI.getInstance().getCurrentTool() == Tool.DRAW_CONNECTIVE && 
		GUI.getInstance().getCurrentConnective().getEdgeCount() == 1) {
			g2d.draw(new Line2D.Double(GUI.getInstance().getCurrentConnective().getP1(), mousePos));
		}*/
		
		for(Connective c : connectives) {
			g2d.draw(c);
		}
	}
	
	public void asdasd() {
		repaint();
	}
}
