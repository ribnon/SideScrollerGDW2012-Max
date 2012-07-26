package gdw.gameplay.levelObjects;

import java.util.HashMap;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;

public class RotateBySwitchComponentTemplate extends ComponentTemplate
{
	private float activateRotation;
	private float deactivateRotation;
	private float angularVelocity;
	
	protected RotateBySwitchComponentTemplate(HashMap<String, String> params)
	{
		super(params);
		activateRotation = getFloatParam("activateRotation", 0.0f);
		deactivateRotation = getFloatParam("deactivateRotation", 0.0f);
		angularVelocity = getFloatParam("angularVelocity", 90.0f);
	}

	@Override
	public Component createComponent()
	{
		return new RotateBySwitchComponent(this);
	}

	public float getActivateRotation()
	{
		return activateRotation;
	}

	public float getDeactivateRotation()
	{
		return deactivateRotation;
	}
	
	public float getAngularVelocity()
	{
		return angularVelocity;
	}

}
