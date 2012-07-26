package gdw.gameplay.player;

import java.util.HashMap;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;

public class PlayerBehaviorComponentTemplate extends ComponentTemplate
{
	private float healthPercent;
	private float deathTimerDuration;

	private float hitDuration;
	
	private float hitAngle;
	private float startAngle;
	private float idleAngle;
	
	private float healthChangeInterval;
	private float healthChangeTimer;

	public PlayerBehaviorComponentTemplate(HashMap<String, String> params)
	{
		super(params);

		healthPercent = getFloatParam("healthPercent", 100f);
		deathTimerDuration = getFloatParam("deathTimerDuration", 3000f);
		
		hitDuration = getFloatParam("hitDuration", 1.0f);
		
		hitAngle = getFloatParam("hitAngle", 90.0f);
		startAngle = getFloatParam("startAngle", 170.0f);
		idleAngle = getFloatParam("idleAngle", 45.0f);

		healthChangeInterval = getFloatParam("helthChangeInterval", 500.0f);
		healthChangeTimer = getFloatParam("healthChangeTimer", 0f);
	}
	
	
	public float getHealthPercent()
	{
		return healthPercent;
	}

	public float getDeathTimerDuration()
	{
		return deathTimerDuration;
	}

	public float getHitDuration()
	{
		return hitDuration;
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
