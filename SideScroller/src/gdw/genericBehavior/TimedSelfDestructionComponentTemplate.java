package gdw.genericBehavior;

import java.util.HashMap;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;

public class TimedSelfDestructionComponentTemplate extends ComponentTemplate
{
	private float lifeTime;
	
	public TimedSelfDestructionComponentTemplate(
			HashMap<String, String> params)
	{
		super(params);
		lifeTime = getFloatParam("lifeTime", 1.0f);
	}

	@Override
	public Component createComponent()
	{
		return new TimedSelfDestructionComponent(this);
	}

	public float getLifeTime()
	{
		return lifeTime;
	}

	public void setLifeTime(float lifeTime)
	{
		this.lifeTime = lifeTime;
	}
	
}
