package gdw.gameplay.shooter;

import java.util.HashMap;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;

public class ProjectileComponentTemplate extends ComponentTemplate
{
	private float startSpeed;
	private float endSpeed;
	private float acceleration;
	private float localOffsetX;
	private float localOffsetY;
	private float localOffsetAngular;
	private float distance;
	
	public ProjectileComponentTemplate(HashMap<String, String> params)
	{
		super(params);
		startSpeed = getFloatParam("startSpeed", 120.0f);
		endSpeed = getFloatParam("endSpeed", 120.0f);
		acceleration = getFloatParam("acceleration", 0.0f);
		localOffsetX = getFloatParam("localOffsetX", 0.0f);
		localOffsetY = getFloatParam("localOffsetY", -10.0f);
		localOffsetAngular = getFloatParam("localOffsetAngular", 0.0f);
		distance = getFloatParam("distance", 1000.0f);
	}

	@Override
	public Component createComponent()
	{
		return new ProjectileComponent(this);
	}

	public float getStartSpeed()
	{
		return startSpeed;
	}

	public void setStartSpeed(int startSpeed)
	{
		this.startSpeed = startSpeed;
	}

	public float getEndSpeed()
	{
		return endSpeed;
	}

	public void setEndSpeed(int endSpeed)
	{
		this.endSpeed = endSpeed;
	}

	public float getAcceleration()
	{
		return acceleration;
	}

	public void setAcceleration(float acceleration)
	{
		this.acceleration = acceleration;
	}

	public float getLocalOffsetX()
	{
		return localOffsetX;
	}

	public void setLocalOffsetX(float localOffsetX)
	{
		this.localOffsetX = localOffsetX;
	}

	public float getLocalOffsetY()
	{
		return localOffsetY;
	}

	public void setLocalOffsetY(float localOffsetY)
	{
		this.localOffsetY = localOffsetY;
	}

	public float getLocalOffsetAngular()
	{
		return localOffsetAngular;
	}

	public void setLocalOffsetAngular(float localOffsetAngular)
	{
		this.localOffsetAngular = localOffsetAngular;
	}

	public void setStartSpeed(float startSpeed)
	{
		this.startSpeed = startSpeed;
	}

	public void setEndSpeed(float endSpeed)
	{
		this.endSpeed = endSpeed;
	}

	public float getDistance()
	{
		return distance;
	}

	public void setDistance(float distance)
	{
		this.distance = distance;
	}

}
