package gdw.gameplay.player;

import java.util.HashMap;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;

public class PlayerBehaviorComponentTemplate extends ComponentTemplate
{
	private float healthPercent;

	private float deathTimer;
	private float deathTimerDuration;

	private float hitDuration;
	private float hitActive;
	
	private float healthChangeInterval;
	private float healthChangeTimer;

	protected PlayerBehaviorComponentTemplate(HashMap<String, String> params)
	{
		super(params);
		healthPercent = getFloatParam("healthPercent",100f);
		deathTimer = getFloatParam("deathTimer",0f);
		deathTimerDuration = getFloatParam("deathTimerDuration",3000f);
		hitDuration = getFloatParam("hitDuration",0f);
		hitActive = getFloatParam("hitActive",250f);
		healthChangeInterval = getFloatParam("helthChangeInterval",500.0f);
		healthChangeTimer = getFloatParam("healthChangeTimer", 0f);
	}
	
	
	public float getHealthPercent()
	{
		return healthPercent;
	}

	public float getDeathTimer()
	{
		return deathTimer;
	}

	public float getDeathTimerDuration()
	{
		return deathTimerDuration;
	}

	public float getHitDuration()
	{
		return hitDuration;
	}

	public float getHitActive()
	{
		return hitActive;
	}
	

	public float getHealthChangeInterval()
	{
		return healthChangeInterval;
	}

	

	public float getHealthChangeTimer()
	{
		return healthChangeTimer;
	}


	@Override
	public Component createComponent()
	{
		return new PlayerBehaviorComponent(this);
	}

}
