package gdw.gameplay.enemy;

import java.util.HashMap;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;

public class EnemyBehaviorComponenTemplate extends ComponentTemplate
{
	private boolean hostile;
	private float aggroRange;
	private String shootEntityTemplate;

	
	public EnemyBehaviorComponenTemplate(HashMap<String, String> params)
	{
		super(params);
		int hostileFlag = getIntegerParam("hostile",1);
		this.hostile = hostileFlag == 1;
		
		this.aggroRange = getFloatParam("aggroRange",5.0f);
		
		this.shootEntityTemplate = getStringParam("shootEntityTemplate");
	}

	@Override
	public Component createComponent()
	{
		return new EnemyBehaviorComponent(this);
	}

	public boolean isHostile()
	{
		return hostile;
	}

	public float getAggroRange()
	{
		return aggroRange;
	}

	public String getShootEntityTemplate()
	{
		return shootEntityTemplate;
	}
}
