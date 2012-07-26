package gdw.gameplay.enemy;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;


public class EnemyBehaviorComponent extends Component
{

	public final static int COMPONENT_TYPE = 12;

	private boolean hostile;
	private float aggroRange;
	private String shootEntityTemplate;

	public EnemyBehaviorComponent(ComponentTemplate template)
	{
		super(template);
		
		if (template != null && template instanceof EnemyBehaviorComponenTemplate)
		{
			EnemyBehaviorComponenTemplate temp = (EnemyBehaviorComponenTemplate) template;
			this.hostile = temp.isHostile();
			this.aggroRange = temp.getAggroRange();
			this.shootEntityTemplate = temp.getShootEntityTemplate();
		}
	}
	
	
	public void tick(float deltaTime)
	{
		//wenn hostile false dann followcomponenten leeren
		
		//sonst
		
		//wenn in aggroRange
		//hole alle Spielerkomponenten
		//vergleiche wer näher ist
		//weis der followComponenten zu folgen
		
		
		//TODO offen		
	}

	@Override
	public int getComponentTypeID()
	{
		return EnemyBehaviorComponent.COMPONENT_TYPE;
	}
	
	public boolean isHostile()
	{
		return hostile;
	}


	public void setHostile(boolean hostile)
	{
		this.hostile = hostile;
	}


	public float getAggroRange()
	{
		return aggroRange;
	}


	public void setAggroRange(float aggroRange)
	{
		this.aggroRange = aggroRange;
	}


	public String getShootEntityTemplate()
	{
		return shootEntityTemplate;
	}


	public void setShootEntityTemplate(String shootEntityTemplate)
	{
		this.shootEntityTemplate = shootEntityTemplate;
	}
}
