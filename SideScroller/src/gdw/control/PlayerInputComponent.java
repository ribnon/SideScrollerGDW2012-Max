package gdw.control;

import gdw.control.messageType.AttackMessage;
import gdw.control.messageType.BeginDuckMessage;
import gdw.control.messageType.BeginPullMessage;
import gdw.control.messageType.EndDuckMessage;
import gdw.control.messageType.EndPullMessage;
import gdw.control.messageType.InputMessage;
import gdw.control.messageType.JumpMessage;
import gdw.control.messageType.RunMessage;
import gdw.control.messageType.SpecialAttackMessage;
import gdw.control.messageType.StopMessage;
import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;
import gdw.entityCore.Entity;
import gdw.entityCore.EntityManager;
import gdw.entityCore.Message;
import gdw.gameplay.levelObjects.SwitchUserComponent;
import gdw.gameplay.player.DuckableComponent;
import gdw.gameplay.player.PlayerBehaviorComponent;
import gdw.gameplay.player.PlayerBehaviorComponent.AttackType;
import gdw.graphics.SpriteComponent;
import gdw.network.NetComponent;
import gdw.network.NetSubSystem;
import gdw.network.server.GDWServerLogger;
import gdw.physics.SimulationComponent;

import org.newdawn.slick.Input;

public class PlayerInputComponent extends Component
{
	private int playerID;

	// KeyValues
	private int downKey, jumpKey, leftKey, rightKey, attackKey, specattackKey;

	// VelocityValues
	private float jumpVelocity, runVelocity;

	// PullingTime in ms
	private long waitingTime, pastTime;

	// Flag if Key was down
	private boolean wasDownKeyDown, wasJumpKeyDown, wasLeftKeyDown,
			wasRightKeyDown, wasAttackKeyDown, wasSpecAttackKeyDown,
			directionIsRight, isUnflippedRight;

	public static final int COMPONENT_TYPE = 3;

	/**
	 * PlayerInputComponent Constructor
	 * 
	 * @param template
	 * @param downKey
	 * @param jumpKey
	 * @param leftKey
	 * @param rightKey
	 * @param attackKey
	 * @param specattackKey
	 * @param jumpVelocity
	 * @param runVelocity
	 */
	public PlayerInputComponent(ComponentTemplate template, int downKey,
			int jumpKey, int leftKey, int rightKey, int attackKey,
			int specattackKey, float jumpVelocity, float runVelocity,
			long waitingTime, boolean isUnflippedRight, int playerNumber)
	{

		super(template);

		playerID=playerNumber;
		
		wasDownKeyDown = false;
		wasJumpKeyDown = false;
		wasLeftKeyDown = false;
		wasRightKeyDown = false;
		wasAttackKeyDown = false;
		wasSpecAttackKeyDown = false;

		this.attackKey = attackKey;
		this.specattackKey = specattackKey;
		this.jumpKey = jumpKey;
		this.downKey = downKey;
		this.rightKey = rightKey;
		this.leftKey = leftKey;

		this.directionIsRight = true;
		this.isUnflippedRight = isUnflippedRight;

		this.jumpVelocity = jumpVelocity;
		this.runVelocity = runVelocity;

		this.waitingTime = waitingTime;
		this.pastTime = 0l;

		PlayerInputComponentManager.getInstance().registerPlayerInputComponent(this);
	}

	public void processingInput(Input input)
	{
		boolean isDownKeyDown = input.isKeyDown(downKey);
		boolean isJumpKeyDown = input.isKeyDown(jumpKey);
		boolean isRightKeyDown = input.isKeyDown(rightKey);
		boolean isLeftKeyDown = input.isKeyDown(leftKey);
		boolean isAttackKeyDown = input.isKeyDown(attackKey);
		boolean isSpecAttackKeyDown = input.isKeyDown(specattackKey);

		long currentTime = System.currentTimeMillis();

		long deltaTime = 0l;

		if (pastTime != 0l)
		{
			deltaTime = pastTime - currentTime;
		}

		NetComponent netcomp = (NetComponent) getOwner().getComponent(
				NetComponent.COMPONENT_TYPE);

		// Run
		if (isRightKeyDown && !wasRightKeyDown)
		{
			if (EntityManager.getInstance().isOfflineMode())
			{
				if (this.getOwner() != null)
					this.getOwner().message(new RunMessage(true));
			} else if (netcomp != null)
				netcomp.sendNetworkMessage(new RunMessage(true));
			else
				System.err.println("NetComponent nicht intialisiert");
		}

		if (isLeftKeyDown && !wasLeftKeyDown)
		{
			if (EntityManager.getInstance().isOfflineMode())
			{
				if (this.getOwner() != null)
					this.getOwner().message(new RunMessage(false));
			} else if (netcomp != null)
				netcomp.sendNetworkMessage(new RunMessage(false));
			else
				System.err.println("NetComponent nicht intialisiert");
		}

		if ((!isRightKeyDown && wasRightKeyDown)
				|| (!isLeftKeyDown && wasLeftKeyDown))
		{
			if (EntityManager.getInstance().isOfflineMode())
			{
				if (this.getOwner() != null)
					this.getOwner().message(new StopMessage());
			} else if (netcomp != null)
				netcomp.sendNetworkMessage(new StopMessage());
			else
				System.err.println("NetComponent nicht intialisiert");
		}

		// Jump
		if (isJumpKeyDown && !wasJumpKeyDown)
		{
			if (EntityManager.getInstance().isOfflineMode())
			{
				if (this.getOwner() != null)
					this.getOwner().message(new JumpMessage());
			} else if (netcomp != null)
				netcomp.sendNetworkMessage(new JumpMessage());
			else
				System.err.println("NetComponent nicht intialisiert");
		}

		// Attack
		if (isAttackKeyDown && !wasAttackKeyDown)
		{
			if (pastTime == 0l)
			{
				pastTime = currentTime;
			}
		}

		if (isAttackKeyDown && wasAttackKeyDown)
		{
			if ((pastTime != 0l) && (deltaTime >= waitingTime))
			{
				if (EntityManager.getInstance().isOfflineMode())
				{
					if (this.getOwner() != null)
						this.getOwner().message(new BeginPullMessage());
				} else if (netcomp != null)
					netcomp.sendNetworkMessage(new BeginPullMessage());
				else
					System.err.println("NetComponent nicht intialisiert");
			}
		}

		if (!isAttackKeyDown && wasAttackKeyDown)
		{
			if (deltaTime < waitingTime)
			{
				if (EntityManager.getInstance().isOfflineMode())
				{
					if (this.getOwner() != null)
						this.getOwner().message(new AttackMessage());
				} else if (netcomp != null)
					netcomp.sendNetworkMessage(new AttackMessage());
				else
					System.err.println("NetComponent nicht intialisiert");
			} else
			{
				if (EntityManager.getInstance().isOfflineMode())
				{
					if (this.getOwner() != null)
						this.getOwner().message(new EndPullMessage());
				} else if (netcomp != null)
					netcomp.sendNetworkMessage(new EndPullMessage());
				else
					System.err.println("NetComponent nicht intialisiert");
			}
			pastTime = 0l;
		}

		// Special Attack
		if (isSpecAttackKeyDown && !wasSpecAttackKeyDown)
		{
			if (EntityManager.getInstance().isOfflineMode())
			{
				if (this.getOwner() != null)
					this.getOwner().message(new SpecialAttackMessage());
			} else if (netcomp != null)
				netcomp.sendNetworkMessage(new SpecialAttackMessage());
			else
				System.err.println("NetComponent nicht intialisiert");
		}

		// Duck
		if (isDownKeyDown && !wasDownKeyDown)
		{
			if (EntityManager.getInstance().isOfflineMode())
			{
				if (this.getOwner() != null)
					this.getOwner().message(new BeginDuckMessage());
			} else if (netcomp != null)
				netcomp.sendNetworkMessage(new BeginDuckMessage());
			else
				System.err.println("NetComponent nicht intialisiert");
		}

		if (!isDownKeyDown && wasDownKeyDown)
		{
			if (EntityManager.getInstance().isOfflineMode())
			{
				if (this.getOwner() != null)
					this.getOwner().message(new EndDuckMessage());
			} else if (netcomp != null)
				netcomp.sendNetworkMessage(new EndDuckMessage());
			else
				System.err.println("NetComponent nicht intialisiert");
		}
		

		wasDownKeyDown = isDownKeyDown;
		wasJumpKeyDown = isJumpKeyDown;
		wasRightKeyDown = isRightKeyDown;
		wasLeftKeyDown = isLeftKeyDown;
		wasAttackKeyDown = isAttackKeyDown;
		wasSpecAttackKeyDown = isSpecAttackKeyDown;
	}

	public boolean getUnflippedRight()
	{
		return isUnflippedRight;
	}

	public void setUnflippedRight(boolean isUnflippedRight)
	{
		this.isUnflippedRight = isUnflippedRight;
	}

	public boolean getDirectionIsRight()
	{
		return directionIsRight;
	}

	public int getPlayerID()
	{
		return playerID;
	}

	public void setPlayerID(int id)
	{
		playerID = id;
	}

	/**
	 * Reacts on MovementMessages with Behaviour
	 */
	@Override
	public void onMessage(Message msg)
	{
		if(!(msg instanceof InputMessage))
		{
			return;
		}
		
		if (!EntityManager.getInstance().isOfflineMode())
		{
			// If on Server
			if (NetSubSystem.getInstance().isServer())
			{
				NetComponent netcomp = (NetComponent) super.getOwner()
						.getComponent(NetComponent.COMPONENT_TYPE);
				if (netcomp != null)
				{
					netcomp.sendNetworkMessage(msg);
				}
			}
		}

		// Components which will be modified
		SimulationComponent simcomp = null;
		SpriteComponent spritecomp = null;
		DuckableComponent duckcomp = null;
		PlayerBehaviorComponent plbehcomp = null;
		SwitchUserComponent swusrcomp = null;

		// Run behavior
		if (msg instanceof RunMessage)
		{			
			RunMessage tmpmsg = (RunMessage) msg;
			// Manipulates the SimulationComponent
			simcomp = (SimulationComponent) super.getOwner().getComponent(
					SimulationComponent.COMPONENT_TYPE);
			if (simcomp != null)
			{
				if (!tmpmsg.isForwardDirection())
				{
					simcomp.setVelocityX(-runVelocity);
					directionIsRight = false;
				} else
				{
					simcomp.setVelocityX(runVelocity);
					directionIsRight = true;
				}
			}

			// Manipulates the SpriteComponent
			spritecomp = (SpriteComponent) super.getOwner().getComponent(
					SpriteComponent.COMPONENT_TYPE);
			if (spritecomp != null)
			{
				if (!tmpmsg.isForwardDirection())
				{
					if (isUnflippedRight)
						spritecomp.setFlipped(true);
					else
						spritecomp.setFlipped(false);
					directionIsRight = false;
				} else
				{
					if (isUnflippedRight)
						spritecomp.setFlipped(false);
					else
						spritecomp.setFlipped(true);
					directionIsRight = true;
				}
			}
			return;
		}

		if (msg instanceof StopMessage)
		{
			simcomp = (SimulationComponent) super.getOwner().getComponent(
					SimulationComponent.COMPONENT_TYPE);
			if (simcomp != null)
			{
				simcomp.setVelocityX(0.0f);
			}
			return;
		}

		// Jump behavior
		if (msg instanceof JumpMessage)
		{
			simcomp = (SimulationComponent) super.getOwner().getComponent(
					SimulationComponent.COMPONENT_TYPE);
			if (simcomp != null)
			{
				simcomp.setVelocityY(-jumpVelocity);
			}
			
			/*if (simcomp != null && simcomp.isGrounded())
			{
				simcomp.setVelocityY(-jumpVelocity);
			}*/
			return;
		}

		// Duck behavior
		if (msg instanceof BeginDuckMessage)
		{
			duckcomp = (DuckableComponent) super.getOwner().getComponent(
					DuckableComponent.COMPONENT_TYPE);
			if (duckcomp != null)
			{
				duckcomp.setDucked(true);
			}
			return;
		}

		if (msg instanceof EndDuckMessage)
		{
			duckcomp = (DuckableComponent) super.getOwner().getComponent(
					DuckableComponent.COMPONENT_TYPE);
			if (duckcomp != null)
			{
				duckcomp.setDucked(false);
			}
			return;
		}

		// Attack behavior
		if (msg instanceof AttackMessage)
		{
			plbehcomp = (PlayerBehaviorComponent) super.getOwner()
					.getComponent(PlayerBehaviorComponent.COMPONENT_TYPE);
			if (plbehcomp != null)
			{
				plbehcomp.startAttack(AttackType.Normal);
			}
			return;
		}

		// Special attack behavior
		if (msg instanceof SpecialAttackMessage)
		{
			plbehcomp = (PlayerBehaviorComponent) super.getOwner()
					.getComponent(PlayerBehaviorComponent.COMPONENT_TYPE);
			if (plbehcomp != null)
			{
				plbehcomp.startAttack(AttackType.Special);
			}
			return;
		}

		// Pull behavior
		if (msg instanceof BeginPullMessage)
		{
			swusrcomp = (SwitchUserComponent) super.getOwner().getComponent(
					SwitchUserComponent.COMPONENT_TYPE);
			if (swusrcomp != null)
			{
				swusrcomp.setpullActive(true);
			}
			return;
		}

		if (msg instanceof EndPullMessage)
		{
			swusrcomp = (SwitchUserComponent) super.getOwner().getComponent(
					SwitchUserComponent.COMPONENT_TYPE);
			if (swusrcomp != null)
			{
				swusrcomp.setpullActive(true);
			}
			return;
		}
	}

	@Override
	public int getComponentTypeID()
	{
		return COMPONENT_TYPE;
	}

	@Override
	public void destroy()
	{
		PlayerInputComponentManager.getInstance()
				.deregisterPlayerInputComponent(this);
	}
}
