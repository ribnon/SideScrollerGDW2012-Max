package gdw.control;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;

import java.util.HashMap;

public class PlayerInputComponentTemplate extends ComponentTemplate {
	private int downKey, jumpKey, leftKey, rightKey, attackKey, specattackKey;

	private float jumpVelocity, runVelocity;

	private long waitingTime;

	private boolean isUnflippedRight;

	/**
	 * PlayerInputComponentTemplate constructor which initializes:
	 * DownKey(:String), JumpKey(:String), LeftKey(:String), RightKey(:String),
	 * AttackKey(:String), SpecialAttackKey(:String), JumpVelocity(:float),
	 * RunVelocity(:float), WaitingTime(:float) StringValues can be found in
	 * PlayerInputComponentManager
	 * 
	 * @param params
	 */
	public PlayerInputComponentTemplate(HashMap<String, String> params) {
		super(params);

		PlayerInputComponentManager pm = PlayerInputComponentManager.getInstance();
		downKey = pm.getKeyValue(super.getStringParam(
				"DownKey", "DOWN"));
		jumpKey = pm.getKeyValue(super.getStringParam(
				"JumpKey", "UP"));
		leftKey = pm.getKeyValue(super.getStringParam(
				"LeftKey", "LEFT"));
		rightKey = pm.getKeyValue(super
				.getStringParam("RightKey", "RIGHT"));
		attackKey = pm.getKeyValue(super
				.getStringParam("AttackKey", "SPACE"));
		specattackKey = pm.getKeyValue(super
				.getStringParam("SpecialAttackKey", "RCONTROL"));

		jumpVelocity = super.getIntegerParam("JumpVelocity", 5);
		runVelocity = super.getIntegerParam("RunVelocity", 10);

		waitingTime = super.getIntegerParam("WaitingTime", 1000);

		int unflipped = super.getIntegerParam("IsUnflippedRight", 1);

		if (unflipped == 1) {
			isUnflippedRight = true;
		} else if (unflipped == 0) {
			isUnflippedRight = false;
		}
	}

	@Override
	public Component createComponent() {
		return new PlayerInputComponent(this, downKey, jumpKey, leftKey,
				rightKey, attackKey, specattackKey, jumpVelocity, runVelocity,
				waitingTime, isUnflippedRight);
	}
}
