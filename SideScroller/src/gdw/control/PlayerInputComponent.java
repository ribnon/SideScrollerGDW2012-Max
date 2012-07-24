package gdw.control;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;
import gdw.entityCore.Message;
import gdw.network.NetComponent;

import org.newdawn.slick.Input;

import Physics.SimulationComponent;

public class PlayerInputComponent extends Component {
	private int playerNumber;

	// KeyValues
	private int downKey, jumpKey, leftKey, rightKey, attackKey, specattackKey;

	// VelocityValues
	private float jumpVelocity, runVelocity;

	// PullingTime in ms
	private long waitingTime, pastTime;

	// Flag if Key was down
	private boolean wasDownKeyDown, wasJumpKeyDown, wasLeftKeyDown,
			wasRightKeyDown, wasAttackKeyDown, wasSpecAttackKeyDown;

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
			long waitingTime) {

		super(template);

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

		this.jumpVelocity = jumpVelocity;
		this.runVelocity = runVelocity;

		this.waitingTime = waitingTime;
		this.pastTime = 0l;

		PlayerInputComponentManager.getInstance().registerPlayerInputComponent(
				this);
	}

	public PlayerInputComponent(ComponentTemplate template, int downKey,
			int jumpKey, int leftKey, int rightKey, int attackKey,
			int specattackKey, float jumpVelocity, float runVelocity) {
		this(template, downKey, jumpKey, leftKey, rightKey, attackKey,
				specattackKey, jumpVelocity, runVelocity, 32l);
	}

	public void processingInput(Input input) {
		boolean isDownKeyDown = input.isKeyDown(downKey);
		boolean isJumpKeyDown = input.isKeyDown(jumpKey);
		boolean isRightKeyDown = input.isKeyDown(rightKey);
		boolean isLeftKeyDown = input.isKeyDown(leftKey);
		boolean isAttackKeyDown = input.isKeyDown(attackKey);
		boolean isSpecAttackKeyDown = input.isKeyDown(specattackKey);

		long currentTime = System.currentTimeMillis();
		long deltaTime = pastTime - currentTime;

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

	/**
	 * Reacts on MovementMessages with Behaviour
	 */
	@Override
	public void onMessage(Message msg) {
		SimulationComponent simcomp = (SimulationComponent) super.getOwner()
				.getComponent(SimulationComponent.COMPONENT_TYPE);
		if (simcomp != null) {

		} else {
			System.err.println("SimulationComponent ist nicht initialisiert");
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
