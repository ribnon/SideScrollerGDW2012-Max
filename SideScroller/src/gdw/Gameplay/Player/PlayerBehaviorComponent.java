package gdw.Gameplay.Player;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;
import gdw.entityCore.Message;
import gdw.Gameplay.Progress.GameplayProgressManager;

public class PlayerBehaviorComponent extends Component {
	private float healthPercent;
	private float healthIncrement;
	private float healthDecrement;
	
	private float deathTimer;
	private float deathTimerDuration;
	
	private float hitDuration;
	private float hitActive;
	
	public final static int COMPONENT_TYPE = 14;
	
	public PlayerBehaviorComponent(ComponentTemplate template)
	{
		super(template);
		healthPercent = template.getFloatParam("HealthPercent");
		healthIncrement = template.getFloatParam("HealthIncrement");
		healthDecrement = template.getFloatParam("HealthDecrement");
		deathTimer = template.getFloatParam("DeathTimer");
		deathTimerDuration = template.getFloatParam("DeathTimerDuration");
		hitDuration = template.getFloatParam("HitDuration");
		hitActive = template.getFloatParam("HitActive");
	}
	
	protected void destroy()
	{
		
	}
	
	public void tick(float deltaTime)
	{
		if(healthPercent == 0.0f)
		{
			deathTimer -= deltaTime;
			if(deathTimer < 0)
			{
				Component currentSpawn= GameplayProgressManager.getInstance().getCurrentSpawnComponent();
				getOwner().setPosX(currentSpawn.getOwner().getPosX());
				getOwner().setPosY(currentSpawn.getOwner().getPosY());
				
				deathTimer = deathTimerDuration;
				healthPercent = 100.0f;
				//TODO: Networkmessage for respawning the player
			}
		}
	}
	
	public void onMessage(Message msg)
	{
		
	}
	
	public void startAttack(Type type)
	{
		
	}

}
