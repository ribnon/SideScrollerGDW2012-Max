package collisionDetection;

import gdw.entityCore.ComponentTemplate;

public class CircleCollisionDetectionComponent extends CollisionDetectionComponent
{
	private float radius;
	
	public CircleCollisionDetectionComponent(ComponentTemplate template)
	{
		super(template, CollisionDetectionComponent.COLLISION_COMPONENT_SUBCLASS_CIRCLE);
		
		radius = template.getFloatParam("radius", 1.0f);
		CollisionDetectionComponentManager.getInstance().registerCircleCollisionDetectionComponent(this);
	}
	
	protected void destroy()
	{
		CollisionDetectionComponentManager.getInstance().removeCircleCollisionDetectionComponent(this);
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
}
