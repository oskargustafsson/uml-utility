package algorithm.force;

public interface Body {

	public Vector2D getVelocity();
	
	public void setVelocity(Vector2D v);
	
	public void addVelocity(Vector2D v);
	
}
