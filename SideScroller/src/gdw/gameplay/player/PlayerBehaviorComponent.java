package gdw.gameplay.player;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;
import gdw.entityCore.Message;
import gdw.gameplay.progress.GameplayProgressManager;
import gdw.network.NetSubSystem;

public class PlayerBehaviorComponent extends Component
{
	private float healthPercent;

	private float deathTimer;//wenn unten respawn beide
	private float deathTimerDuration;

	private float hitDuration;
	private float hitActive; // Interpolation zwischen 0 (start) und 1 (ende)
	
	private float hitAngle;
	private float startAngle;
	private float idleAngle;
	
	private float healthChangeInterval;
	private float healthChangeTimer;
	
	public static enum AttackType{
		Normal,
		Special
	};
	
	public final static int COMPONENT_TYPE = 14;

	public PlayerBehaviorComponent(ComponentTemplate template)
	{
		super(template);
		if((template != null)&&(template instanceof PlayerBehaviorComponentTemplate))
		{
			PlayerBehaviorComponentTemplate t = (PlayerBehaviorComponentTemplate) template;
			healthPercent = t.getHealthPercent();
			deathTimer = t.getDeathTimer();
			deathTimerDuration = t.getDeathTimerDuration();
			hitDuration = t.getHitDuration();
			hitActive = t.getHitActive();
			healthChangeInterval = t.getHealthChangeInterval();
			healthChangeTimer = t.getHealthChangeTimer();
		}
		PlayerSubSystem.getInstance().addPlayerBehaviorComponent(this);
	}

	protected void destroy()
	{
		PlayerSubSystem.getInstance().removePlayerBehaviorComponent(this);
		// TODO alter digger, ich hätte gern ne PlayerSubsystem, schwör
	}

	public void tick(float deltaTime)
	{
		//prüfen ob ich server bin
		if(!NetSubSystem.getInstance().isServer())
			return;
		
		if (healthPercent <= 0.0f)
		{
			deathTimer -= deltaTime;
			if (deathTimer < 0.0f)
			{
				Component currentSpawn = GameplayProgressManager.getInstance()
						.getCurrentSpawnComponent();
				getOwner().setPosX(currentSpawn.getOwner().getPosX());
				getOwner().setPosY(currentSpawn.getOwner().getPosY());

				deathTimer = deathTimerDuration;
				healthPercent = 100.0f;
				
				// TODO: Networkmessage for respawning the player
			}
		}
	}

	public void onMessage(Message msg)
	{
		//collision
		
		//meine nachrichten
	}

	public void startAttack(AttackType type)
	{

	}

	@Override
	public int getComponentTypeID()
	{
		return COMPONENT_TYPE;
	}

	public float getHealthPercent()
	{
		return healthPercent;
	}

	public void setHealthPercent(float healthPercent)
	{
		this.healthPercent = healthPercent;
	}

	public float getDeathTimerDuration()
	{
		return deathTimerDuration;
	}

	public void setDeathTimerDuration(float deathTimerDuration)
	{
		this.deathTimerDuration = deathTimerDuration;
	}

	public float getHitDuration()
	{
		return hitDuration;
	}

	public void setHitDuration(float hitDuration)
	{
		this.hitDuration = hitDuration;
	}

	public float getHealthChangeInterval()
	{
		return healthChangeInterval;
	}

	public void setHealthChangeInterval(float healthChangeInterval)
	{
		this.healthChangeInterval = healthChangeInterval;
	}

	public float getHealthChangeTimer()
	{
		return healthChangeTimer;
	}

	public void setHealthChangeTimer(float healthChangeTimer)
	{
		this.healthChangeTimer = healthChangeTimer;
	}

	public float getDeathTimer()
	{
		return deathTimer;
	}

	public float getHitActive()
	{
		return hitActive;
	}

	public float getHitAngle()
	{
		return hitAngle;
	}

	public void setHitAngle(float hitAngle)
	{
		this.hitAngle = hitAngle;
	}

	public float getStartAngle()
	{
		return startAngle;
	}

	public void setStartAngle(float startAngle)
	{
		this.startAngle = startAngle;
	}

	public float getIdleAngle()
	{
		return idleAngle;
	}

	public void setIdleAngle(float idleAngle)
	{
		this.idleAngle = idleAngle;
	}

}
