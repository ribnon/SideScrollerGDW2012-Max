package gdw.gameplay.player;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;
import gdw.gameplay.GameColor;

import java.util.HashMap;

public class PlayerWeaponComponentTemplate extends ComponentTemplate
{
	private GameColor currentColor;
	private float healthIncrement;
	
	protected PlayerWeaponComponentTemplate(HashMap<String, String> params)
	{
		super(params);
		String 
	}

	@Override
	public Component createComponent()
	{
		return new PlayerWeaponComponent(this);
	}
}
