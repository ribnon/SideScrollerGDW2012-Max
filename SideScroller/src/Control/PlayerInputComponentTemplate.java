package Control;

import java.awt.Component;
import java.util.HashMap;

public class PlayerInputComponentTemplate extends ComponentTemplate
{
	private int downKey, jumpKey, leftKey, rightKey, attackKey, specattackKey;

	private float jumpVelocity, runVelocity;

	public PlayerInputComponentManager(HashMap<String, String> params)
	{
		super.params = params;
	}

	public Component createComponent()
	{
		return new PlayerInputComponent(this, downKey, jumpKey, leftKey,
				rightKey, attackKey, specattackKey);
		// TODO: Add/Register to PlayerInputComponentManager
	}
}
