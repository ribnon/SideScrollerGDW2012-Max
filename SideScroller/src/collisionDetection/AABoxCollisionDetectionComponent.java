package collisionDetection;

import gdw.entityCore.ComponentTemplate;

public class AABoxCollisionDetectionComponent extends CollisionDetectionComponent
{
	private float halfExtentX;
	private float halfExtentY;
	
	public AABoxCollisionDetectionComponent(ComponentTemplate template)
	{
		super(template);
	}
	
	protected void destroy()
	{
		super.destroy();
	}

	
	// Getters / Setters
	public float getHalfExtentX()
	{
		return halfExtentX;
	}

	public void setHalfExtentX(float halfExtentX)
	{
		this.halfExtentX = halfExtentX;
	}

	public float getHalfExtentY()
	{
		return halfExtentY;
	}

	public void setHalfExtentY(float halfExtentY)
	{
		this.halfExtentY = halfExtentY;
	}
}
