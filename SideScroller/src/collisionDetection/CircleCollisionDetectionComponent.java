package collisionDetection;

import gdw.entityCore.ComponentTemplate;

public class CircleCollisionDetectionComponent extends CollisionDetectionComponent
{
	private float radius;
	
	public CircleCollisionDetectionComponent(ComponentTemplate template)
	{
		super(template);
	}
	
	protected void destroy()
	{
		super.destroy();
	}
	
	// Getters / Setters
	public float getRadius()
	{
		return radius;
	}

	public void setRadius(float radius)
	{
		this.radius = radius;
	}

	@Override
	public int getComponentTypeID()
	{
		// TODO Auto-generated method stub
		return 0;
	}
}
