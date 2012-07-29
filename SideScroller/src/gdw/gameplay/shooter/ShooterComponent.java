package gdw.gameplay.shooter;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

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
	
	private Shooter projectileSet;
	
	private Sound shootSound;
	
	public ShooterComponent(ComponentTemplate template) throws SlickException
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
		
		projectileSet = new Shooter();
		projectileSet.addProjectile("MultifireProjectileA");
		projectileSet.addProjectile("MultifireProjectileB");
		projectileSet.addProjectile("MultifireProjectileC");
		
		shootSound = new Sound("astroids/assets/shoot.wav");
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
				
				if (projectileSet.getProjectileCount() == 0) return;
				
				for (int i = 0; i < projectileFireCount; i++)
				{
					String name = projectileSet.getProjectile(i);
					EntityTemplate template = EntityTemplateManager.getInstance().getEntityTemplate(name);
					template.createEntity(getOwner().getPosX(), getOwner().getPosY(), getOwner().getOrientation());
					
					template = EntityTemplateManager.getInstance().getEntityTemplate("ShootingAnimation");
					template.createEntity(getOwner().getPosX(), getOwner().getPosY(), getOwner().getOrientation());
					
					shootSound.play();
				}
			}
		}
	}
	
	public void tick(float deltaTime)
	{
		timer += deltaTime;
		
		if (projectilesOwned < projectilePoolSize && timer - shootTimeStamp >= projectilePoolCooldownTime)
		{
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
