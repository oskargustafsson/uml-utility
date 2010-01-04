package algorithm;

import algorithm.force.ForceAlgorithm;
import base.Canvas;
import base.GUI;

public class Applier extends Thread {
	
	public long iterations = 0, millisAlgorithm = 0, before, afterAlgorithm;
	
	public static long millisDraw = 0; 

	private Algorithm algorithm;
	private Canvas data;

	private int sleepTime;

	private boolean doRepeat;

	public Applier(Algorithm algorithm, Canvas data, int sleepTime) {
		this.algorithm = algorithm;
		this.data = data;
		if(sleepTime > 0) {
			this.sleepTime = sleepTime;
			doRepeat = true;
		}
		else {
			doRepeat = false;
		}
		iterations = millisAlgorithm = millisDraw = 0;
	}

	public Applier(Algorithm algorithm, Canvas data) {
		this(algorithm, data, 0);
	}

	public void run() {
		do {
			iterations++;
			before = System.currentTimeMillis();
			algorithm.execute(data);
			afterAlgorithm = System.currentTimeMillis();
			millisAlgorithm += afterAlgorithm - before;
			//data.repaint();				//////////// !!
			System.out.println("Iterations: " + iterations);
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				break;
			}
		} while (doRepeat && !isInterrupted() && !algorithm.hasTerminated());
		System.out.println("Iterations: " + iterations);
		System.out.println("Runtime/iter " + ((double)millisAlgorithm/(double)iterations));
		System.out.println("Runtime " + millisAlgorithm + "\n");
		System.out.println("Verices: " + GUI.getInstance().getCanvas().getComponents().length);
		System.out.println("Edges: " + GUI.getInstance().getCanvas().getConnectives().size());
	}

	public void setDoRepeat(boolean doRepeat) {
	    this.doRepeat = doRepeat;
	}
	
}
