package gdw.gameplay.player;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;
import gdw.gameplay.GameColor;

import java.util.HashMap;

public class PlayerWeaponComponentTemplate extends ComponentTemplate
{
	private GameColor currentColor;
	private float healthIncrement;
	
	public PlayerWeaponComponentTemplate(HashMap<String, String> params)
	{
		super(params);
		String gameColor = getStringParam("currentColor","0;1;0");
		String gameColorArr[] = gameColor.split(";");
		if(gameColorArr.length==3)
		{
			this.currentColor = new GameColor(gameColorArr[0].equals("1"), gameColorArr[1].equals("1"), gameColorArr[2].equals("1"));
		}else
		{
			this.currentColor = new GameColor();
		}
		this.healthIncrement = getFloatParam("healthIncrement",50.0f);
	}

	@Override
	public Component createComponent()
	{
		return new PlayerWeaponComponent(this);
	}

	public GameColor getCurrentColor()
	{
		return currentColor;
	}

	public float getHealthIncrement()
	{
		return healthIncrement;
	}
	
	
}
