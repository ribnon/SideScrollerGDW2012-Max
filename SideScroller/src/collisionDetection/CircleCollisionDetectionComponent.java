package collisionDetection;

import gdw.entityCore.ComponentTemplate;

public class CircleCollisionDetectionComponent extends CollisionDetectionComponent
{
	private float radius;
	
	public CircleCollisionDetectionComponent(ComponentTemplate template)
	{
		super(template, CollisionDetectionComponent.COLLISION_COMPONENT_SUBCLASS_CIRCLE);
		
		radius = template.getFloatParam("radius", 1.0f);
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
