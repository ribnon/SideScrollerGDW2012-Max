package gdw.control;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;

import java.util.HashMap;

public class PlayerInputComponentTemplate extends ComponentTemplate {
	private int downKey, jumpKey, leftKey, rightKey, attackKey, specattackKey;

	private float jumpVelocity, runVelocity;

	public PlayerInputComponentTemplate(HashMap<String, String> params) {
		super(params);

		downKey = super.getIntegerParam("DownKey");
		jumpKey = super.getIntegerParam("JumpKey");
		leftKey = super.getIntegerParam("LeftKey");
		rightKey = super.getIntegerParam("RightKey");
		attackKey = super.getIntegerParam("AttackKey");
		specattackKey = super.getIntegerParam("SpecialAttackKey");

		jumpVelocity = super.getIntegerParam("JumpVelocity");
		runVelocity = super.getIntegerParam("RunVelocity");
	}

	@Override
	public Component createComponent() {
		return new PlayerInputComponent(this, downKey, jumpKey, leftKey,
				rightKey, attackKey, specattackKey, jumpVelocity, runVelocity);
	}
}
