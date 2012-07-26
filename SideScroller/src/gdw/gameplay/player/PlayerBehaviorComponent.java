package gdw.gameplay.player;

import java.util.LinkedList;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;
import gdw.entityCore.Message;
import gdw.gameplay.progress.GameplayProgressManager;
import gdw.network.NetSubSystem;

public class PlayerBehaviorComponent extends Component
{
	private float healthPercent;

	private float deathTimer;// wenn unten respawn beide
	private float deathTimerDuration;

	private float hitDuration;
	private float hitActive;

	private float healthChangeInterval;
	private float healthChangeTimer;

	public static enum AttackType
	{
		Normal, Special
	};

	public final static int COMPONENT_TYPE = 14;

	public PlayerBehaviorComponent(ComponentTemplate template)
	{
		super(template);
		if ((template != null)
				&& (template instanceof PlayerBehaviorComponentTemplate))
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
		// prüfen ob ich server bin
		if (!NetSubSystem.getInstance().isServer())
			return;

		if (healthPercent <= 0.0f)
		{
			deathTimer -= deltaTime;
			if (deathTimer < 0.0f)
			{
				Component currentSpawn = GameplayProgressManager.getInstance()
						.getCurrentSpawnComponent();
				// get all Player
				LinkedList<PlayerBehaviorComponent> allPlayer = PlayerSubSystem
						.getInstance().getAllPlayerBehaviorComponent();
				for (PlayerBehaviorComponent player : allPlayer)
				{
					player.getOwner()
							.setPosX(currentSpawn.getOwner().getPosX());
					player.getOwner()
							.setPosY(currentSpawn.getOwner().getPosY());
					player.deathTimer = deathTimerDuration;
					player.healthPercent = 100.0f;
					player.hitActive = 0.0f;
					player.healthChangeTimer = 0.0f;
				}

				// TODO: Networkmessage für so einiges
				//TODO increment aus den anderen componenten auslesen
				
			}
		}else
		{
			
		}
	}

	public void onMessage(Message msg)
	{
		// collision

		// meine nachrichten
	}

	public void startAttack(AttackType type)
	{

	}

	@Override
	public int getComponentTypeID()
	{
		return PlayerBehaviorComponent.COMPONENT_TYPE;
	}

}
