package algorithm;

import base.Canvas;
import base.GUI;

public class Applier extends Thread {

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
	}

	public Applier(Algorithm algorithm, Canvas data) {
		this(algorithm, data, 0);
	}

	public void run() {
		do {
			algorithm.execute(data);
			data.repaint();
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} while (doRepeat && !isInterrupted());
	}

}
