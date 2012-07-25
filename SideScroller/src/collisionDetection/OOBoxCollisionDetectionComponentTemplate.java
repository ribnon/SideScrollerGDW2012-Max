package collisionDetection;

import java.util.HashMap;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;

public class OOBoxCollisionDetectionComponentTemplate extends ComponentTemplate
{
	private float halfExtentX;
	private float halfExtentY;
	
	public OOBoxCollisionDetectionComponentTemplate(HashMap<String, String> params)
	{
		super(params);
		
		halfExtentX = getFloatParam("halfExtentX", 1.0f);
		halfExtentY = getFloatParam("halfExtentY", 1.0f);
	}

	@Override
	public Component createComponent()
	{
		return new OOBoxCollisionDetectionComponent(this);
	}

	public float getHalfExtentX()
	{
		return halfExtentX;
	}

	public float getHalfExtentY()
	{
		return halfExtentY;
	}
}
