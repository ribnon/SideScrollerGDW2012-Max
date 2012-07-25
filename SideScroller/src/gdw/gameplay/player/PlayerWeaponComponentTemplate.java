package gdw.gameplay.player;

import java.util.HashMap;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;

public class PlayerWeaponComponentTemplate extends ComponentTemplate
{
	protected PlayerWeaponComponentTemplate(HashMap<String, String> params)
	{
		super(params);
	}

	@Override
	public Component createComponent()
	{
		return new PlayerWeaponComponent(this);
	}
}
