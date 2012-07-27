package gdw.gameplay.player;

import gdw.control.messageType.AttackMessage;
import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;
import gdw.entityCore.Entity;
import gdw.entityCore.EntityManager;
import gdw.entityCore.Message;
import gdw.gameplay.enemy.EnemyDamageDealerComponent;
import gdw.gameplay.player.messageType.ReSpawnMessage;
import gdw.gameplay.player.messageType.HealthModify;
import gdw.gameplay.progress.GameplayProgressManager;
import gdw.gameplay.progress.RainbowComponent;
import gdw.network.NetSubSystem;

import java.util.LinkedList;

import collisionDetection.CollisionDetectionMessage;

public class PlayerBehaviorComponent extends Component
{
	private float healthPercent;

	private float deathTimer;// wenn unten respawn beide
	private float deathTimerDuration;
	private boolean isDown;

	private float hitDuration;
	private float hitActive;

	private float healthChangeInterval;
	private float healthChangeTimer;

	public static enum AttackType
	{
		Normal, Special
	};

	// alle healthChangeInterval damage bei kontakt enemydamagedealer
	// rainbow component
	// input component flip wenn getDirectionIsRight dann angle flip

	public final static int COMPONENT_TYPE = 14;

	public PlayerBehaviorComponent(ComponentTemplate template)
	{
		super(template);
		if ((template != null)
				&& (template instanceof PlayerBehaviorComponentTemplate))
		{
			PlayerBehaviorComponentTemplate t = (PlayerBehaviorComponentTemplate) template;
			healthPercent = t.getHealthPercent();
			deathTimer = t.getDeathTimerDuration();
			deathTimerDuration = t.getDeathTimerDuration();
			isDown = false;
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

		if (isDown)
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
					player.revive(false);
				}

				// TODO: Networkmessage für so einiges an netzsubsystem
				// und die gleichen messages auch behandeln
				// TODO increment aus den anderen componenten auslesen

			}
		} else
		{

		}

		// Health change timer:
		if (healthChangeTimer > 0)
			healthChangeTimer -= deltaTime;

		// Weapon Timer
		float increment = 1.0f / (hitDuration * deltaTime);

		if (hitActive < 1.0f)
		{
			hitActive += increment;
		}

		else if (hitActive == 1.0f)
			return;
		else
			hitActive = 1.0f;
	}

	public void onMessage(Message msg)
	{
		//TODO:meine nachrichten

		if (msg instanceof AttackMessage)
		{
			if (hitActive == 1.0f)
				hitActive = 0.0f;
		}
		// Collisions
		else if (msg instanceof CollisionDetectionMessage)
		{
			CollisionDetectionMessage cmsg = (CollisionDetectionMessage) msg;
			Entity other;
			if (getOwner().getID() == cmsg.getIDCandidate1())
				other = EntityManager.getInstance().getEntity(
						cmsg.getIDCandidate2());
			else
				other = EntityManager.getInstance().getEntity(
						cmsg.getIDCandidate1());

			handleRainbow(other);
			handlePlayerWeapon(other);
			handleEnemyDamageDealer(other);
		}
		//My events:
		
		//HealthModify
		else if (msg instanceof HealthModify)
		{
			HealthModify hm = (HealthModify) msg;
			healthChange(hm.healthModify, false);
		}
		else if (msg instanceof ReSpawnMessage)
		{
			revive(false);
		}
	}

	private void handleRainbow(Entity other)
	{
		// Rainbow (Dash (is best pony))
		RainbowComponent rainbow = (RainbowComponent) other.getComponent(RainbowComponent.COMPONENT_TYPE);
		if (rainbow != null)
		{
			healthChange(rainbow.getHealthIncrement(), true);
		}
	}

	private void handlePlayerWeapon(Entity other)
	{
		// Brush if currently incapitated
		PlayerWeaponComponent weaponComponent = (PlayerWeaponComponent) other
				.getComponent(PlayerWeaponComponent.COMPONENT_TYPE);
		if (weaponComponent != null)
		{
			if (isDown && healthChangeTimer < 0)
			{
				healthChange(weaponComponent.getHealthIncrement(), true);
				if (healthPercent >= 100)
					revive(true);
			}
		}
	}

	private void handleEnemyDamageDealer(Entity other)
	{
		// Enemy Damage Dealer on contact
		EnemyDamageDealerComponent enemyDmg = (EnemyDamageDealerComponent) other
				.getComponent(EnemyDamageDealerComponent.COMPONENT_TYPE);
		if (enemyDmg != null)
		{
			if (healthChangeTimer < 0)
			{
				float healthChange = -enemyDmg.getHealthDecrement();
				healthChange(healthChange, true);
			}
		}
	}

	private void healthChange(float healthChange, boolean sendMessage)
	{
		healthPercent += healthChange; 
		healthChangeTimer = healthChangeInterval;
		if (healthPercent < 0)
			isDown = true;
		if (healthPercent > 100)
			healthPercent = 100;
		
		if (sendMessage)
			NetSubSystem.getInstance().sendBusMessage(
					getOwner().getID(),
					new HealthModify(healthChange));
	}

	private void revive(boolean sendNetworkMessage)
	{
		deathTimer = deathTimerDuration;
		isDown = false;
		healthPercent = 100.0f;
		hitActive = 0.0f;
		healthChangeTimer = 0.0f;

		if (sendNetworkMessage)
			NetSubSystem.getInstance().sendBusMessage(getOwner().getID(),
					new ReSpawnMessage());
	}

	public void startAttack(AttackType type)
	{
		// TODO: this
	}

	@Override
	public int getComponentTypeID()
	{
		return PlayerBehaviorComponent.COMPONENT_TYPE;
	}

}
