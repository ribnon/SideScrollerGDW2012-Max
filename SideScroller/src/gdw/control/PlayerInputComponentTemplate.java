package gdw.control;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;

import java.util.HashMap;

public class PlayerInputComponentTemplate extends ComponentTemplate {
	private int downKey, jumpKey, leftKey, rightKey, attackKey, specattackKey;

	private float jumpVelocity, runVelocity;

	public PlayerInputComponentTemplate(HashMap<String, String> params) {
		super(params);

		downKey = PlayerInputComponentManager.getKeyValue(super
				.getStringParam("DownKey"));
		jumpKey = PlayerInputComponentManager.getKeyValue(super
				.getStringParam("JumpKey"));
		leftKey = PlayerInputComponentManager.getKeyValue(super
				.getStringParam("LeftKey"));
		rightKey = PlayerInputComponentManager.getKeyValue(super
				.getStringParam("RightKey"));
		attackKey = PlayerInputComponentManager.getKeyValue(super
				.getStringParam("AttackKey"));
		specattackKey = PlayerInputComponentManager.getKeyValue(super
				.getStringParam("SpecialAttackKey"));

		jumpVelocity = super.getIntegerParam("JumpVelocity");
		runVelocity = super.getIntegerParam("RunVelocity");
	}

	@Override
	public Component createComponent() {
		return new PlayerInputComponent(this, downKey, jumpKey, leftKey,
				rightKey, attackKey, specattackKey, jumpVelocity, runVelocity);
	}
}
