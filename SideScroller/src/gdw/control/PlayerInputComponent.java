package gdw.control;

import gdw.control.messageType.AttackMessage;
import gdw.control.messageType.BeginDuckMessage;
import gdw.control.messageType.BeginPullMessage;
import gdw.control.messageType.EndDuckMessage;
import gdw.control.messageType.EndPullMessage;
import gdw.control.messageType.JumpMessage;
import gdw.control.messageType.RunMessage;
import gdw.control.messageType.SpecialAttackMessage;
import gdw.control.messageType.StopMessage;
import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;
import gdw.entityCore.Message;
import gdw.gameplay.levelObjects.SwitchUserComponent;
import gdw.gameplay.player.DuckableComponent;
import gdw.gameplay.player.PlayerBehaviorComponent;
import gdw.gameplay.player.PlayerBehaviorComponent.AttackType;
import gdw.graphics.SpriteComponent;
import gdw.network.NetComponent;
import gdw.network.NetSubSystem;

import org.newdawn.slick.Input;

import Physics.SimulationComponent;

public class PlayerInputComponent extends Component {
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
			long waitingTime, boolean isUnflippedRight) {

		super(template);

		this.setPlayerID(NetSubSystem.getInstance().getPlayerID());

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

		PlayerInputComponentManager.getInstance().registerPlayerInputComponent(
				this);
	}

	public void processingInput(Input input) {
		boolean isDownKeyDown = input.isKeyDown(downKey);
		boolean isJumpKeyDown = input.isKeyDown(jumpKey);
		boolean isRightKeyDown = input.isKeyDown(rightKey);
		boolean isLeftKeyDown = input.isKeyDown(leftKey);
		boolean isAttackKeyDown = input.isKeyDown(attackKey);
		boolean isSpecAttackKeyDown = input.isKeyDown(specattackKey);

		long currentTime = System.currentTimeMillis();

		long deltaTime = 0l;

		if (pastTime != 0l) {
			deltaTime = pastTime - currentTime;
		}

		NetComponent netcomp = (NetComponent) getOwner().getComponent(
				NetComponent.COMPONENT_TYPE);

		if (netcomp != null) {
			// Run
			if (isRightKeyDown && !wasRightKeyDown) {
				netcomp.sendNetworkMessage(new RunMessage(true));
			}

			if (isLeftKeyDown && !wasLeftKeyDown) {
				netcomp.sendNetworkMessage(new RunMessage(false));
			}

			if ((!isRightKeyDown && wasRightKeyDown)
					|| (!isLeftKeyDown && wasLeftKeyDown)) {
				netcomp.sendNetworkMessage(new StopMessage());
			}

			// Jump
			if (isJumpKeyDown && !wasJumpKeyDown) {
				netcomp.sendNetworkMessage(new JumpMessage());
			}

			// Attack
			if (isAttackKeyDown && !wasAttackKeyDown) {
				if (pastTime == 0l) {
					pastTime = currentTime;
				}
			}

			if (isAttackKeyDown && wasAttackKeyDown) {
				if ((pastTime != 0l) && (deltaTime >= waitingTime)) {
					netcomp.sendNetworkMessage(new BeginPullMessage());
				}
			}

			if (!isAttackKeyDown && wasAttackKeyDown) {
				if (deltaTime < waitingTime) {
					netcomp.sendNetworkMessage(new AttackMessage());
				} else {
					netcomp.sendNetworkMessage(new EndPullMessage());
				}
				pastTime = 0l;
			}

			// Special Attack
			if (isSpecAttackKeyDown && !wasSpecAttackKeyDown) {
				netcomp.sendNetworkMessage(new SpecialAttackMessage());
			}

			// Duck
			if (isDownKeyDown && !wasDownKeyDown) {
				netcomp.sendNetworkMessage(new BeginDuckMessage());
			}

			if (!isDownKeyDown && wasDownKeyDown) {
				netcomp.sendNetworkMessage(new EndDuckMessage());
			}
		} else {
			System.err.println("NetComponent nicht initialisiert");
		}

		wasDownKeyDown = isDownKeyDown;
		wasJumpKeyDown = isJumpKeyDown;
		wasRightKeyDown = isRightKeyDown;
		wasLeftKeyDown = isLeftKeyDown;
		wasAttackKeyDown = isAttackKeyDown;
		wasSpecAttackKeyDown = isSpecAttackKeyDown;
	}

	public boolean getUnflippedRight() {
		return isUnflippedRight;
	}

	public void setUnflippedRight(boolean isUnflippedRight) {
		this.isUnflippedRight = isUnflippedRight;
	}

	public boolean getDirectionIsRight() {
		return directionIsRight;
	}

	public int getPlayerID() {
		return playerID;
	}

	public void setPlayerID(int id) {
		playerID = id;
	}

	/**
	 * Reacts on MovementMessages with Behaviour
	 */
	@Override
	public void onMessage(Message msg) {
		// If on Server
		if (NetSubSystem.getInstance().isServer()) {
			NetComponent netcomp = (NetComponent) super.getOwner()
					.getComponent(NetComponent.COMPONENT_TYPE);
			if (netcomp != null) {
				netcomp.sendNetworkMessage(msg);
			}
		}

		// Components which will be modified
		SimulationComponent simcomp = null;
		SpriteComponent spritecomp = null;
		DuckableComponent duckcomp = null;
		PlayerBehaviorComponent plbehcomp = null;
		SwitchUserComponent swusrcomp = null;

		// Run behavior
		if (msg instanceof RunMessage) {
			RunMessage tmpmsg = (RunMessage) msg;
			// Manipulates the SimulationComponent
			simcomp = (SimulationComponent) super.getOwner().getComponent(
					SimulationComponent.COMPONENT_TYPE);
			if (simcomp != null) {
				if (!tmpmsg.isForwardDirection()) {
					simcomp.setVelocityX(-runVelocity);
					directionIsRight = false;
				} else {
					simcomp.setVelocityX(runVelocity);
					directionIsRight = true;
				}
			}

			// Manipulates the SpriteComponent
			spritecomp = (SpriteComponent) super.getOwner().getComponent(
					SpriteComponent.COMPONENT_TYPE);
			if (spritecomp != null) {
				if (!tmpmsg.isForwardDirection()) {
					if (isUnflippedRight)
						spritecomp.setFlipped(true);
					else
						spritecomp.setFlipped(false);
					directionIsRight = false;
				} else {
					if (isUnflippedRight)
						spritecomp.setFlipped(false);
					else
						spritecomp.setFlipped(true);
					directionIsRight = true;
				}
			}
			return;
		}

		if (msg instanceof StopMessage) {
			simcomp = (SimulationComponent) super.getOwner().getComponent(
					SimulationComponent.COMPONENT_TYPE);
			if (simcomp != null) {
				simcomp.setVelocityX(0.0f);
			}
			return;
		}

		// Jump behavior
		if (msg instanceof JumpMessage) {
			simcomp = (SimulationComponent) super.getOwner().getComponent(
					SimulationComponent.COMPONENT_TYPE);
			if (simcomp != null && simcomp.isGrounded()) {
				simcomp.setVelocityY(jumpVelocity);
			}
			return;
		}

		// Duck behavior
		if (msg instanceof BeginDuckMessage) {
			duckcomp = (DuckableComponent) super.getOwner().getComponent(
					DuckableComponent.COMPONENT_TYPE);
			if (duckcomp != null) {
				duckcomp.setDucked(true);
			}
			return;
		}

		if (msg instanceof EndDuckMessage) {
			duckcomp = (DuckableComponent) super.getOwner().getComponent(
					DuckableComponent.COMPONENT_TYPE);
			if (duckcomp != null) {
				duckcomp.setDucked(false);
			}
			return;
		}

		// Attack behavior
		if (msg instanceof AttackMessage) {
			plbehcomp = (PlayerBehaviorComponent) super.getOwner()
					.getComponent(PlayerBehaviorComponent.COMPONENT_TYPE);
			if (plbehcomp != null) {
				plbehcomp.startAttack(AttackType.Normal);
			}
			return;
		}

		// Special attack behavior
		if (msg instanceof SpecialAttackMessage) {
			plbehcomp = (PlayerBehaviorComponent) super.getOwner()
					.getComponent(PlayerBehaviorComponent.COMPONENT_TYPE);
			if (plbehcomp != null) {
				plbehcomp.startAttack(AttackType.Special);
			}
			return;
		}

		// Pull behavior
		if (msg instanceof BeginPullMessage) {
			swusrcomp = (SwitchUserComponent) super.getOwner().getComponent(
					SwitchUserComponent.COMPONENT_TYPE);
			if (swusrcomp != null) {
				swusrcomp.setpullActive(true);
			}
			return;
		}

		if (msg instanceof EndPullMessage) {
			swusrcomp = (SwitchUserComponent) super.getOwner().getComponent(
					SwitchUserComponent.COMPONENT_TYPE);
			if (swusrcomp != null) {
				swusrcomp.setpullActive(true);
			}
			return;
		}
	}

	@Override
	public int getComponentTypeID() {
		return COMPONENT_TYPE;
	}

	@Override
	public void destroy() {
		PlayerInputComponentManager.getInstance()
				.deregisterPlayerInputComponent(this);
	}
}
