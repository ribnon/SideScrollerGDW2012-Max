package control;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;

import java.util.HashMap;

public class PlayerInputComponentTemplate extends ComponentTemplate {
	private int downKey, jumpKey, leftKey, rightKey, attackKey, specattackKey;

	private float jumpVelocity, runVelocity;

	public PlayerInputComponentTemplate(HashMap<String, String> params) {
		super(params);

		downKey = super.getIntParam("DownKey");
		jumpKey = super.getIntParam("JumpKey");
		leftKey = super.getIntParam("LeftKey");
		rightKey = super.getIntParam("RightKey");
		attackKey = super.getIntParam("AttackKey");
		specattackKey = super.getIntParam("SpecialAttackKey");

		jumpVelocity = super.getIntParam("JumpVelocity");
		runVelocity = super.getIntParam("RunVelocity");
	}

	@Override
	public Component createComponent() {
		return new PlayerInputComponent(this, downKey, jumpKey, leftKey,
				rightKey, attackKey, specattackKey);
		// TODO: Add/Register to PlayerInputComponentManager
	}
}
