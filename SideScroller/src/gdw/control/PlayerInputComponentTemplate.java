package gdw.control;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;

import java.util.HashMap;

public class PlayerInputComponentTemplate extends ComponentTemplate
{
	private int downKey, jumpKey, leftKey, rightKey, attackKey, specattackKey;

	private float jumpVelocity, runVelocity;
	private long waitingTime;
	private boolean isUnflippedRight;
	private int playerNumber;

	/**
	 * PlayerInputComponentTemplate constructor which initializes:
	 * DownKey(:String), JumpKey(:String), LeftKey(:String), RightKey(:String),
	 * AttackKey(:String), SpecialAttackKey(:String), JumpVelocity(:float),
	 * RunVelocity(:float) StringValues can be found in
	 * PlayerInputComponentManager
	 * 
	 * @param params
	 */
	public PlayerInputComponentTemplate(HashMap<String, String> params)
	{
		super(params);

		downKey = PlayerInputComponentManager.getInstance().getKeyValue(
				super.getStringParam("DownKey", "DOWN"));
		jumpKey = PlayerInputComponentManager.getInstance().getKeyValue(
				super.getStringParam("JumpKey", "UP"));
		leftKey = PlayerInputComponentManager.getInstance().getKeyValue(
				super.getStringParam("LeftKey", "LEFT"));
		rightKey = PlayerInputComponentManager.getInstance().getKeyValue(
				super.getStringParam("RightKey", "RIGHT"));
		attackKey = PlayerInputComponentManager.getInstance().getKeyValue(
				super.getStringParam("AttackKey", "SPACE"));
		specattackKey = PlayerInputComponentManager.getInstance().getKeyValue(
				super.getStringParam("SpecialAttackKey", "RCONTROL"));

		jumpVelocity = super.getIntegerParam("JumpVelocity", 5);
		runVelocity = super.getIntegerParam("RunVelocity", 10);

		waitingTime = super.getIntegerParam("waitingTime", 500);
		isUnflippedRight = super.getIntegerParam("isUnflippedRight", 0) == 1;
		
		playerNumber = super.getIntegerParam("playerNumber", 2);
	}

	@Override
	public Component createComponent()
	{
		return new PlayerInputComponent(this, downKey, jumpKey, leftKey,
				rightKey, attackKey, specattackKey, jumpVelocity, runVelocity,
				waitingTime, isUnflippedRight,playerNumber);
	}
}
