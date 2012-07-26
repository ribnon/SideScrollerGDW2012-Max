package gdw.gameplay.progress;

import java.util.HashMap;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;

public class RainbowComponentTemplate extends ComponentTemplate
{
	private boolean active;
	private int checkPointNumber;
	private float healthIncrement;
	
	public RainbowComponentTemplate(HashMap<String, String> params)
	{
		super(params);
		if (getIntegerParam("Active", 0) != 0)
			active = true;
		checkPointNumber = getIntegerParam("checkPointNumber", 0);
		healthIncrement = getFloatParam("healthIncrement",10.0f);
	}
	
	public boolean isActive()
	{
		return active;
	}

	public int getCheckPointNumber()
	{
		return checkPointNumber;
	}

	public float getHealthIncrement()
	{
		return healthIncrement;
	}

	@Override
	public Component createComponent()
	{
		return new RainbowComponent(this);
	}

}
