package collisionDetection;

import gdw.entityCore.ComponentTemplate;

public class OOBoxCollisionDetectionComponent extends CollisionDetectionComponent
{
	private float halfExtentX;
	private float halfExtentY;
	
	public OOBoxCollisionDetectionComponent(ComponentTemplate template)
	{
		super(template, CollisionDetectionComponent.COLLISION_COMPONENT_SUBCLASS_OOBOX);
		
		if (template instanceof OOBoxCollisionDetectionComponentTemplate)
		{
			halfExtentX = ((OOBoxCollisionDetectionComponentTemplate) template).getHalfExtentX();
			halfExtentY = ((OOBoxCollisionDetectionComponentTemplate) template).getHalfExtentY();
		}
		else
		{
			halfExtentX = 1.0f;
			halfExtentY = 1.0f;
		}
		
		CollisionDetectionComponentManager.getInstance().registerOOBoxCollisionDetectionComponent(this);
	}
	
	protected void destroy()
	{
		CollisionDetectionComponentManager.getInstance().removeOOBoxCollisionDetectionComponent(this);
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
