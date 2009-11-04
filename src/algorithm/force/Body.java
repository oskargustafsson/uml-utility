package algorithm.force;

public interface Body {

	public Vector3D getVelocity();
	
	public void setVelocity(Vector3D v);
	
	public void addVelocity(Vector3D v);
	
	public Vector3D getPosition();
	
	public void setPosition(Vector3D v);
	
	public void setPosition(double x, double y);
	
	public void setPosition(double x, double y, double z);
	
	public void addPosition(Vector3D v);
}
