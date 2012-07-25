package gdw.gameplay.enemy;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;

public class EnemyDamageDealerComponent extends Component
{
	
	public final static int COMPONENT_TYPE = 24;

	private float healthDecrement;
	
	public EnemyDamageDealerComponent(ComponentTemplate template)
	{
		super(template);
		if((template != null)&&(template instanceof EnemyDamageDealerComponentTemplate))
		{
			EnemyDamageDealerComponentTemplate t = (EnemyDamageDealerComponentTemplate) template;
			this.healthDecrement = t.getHealthDecrement();
		}
	}

	public float getHealthDecrement()
	{
		return healthDecrement;
	}

	public void setHealthDecrement(float healthDecrement)
	{
		this.healthDecrement = healthDecrement;
	}

	@Override
	public int getComponentTypeID()
	{
		return EnemyDamageDealerComponent.COMPONENT_TYPE;
	}
}
