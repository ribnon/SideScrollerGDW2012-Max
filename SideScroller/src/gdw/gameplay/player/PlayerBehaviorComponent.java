package gdw.gameplay.player;

import gdw.control.messageType.AttackMessage;
import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;
import gdw.entityCore.Entity;
import gdw.entityCore.EntityManager;
import gdw.entityCore.Message;
import gdw.gameplay.progress.GameplayProgressManager;
import gdw.gameplay.progress.RainbowComponent;
import gdw.network.NetSubSystem;

import java.util.LinkedList;

import gdw.collisionDetection.CollisionDetectionMessage;

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
	//alle healthChangeInterval damage bei kontakt enemydamagedealer
	//rainbow component
	//input component flip wenn getDirectionIsRight dann angle flip

	public final static int COMPONENT_TYPE = 14;

	public PlayerBehaviorComponent(ComponentTemplate template)
	{
		super(template);
		if ((template != null) && (template instanceof PlayerBehaviorComponentTemplate))
		{
			PlayerBehaviorComponentTemplate t = (PlayerBehaviorComponentTemplate) template;
			healthPercent = t.getHealthPercent();
			deathTimer = 0.0f;
			deathTimerDuration = t.getDeathTimerDuration();
			hitDuration = t.getHitDuration();
			hitActive = 1.0f;
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

				// TODO: Networkmessage für so einiges an netzsubsystem
				//und die gleichen messages auch behandeln
				//TODO increment aus den anderen componenten auslesen
				
			}
		}else
		{
			
		}
		
		//Health change timer:
		if (healthChangeTimer > 0)
			healthChangeTimer -= deltaTime;
		
		// Weapon Timer
		float increment = 1.0f / (hitDuration * deltaTime);
		
		if (hitActive < 1.0f)
		{
			hitActive += increment;
		}
		
		else if(hitActive == 1.0f) return;
		else hitActive = 1.0f;
	}

	public void onMessage(Message msg)
	{
		// collision: regenbogen, pinsel (nur wenn tod)

		// meine nachrichten
		
		if (msg instanceof AttackMessage)
		{
			if (hitActive == 1.0f)
				hitActive = 0.0f;
		}
		//Collisions
		else if (msg instanceof CollisionDetectionMessage)
		{
			CollisionDetectionMessage cmsg = (CollisionDetectionMessage) msg;
			Entity other;
			if (getOwner().getID() == cmsg.getIDCandidate1())
				other = EntityManager.getInstance().getEntity(cmsg.getIDCandidate2());
			else
				other = EntityManager.getInstance().getEntity(cmsg.getIDCandidate1());
			
			//Rainbow (Dash)
			if (other.getComponent(RainbowComponent.COMPONENT_TYPE) != null)
			{
			}
		}
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
