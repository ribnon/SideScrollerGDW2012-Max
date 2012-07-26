package gdw.genericBehavior;

import java.util.HashMap;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;

public class PivotRotationComponentTemplate extends ComponentTemplate
{
	private float pivotX;
	private float pivotY;
	
	public PivotRotationComponentTemplate(HashMap<String, String> params)
	{
		super(params);
		pivotX = getFloatParam("pivotX", 0.0f);
		pivotY = getFloatParam("pivotY", 0.0f);
	}

	@Override
	public Component createComponent()
	{
		return new PivotRotationComponent(this);
	}

	public float getPivotX()
	{
		return pivotX;
	}

	public float getPivotY()
	{
		return pivotY;
	}

}
