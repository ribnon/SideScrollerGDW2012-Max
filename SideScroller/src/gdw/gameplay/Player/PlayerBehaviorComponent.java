package gdw.gameplay.Player;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;
import gdw.entityCore.Message;
import gdw.gameplay.progress.GameplayProgressManager;

public class PlayerBehaviorComponent extends Component {
	private float healthPercent;
	private float healthIncrement;
	
	private float deathTimer;
	private float deathTimerDuration;
	
	private float hitDuration;
	private float hitActive;
	
	public final static int COMPONENT_TYPE = 14;
	
	public PlayerBehaviorComponent(ComponentTemplate template)
	{
		super(template);
		PlayerBehaviorComponentTemplate t = (PlayerBehaviorComponentTemplate) template;
		healthPercent = t.getHealthPercent();
		healthIncrement = t.getHealthIncrement();
		deathTimer = t.getDeathTimer();
		deathTimerDuration = t.getDeathTimerDuration();
		hitDuration = t.getHitDuration();
		hitActive = t.getHitActive();
	}
	
	protected void destroy()
	{
		//TODO alter digger, ich hätte gern ne PlayerSubsystem, schwör
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

	@Override
	public int getComponentTypeID() {
		return COMPONENT_TYPE;
	}

}
