package gdw.control;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;
import gdw.entityCore.Message;

import org.newdawn.slick.Input;

import Physics.SimulationComponent;

public class PlayerInputComponent extends Component {
	private int playerNumber;

	// KeyValues
	private int downKey, jumpKey, leftKey, rightKey, attackKey, specattackKey;

	// VelocityValues
	private float jumpVelocity, runVelocity;

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
			int specattackKey, float jumpVelocity, float runVelocity) {

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

		wasDownKeyDown = isDownKeyDown;
		wasJumpKeyDown = isJumpKeyDown;
		wasRightKeyDown = isRightKeyDown;
		wasLeftKeyDown = isLeftKeyDown;
		wasAttackKeyDown = isAttackKeyDown;
		wasSpecAttackKeyDown = isSpecAttackKeyDown;
	}

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
