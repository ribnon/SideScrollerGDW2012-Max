package collisionDetection;

import gdw.entityCore.ComponentTemplate;

public class AABoxCollisionDetectionComponent extends CollisionDetectionComponent
{
	private float halfExtentX;
	private float halfExtentY;
	
	public AABoxCollisionDetectionComponent(ComponentTemplate template)
	{
		super(template, CollisionDetectionComponent.COLLISION_COMPONENT_SUBCLASS_AABOX);
		
		halfExtentX = template.getFloatParam("halfExtentX", 1.0f);
		halfExtentY = template.getFloatParam("halfExtentY", 1.0f);
		CollisionDetectionComponentManager.getInstance().registerAABoxCollisionDetectionComponent(this);
	}
	
	protected void destroy()
	{
		CollisionDetectionComponentManager.getInstance().removeAABoxCollisionDetectionComponent(this);
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
