package gdw.gameplay.enemy;

import java.util.HashMap;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;

public class EnemyDamageDealerComponentTemplate extends ComponentTemplate
{
	
	private float healthDecrement;

	public EnemyDamageDealerComponentTemplate(HashMap<String, String> params)
	{
		super(params);
		this.healthDecrement = getFloatParam("healthDecrement",0.0f);
	}
	
	public float getHealthDecrement()
	{
		return healthDecrement;
	}

	@Override
	public Component createComponent()
	{
		return null;
	}

}
