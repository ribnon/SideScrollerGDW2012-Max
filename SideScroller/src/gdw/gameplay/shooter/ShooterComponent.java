package gdw.gameplay.shooter;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;
import gdw.entityCore.EntityTemplate;
import gdw.entityCore.EntityTemplateManager;
import gdw.entityCore.Message;

public class ShooterComponent extends Component
{
	public static final int COMPONENT_TYPE = 1100;
	
	private int projectilePoolSize;
	private int projectileFireCount;
	private float projectileFireFrequency;
	private float projectilePoolCooldownTime;
	
	private float timer;
	private float shootTimeStamp;
	
	private int projectilesOwned;
	
	public ShooterComponent(ComponentTemplate template)
	{
		super(template);
		
		ShooterComponentTemplate t = (ShooterComponentTemplate) template;
		
		projectilePoolSize = t.getProjectilePoolSize();
		projectileFireCount = t.getProjectileFireCount();
		projectileFireFrequency = t.getProjectileFireFrequency();
		projectilePoolCooldownTime = t.getProjectilePoolCooldownTime();
		
		timer = 0.0f;
		shootTimeStamp = 0.0f;
		
		projectilesOwned = projectilePoolSize;
	}

	public int getProjectilePoolSize()
	{
		return projectilePoolSize;
	}

	public int getProjectileFireCount()
	{
		return projectileFireCount;
	}

	protected void setProjectilePoolSize(int projectilePoolSize)
	{
		this.projectilePoolSize = projectilePoolSize;
	}

	protected void setProjectileFireCount(int projectileFireCount)
	{
		this.projectileFireCount = projectileFireCount;
	}
	
	public void onMessage(Message msg)
	{
		if (msg instanceof ShooterFiredMessage)
		{
			if (projectilesOwned - projectileFireCount >= 0 &&
					timer - shootTimeStamp >= 1 / projectileFireFrequency)
			{
				projectilesOwned -= projectileFireCount;
				shootTimeStamp = timer;
				
				EntityTemplate template = EntityTemplateManager.getInstance().getEntityTemplate("BasicProjectile");
				template.createEntity(getOwner().getPosX(), getOwner().getPosY(), getOwner().getOrientation());
			}
		}
	}
	
	public void tick(float deltaTime)
	{
		timer += deltaTime;
		
		if (projectilesOwned < projectilePoolSize && timer - shootTimeStamp >= projectilePoolCooldownTime)
		{
			projectilesOwned += projectileFireCount;
			if (projectilesOwned > projectilePoolSize)
				projectilesOwned = projectilePoolSize;
		}
	}

	@Override
	public int getComponentTypeID()
	{
		return COMPONENT_TYPE;
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
