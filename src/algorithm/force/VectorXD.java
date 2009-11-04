package algorithm.force;

import java.util.Vector;

@SuppressWarnings("serial")
public class VectorXD extends Vector<Double> {

    private int dim;
    
    public VectorXD(int dim) {
	super(dim);
	this.dim = dim;
    }

    public int getMaxDimensions() {
	return size();
    }

    public void setMaxDimensions(int dimensions) {
	setSize(dimensions);
    }
    
    public int getUsedDimensions() {
	return dim;
    }
    
    public void setUsedDimensions(int dim) {
	if(dim > getMaxDimensions()) 
	    setMaxDimensions(dim);
	this.dim = dim;
    }

    // ADDITION

    public VectorXD add(VectorXD v) {
	if(!sameDim(v))
	    return this;
	
	for(int i = 0; i < getUsedDimensions(); i++) 
	    set(i, get(i) + v.get(i));

	return this;
    }
    
    // SUBTRACTION

    public VectorXD sub(VectorXD v) {
	if(!sameDim(v))
	    return this;
	
	for(int i = 0; i < getUsedDimensions(); i++) 
	    set(i, get(i) - v.get(i));
	
	return this;
    }
    
    // MULTIPLICATION
    
    public VectorXD mul(double k) {
	for(int i = 0; i < getUsedDimensions(); i++) 
	    set(i, get(i) * k);
	
	return this;
    }
    
    public VectorXD dot(VectorXD v) {
	if(!sameDim(v))
	    return this;
	
	for(int i = 0; i < getUsedDimensions(); i++)
	    set(i, get(i) * v.get(i));
	
	return this;
    }
    
    // HELPER FUNCTIONS
    
    private boolean sameDim(VectorXD v) {
	if(v.getUsedDimensions() != getUsedDimensions())
	    return true;
	return false;
    }
}
