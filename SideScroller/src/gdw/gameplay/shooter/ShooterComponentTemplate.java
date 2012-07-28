package gdw.gameplay.shooter;

import java.util.HashMap;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;

public class ShooterComponentTemplate extends ComponentTemplate
{
	private int projectilePoolSize;
	private int projectileFireCount;
	private float projectileFireFrequency;
	private float projectilePoolCooldownTime;
	
	public ShooterComponentTemplate(HashMap<String, String> params)
	{
		super(params);
		projectilePoolSize = getIntegerParam("projectilePoolSize", 1);
		projectileFireCount = getIntegerParam("projectileFireCount", 1);
		projectileFireFrequency = getFloatParam("projectileFireFrequency", 5.0f); 
		projectilePoolCooldownTime = getFloatParam("projectilePoolCooldownTime", 0.0f); 
	}

	@Override
	public Component createComponent()
	{
		return new ShooterComponent(this);
	}

	public int getProjectilePoolSize()
	{
		return projectilePoolSize;
	}

	public void setProjectilePoolSize(int projectilePoolSize)
	{
		this.projectilePoolSize = projectilePoolSize;
	}

	public int getProjectileFireCount()
	{
		return projectileFireCount;
	}

	public void setProjectileFireCount(int projectileFireCount)
	{
		this.projectileFireCount = projectileFireCount;
	}

	public float getProjectileFireFrequency()
	{
		return projectileFireFrequency;
	}

	public void setProjectileFireFrequency(float projectileFireFrequency)
	{
		this.projectileFireFrequency = projectileFireFrequency;
	}

	public float getProjectilePoolCooldownTime()
	{
		return projectilePoolCooldownTime;
	}

	public void setProjectilePoolCooldownTime(float projectilePoolCooldownTime)
	{
		this.projectilePoolCooldownTime = projectilePoolCooldownTime;
	}

}
