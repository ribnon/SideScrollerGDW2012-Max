package collisionDetection;

import java.util.HashMap;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;

public class CircleCollisionDetectionComponentTemplate extends ComponentTemplate
{
	private float radius;
	
	public CircleCollisionDetectionComponentTemplate(HashMap<String, String> params)
	{
		super(params);
		radius = getFloatParam("radius", 1.0f);
	}

	@Override
	public Component createComponent()
	{
		return new CircleCollisionDetectionComponent(this);
	}

	public float getRadius()
	{
		return radius;
	}
}
