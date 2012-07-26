package gdw.control;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;

import java.util.HashMap;

public class PlayerInputComponentTemplate extends ComponentTemplate {
	private int downKey, jumpKey, leftKey, rightKey, attackKey, specattackKey;

	private float jumpVelocity, runVelocity;

	/**
	 * PlayerInputComponentTemplate constructor which initializes:
	 * DownKey(:String), JumpKey(:String), LeftKey(:String), RightKey(:String),
	 * AttackKey(:String), SpecialAttackKey(:String), JumpVelocity(:float),
	 * RunVelocity(:float)
	 * 
	 * @param params
	 */
	public PlayerInputComponentTemplate(HashMap<String, String> params) {
		super(params);

		downKey = PlayerInputComponentManager.getKeyValue(super.getStringParam(
				"DownKey", "DOWN"));
		jumpKey = PlayerInputComponentManager.getKeyValue(super.getStringParam(
				"JumpKey", "UP"));
		leftKey = PlayerInputComponentManager.getKeyValue(super.getStringParam(
				"LeftKey", "LEFT"));
		rightKey = PlayerInputComponentManager.getKeyValue(super
				.getStringParam("RightKey", "RIGHT"));
		attackKey = PlayerInputComponentManager.getKeyValue(super
				.getStringParam("AttackKey", "SPACE"));
		specattackKey = PlayerInputComponentManager.getKeyValue(super
				.getStringParam("SpecialAttackKey", "RCONTROL"));

		jumpVelocity = super.getIntegerParam("JumpVelocity", 5);
		runVelocity = super.getIntegerParam("RunVelocity", 10);
	}

	@Override
	public Component createComponent() {
		return new PlayerInputComponent(this, downKey, jumpKey, leftKey,
				rightKey, attackKey, specattackKey, jumpVelocity, runVelocity);
	}
}
