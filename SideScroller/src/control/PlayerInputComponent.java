package control;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;
import gdw.entityCore.Message;

import org.newdawn.slick.Input;

public class PlayerInputComponent extends Component {
	private int playerNumber;

	private final int downKey, jumpKey, leftKey, rightKey, attackKey,
			specattackKey;

	private final float jumpVelocity, runVelocity;

	private final boolean wasDownKeyDown, wasJumpKeyDown, wasLeftKeyDown, wasRightKeyDown, wasAttackKeyDown,
			wasSpecAttackKeyDown;

	public static int COMPONENT_TYPE = 3;

	public PlayerInputComponent(ComponentTemplate template, int downKey,
			int jumpKey, int leftKey, int rightKey, int attackKey,
			int specattackKey, int jumpVelocity, int runVelocity) {
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

	public processingInput(Input input) {

	}

	@Override
	public void onMessage(Message msg) {

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
