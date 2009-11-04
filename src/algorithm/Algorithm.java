package algorithm;

import base.Canvas;

public abstract class Algorithm {

    private boolean hasTerminated;

    public boolean hasTerminated() {
        return hasTerminated;
    }

    public void setTerminated() {
        this.hasTerminated = true;
    }

    public abstract void execute(Canvas canvas);

}
