package gdw.genericBehavior;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;

public class TimedSelfDestructionComponent extends Component
{
	public static final int COMPONENT_TYPE = 33333;
	private float lifeTime;
	
	private float timeLeft;

	public TimedSelfDestructionComponent(ComponentTemplate template)
	{
		super(template);
		TimedSelfDestructionComponentTemplate t = (TimedSelfDestructionComponentTemplate) template;
		lifeTime = t.getLifeTime();
		timeLeft = lifeTime;
	}

	@Override
	public int getComponentTypeID()
	{
		return COMPONENT_TYPE;
	}
	
	
	public void tick(float deltaTime)
	{
		timeLeft -= deltaTime;
		if (timeLeft <= 0) getOwner().markForDestroy();
	}

	public float getLifeTime()
	{
		return lifeTime;
	}

	public void setLifeTime(float lifeTime)
	{
		this.lifeTime = lifeTime;
	}

	public float getTimeLeft()
	{
		return timeLeft;
	}

	public void setTimeLeft(float timeLeft)
	{
		this.timeLeft = timeLeft;
	}
}
